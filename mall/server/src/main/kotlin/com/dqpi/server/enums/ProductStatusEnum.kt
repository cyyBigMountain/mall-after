package com.dqpi.server.enums

/**
 * 商品状态.1-在售 2-下架 3-删除
 */
enum class ProductStatusEnum(val code: Int) {
    ON_SALE(1),
    OFF_SALE(2),
    DELETE(3)
}