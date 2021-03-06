package com.dqpi.server.domain.entity

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
@DynamicUpdate
@DynamicInsert
data class OrderItem(
        /**
         * id
         */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,

        /**
         * 用户id
         */
        var userId: Int? = null,

        /**
         * 订单编号
         */
        var orderNo: Long? = null,

        /**
         * 产品id
         */
        var productId: Int? = null,

        /**
         * 产品名
         */
        var productName: String? = null,

        /**
         * 产品图片
         */
        var productImage: String? = null,

        /**
         * 生成订单时单价
         */
        var currentUnitPrice: BigDecimal? = null,

        /**
         * 订单数量
         */
        var quantity: Int? = null,

        /**
         * 总价
         */
        var totalPrice: BigDecimal? = null,

        /**
         * 创建时间
         */
        var createTime: LocalDateTime? = null,

        /**
         * 修改时间
         */
        var updateTime: LocalDateTime? = null
)
