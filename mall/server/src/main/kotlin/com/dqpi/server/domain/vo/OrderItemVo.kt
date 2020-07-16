package com.dqpi.server.domain.vo

import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderItemVo(
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
        var createTime: LocalDateTime? = null
)