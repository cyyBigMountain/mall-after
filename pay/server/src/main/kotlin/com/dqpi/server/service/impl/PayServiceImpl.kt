package com.dqpi.server.service.impl

import com.alibaba.fastjson.JSON
import com.dqpi.server.dao.PayInfoDao
import com.dqpi.server.domain.entity.PayInfo
import com.dqpi.server.enums.PayPlatformEnum
import com.dqpi.server.service.PayService
import com.lly835.bestpay.enums.BestPayPlatformEnum
import com.lly835.bestpay.enums.BestPayTypeEnum
import com.lly835.bestpay.enums.OrderStatusEnum
import com.lly835.bestpay.model.PayRequest
import com.lly835.bestpay.model.PayResponse
import com.lly835.bestpay.service.BestPayService
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*
import javax.annotation.Resource

@Service
class PayServiceImpl: PayService {
    companion object {
        const val QUEUE_PAY_NOTIFY: String = "payNotify"
    }
    
    private val log = LoggerFactory.getLogger(this::class.java)

    @Resource
    private lateinit var bestPayService: BestPayService
    
    @Resource
    private lateinit var payInfoDao: PayInfoDao
    
    @Resource
    private lateinit var amqpTemplate: AmqpTemplate
    
    /**
     * 发起支付
     */
    override fun create(orderNo: String, amount: BigDecimal, payType: BestPayTypeEnum): PayResponse {
        //写入数据库
        payInfoDao.save(PayInfo(orderNo = orderNo.toLong(), 
                                payPlatform = PayPlatformEnum.getByBestPayTypeEnum(payType).code,
                                platformStatus = OrderStatusEnum.NOTPAY.name,
                                payAmount = amount))
        
        //发起支付
        val payRequest = PayRequest()
        payRequest.orderName = "6051588-微信支付"
        payRequest.orderId = orderNo
        payRequest.orderAmount = amount.toDouble()
        payRequest.payTypeEnum = payType
        
        //支付响应
        val payResponse = bestPayService.pay(payRequest)
        log.info("发起支付 response={$payResponse}")
        return payResponse
    }

    /**
     * 用户付款后异步通知处理
     */
    override fun asyncNotify(notifyData: String): String {
        //签名校验
        val payResponse = bestPayService.asyncNotify(notifyData)
        log.info("异步通知 response={$payResponse}")
            
        //金额校验(数据库查订单)
        val payOptional = payInfoDao.findByOrderNo(payResponse.orderId.toLong())
        require(payOptional.isPresent) { "通过orderNo查询到的结果是null" }
        
        payOptional.get().let {
            //判断订单状态是否未支付
            if (it.platformStatus != OrderStatusEnum.SUCCESS.name) {
                //判断数据库金额和支付响应金额是否相等
                require(it.payAmount == BigDecimal(payResponse.orderAmount.toString())) {
                    "异步通知中的金额和数据库里的不一致, orderNo=${payResponse.orderAmount}"
                }

                //修改订单状态
                it.platformStatus = OrderStatusEnum.SUCCESS.name
                it.platformNumber = payResponse.outTradeNo
                payInfoDao.save(it)
            }

            //发送MQ消息通知mall
            amqpTemplate.convertAndSend(QUEUE_PAY_NOTIFY, JSON.toJSONString(it))
        }
        
        
        //通知平台
        return when (true) {
            payResponse.payPlatformEnum == BestPayPlatformEnum.WX -> {
                """
                    <xml>
                      <return_code><![CDATA[SUCCESS]]></return_code>
                      <return_msg><![CDATA[OK]]></return_msg>
                    </xml>
                """.trimIndent()
            }
            
            payResponse.payPlatformEnum == BestPayPlatformEnum.ALIPAY -> "success"
            
            else -> throw RuntimeException("付款后异步通知中错误的响应平台")
        }
    }

    /**
     * 通过订单编号查询
     */
    override fun queryByOrderNo(orderNo: String): PayInfo? {
        return payInfoDao.findByOrderNo(orderNo.toLong()).orElse(null)
    }
}