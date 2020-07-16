package com.dqpi.server.form

import javax.validation.constraints.NotNull

data class OrderCreateForm(
        @field:NotNull
        var shippingId: Int
)