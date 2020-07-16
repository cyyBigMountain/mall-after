package com.dqpi.server.service.impl

import com.dqpi.server.ServerApplicationTests
import com.dqpi.server.dao.PayInfoDao
import com.dqpi.server.domain.entity.PayInfo
import com.dqpi.server.enums.PayPlatformEnum
import com.dqpi.server.service.PayService
import com.lly835.bestpay.enums.BestPayTypeEnum
import org.junit.Test
import java.math.BigDecimal
import javax.annotation.Resource


class PayServiceImplTest: ServerApplicationTests() {
    
    @Resource
    private lateinit var payService: PayService

    @Test
    fun create() {
        payService.create("145641616164161", BigDecimal("0.01"), BestPayTypeEnum.WXPAY_NATIVE)
    }
}