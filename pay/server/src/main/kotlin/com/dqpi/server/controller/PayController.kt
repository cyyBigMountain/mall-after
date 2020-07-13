package com.dqpi.server.controller

import com.dqpi.server.domain.entity.PayInfo
import com.dqpi.server.service.PayService
import com.lly835.bestpay.config.WxPayConfig
import com.lly835.bestpay.enums.BestPayTypeEnum
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import java.math.BigDecimal
import java.util.HashMap
import javax.annotation.Resource

@Controller
@RequestMapping("/pay")
class PayController {
    private val log = LoggerFactory.getLogger(this::class.java)
    
    @Resource
    private lateinit var payService: PayService
    
    @Resource
    private lateinit var wxPayConfig: WxPayConfig
    
    @GetMapping("/create")
    fun create(orderNo: String, amount: BigDecimal, payType: BestPayTypeEnum): ModelAndView {
        val returnData = HashMap<String, String>()
        val payResponse = payService.create(orderNo, amount, payType)
        
        return when (true) {
            payType == BestPayTypeEnum.WXPAY_NATIVE -> {
                returnData["codeUrl"] = payResponse.codeUrl
                returnData["orderNo"] = orderNo
                returnData["returnUrl"] = wxPayConfig.returnUrl
                ModelAndView("createForWxNative", returnData)
            }
            
            payType == BestPayTypeEnum.ALIPAY_PC -> {
                returnData["body"] = payResponse.body
                ModelAndView("createForAlipayPC", returnData)
            }
            
            else -> throw RuntimeException("暂不支持的支付类型")
        }
    }
    
    @PostMapping("/notify")
    @ResponseBody
    fun asyncNotify(@RequestBody notifyData: String): String {
        return payService.asyncNotify(notifyData)
    }
    
    @GetMapping("/queryByOrderNo")
    @ResponseBody
    fun queryByOrderNo(orderNo: String): PayInfo? {
        log.info("查询支付记录...")
        return payService.queryByOrderNo(orderNo)
    }
}