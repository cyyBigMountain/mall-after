package com.dqpi.server.listener

import com.alibaba.fastjson.JSON
import com.dqpi.common.domain.entity.PayInfo
import com.dqpi.server.service.OrderService
import com.lly835.bestpay.enums.OrderStatusEnum
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import javax.annotation.Resource

@Component
@RabbitListener(queues = ["payNotify"])
class PayMsgListener {
    
    private val log = LoggerFactory.getLogger(this::class.java)
    
    @Resource
    private lateinit var orderService: OrderService
    
    @RabbitHandler
    fun process(msg: String) {
        log.info("接收到消息 => $msg")

        val payInfo = JSON.parseObject(msg, PayInfo::class.java)
        if (payInfo.platformStatus == OrderStatusEnum.SUCCESS.name) {
            //修改订单状态
            orderService.paid(payInfo.orderNo)
        }
    }
}