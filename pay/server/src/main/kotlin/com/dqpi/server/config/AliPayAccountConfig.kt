package com.dqpi.server.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "alipay")
class AliPayAccountConfig {
    lateinit var appId: String
    lateinit var privateKey: String
    lateinit var aliPayPublicKey: String
    lateinit var notifyUrl: String
    lateinit var returnUrl: String
}