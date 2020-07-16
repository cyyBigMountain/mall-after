package com.dqpi.server.domain.entity

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@DynamicUpdate
@DynamicInsert
data class OrderMaster(
        /**
         * id
         */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,

        /**
         * 订单编号
         */
        var orderNo: Long? = null,

        /**
         * 用户id
         */
        var userId: Int? = null,

        /**
         * 运输id
         */
        var shippingId: Int? = null,

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

        /**
         * 修改时间
         */
        var updateTime: LocalDateTime? = null
)