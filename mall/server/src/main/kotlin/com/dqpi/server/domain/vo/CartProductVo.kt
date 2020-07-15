package com.dqpi.server.domain.vo

import java.math.BigDecimal

data class CartProductVo(
        var productId: Int? = null,
        
        var quantity: Int? = null,

        var productName: String? = null,

        var productSubtitle: String? = null,

        var productMainImage: String? = null,

        var productPrice: BigDecimal? = null,

        var productStatus: Int? = null,

        var productTotalPrice: BigDecimal? = null,

        /**
         * 商品购买数量
         */
        var productStock: Int? = null,
        
        var productSelected: Boolean? = null
)