package com.dqpi.server.domain.vo

import java.math.BigDecimal


data class ProductVo(
        var id: Int? = null,
        var categoryId: Int? = null,
        var name: String? = null,
        var subtitle: String? = null,
        var mainImage: String? = null,
        var status: Int? = null,
        var price: BigDecimal? = null
)