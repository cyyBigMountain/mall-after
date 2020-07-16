package com.dqpi.server.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration


@Configuration
@ConfigurationProperties(prefix = "wx")
class WxAccountConfig{
    lateinit var appId: String
    lateinit var mchId: String
    lateinit var mchKey: String
    lateinit var notifyUrl: String
    lateinit var returnUrl: String
}