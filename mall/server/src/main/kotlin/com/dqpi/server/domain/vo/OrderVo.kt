package com.dqpi.server.domain.vo

import com.dqpi.server.domain.entity.Shipping
import java.math.BigDecimal
import java.time.LocalDateTime


data class OrderVo(
        /**
         * 订单编号
         */
        var orderNo: Long? = null,

        /**
         * 实际付款金额
         */
        var payment: BigDecimal? = null,

        /**
         * 支付类型
         */
        var paymentType: Int? = null,

        /**
         * 邮费
         */
        var postage: Int? = null,

        /**
         * 订单状态
         */
        var status: Int? = null,

        /**
         * 支付时间
         */
        var paymentTime: LocalDateTime? = null,

        /**
         * 发货时间
         */
        var sendTime: LocalDateTime? = null,

        /**
         * 交易完成时间
         */
        var endTime: LocalDateTime? = null,

        /**
         * 交易关闭时间
         */
        var closeTime: LocalDateTime? = null,

        /**
         * 创建时间
         */
        var createTime: LocalDateTime? = null,
        
        var orderItemVoList: List<OrderItemVo>? = null,
        
        var shippingId: Int? = null,
        
        var shippingVo: Shipping? = null
)