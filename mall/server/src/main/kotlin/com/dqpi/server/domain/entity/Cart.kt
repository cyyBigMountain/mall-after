package com.dqpi.server.domain.entity


data class Cart(
        var productId: Int? = null,
        
        var quantity: Int? = null,
        
        var productSelected: Boolean = false
)