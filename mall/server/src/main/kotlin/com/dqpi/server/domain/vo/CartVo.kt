package com.dqpi.server.domain.vo

import java.math.BigDecimal

data class CartVo(
        var cartProductVoList: List<CartProductVo>? = null,
        
        var selectAll: Boolean? = null,
        
        var cartTotalPrice: BigDecimal? = null,
        
        var cartTotalQuantity: Int? = null
)