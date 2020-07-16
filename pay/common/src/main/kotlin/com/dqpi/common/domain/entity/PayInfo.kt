package com.dqpi.common.domain.entity

import java.math.BigDecimal
import java.time.LocalDateTime

data class PayInfo(
        /**
         * id
         */
        var id: Int = -1,
        
        /**
         * 用户id
         */
        var userId: Int = -1,

        /**
         * 订单编号
         */
        var orderNo: Long = -1,

        /**
         * 支付平台
         */
        var payPlatform: Int = -1,

        /**
         * 支付流水号
         */
        var platformNumber: String? = null      ,

        /**
         * 支付状态
         */
        var platformStatus: String = "",

        /**
         * 支付金额
         */
        var payAmount: BigDecimal? = null,

        /**
         * 创建时间
         */
        var createTime: LocalDateTime? = null,

        /**
         * 更新时间
         */
        var updateTime: LocalDateTime? = null
)