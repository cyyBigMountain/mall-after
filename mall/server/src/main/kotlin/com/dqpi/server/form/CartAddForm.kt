package com.dqpi.server.form

import javax.validation.constraints.NotNull

data class CartAddForm(
        @field:NotNull
        var productId: Int? = null,
        
        var selected: Boolean = true
)