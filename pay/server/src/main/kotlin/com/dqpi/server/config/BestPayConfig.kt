package com.dqpi.server.config

import com.lly835.bestpay.config.AliPayConfig
import com.lly835.bestpay.config.WxPayConfig
import com.lly835.bestpay.service.BestPayService
import com.lly835.bestpay.service.impl.BestPayServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.annotation.Resource

@Configuration
class BestPayConfig {
    
    @Resource
    private lateinit var wxAccountConfig: WxAccountConfig
    
    @Resource
    private lateinit var aliPayAccountConfig: AliPayAccountConfig
    
    @Bean
    fun bestPayService(wxPayConfig: WxPayConfig, aliPayConfig: AliPayConfig): BestPayService {
        val bestPayService = BestPayServiceImpl()
        bestPayService.setWxPayConfig(wxPayConfig)
        bestPayService.setAliPayConfig(aliPayConfig)
        return bestPayService
    }
    
    @Bean
    fun wxPayConfig(): WxPayConfig {
        val wxPayConfig = WxPayConfig()
        wxPayConfig.appId = wxAccountConfig.appId
        wxPayConfig.mchId = wxAccountConfig.mchId
        wxPayConfig.mchKey = wxAccountConfig.mchKey
        wxPayConfig.notifyUrl = wxAccountConfig.notifyUrl
        wxPayConfig.returnUrl = wxAccountConfig.returnUrl
        return wxPayConfig
    }
    
    @Bean
    fun aliPayConfig(): AliPayConfig {
        val aliPayConfig = AliPayConfig()
        aliPayConfig.appId = aliPayAccountConfig.appId
        aliPayConfig.privateKey = aliPayAccountConfig.privateKey
        aliPayConfig.aliPayPublicKey = aliPayAccountConfig.aliPayPublicKey
        aliPayConfig.notifyUrl = aliPayAccountConfig.notifyUrl
        aliPayConfig.returnUrl = aliPayAccountConfig.returnUrl
        return aliPayConfig
    }
}