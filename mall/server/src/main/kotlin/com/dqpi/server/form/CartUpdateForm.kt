package com.dqpi.server.form

data class CartUpdateForm(
        var quantity: Int = 0,
        var selected: Boolean = false
)