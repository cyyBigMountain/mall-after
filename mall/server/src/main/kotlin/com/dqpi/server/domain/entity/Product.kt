package com.dqpi.server.domain.entity

import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Product(
        /**
         * id
         */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,

        /**
         * 目录id
         */
        var categoryId: Int? = null,

        /**
         * 产品名
         */
        var name: String? = null,

        /**
         * 子标题
         */
        var subtitle: String? = null,

        /**
         * 主图，url地址
         */
        var mainImage: String? = null,

        /**
         * 子图，json格式，拓展用
         */
        var subImages: String? = null,

        /**
         * 产品详情
         */
        var detail: String? = null,

        /**
         * 产品价格
         */
        var price: BigDecimal? = null,

        /**
         * 库存
         */
        var stock: Int? = null,

        /**
         * 商品状态.1-在售 2-下架 3-删除
         */
        var status: Int? = null,

        /**
         * 创建时间
         */
        var createTime: LocalDateTime? = null,

        /**
         * 修改时间
         */
        var updateTime: LocalDateTime? = null
)