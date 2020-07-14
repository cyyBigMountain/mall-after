package com.dqpi.server.domain.vo

data class CategoryVo(
        /**
         * id
         */
        var id: Int? = null,

        /**
         * 父目录id
         */
        var parentId: Int? = null,

        /**
         * 类目名
         */
        var name: String? = null,

        /**
         * 排序编号
         */
        var sortOrder: Int? = null,

        /**
         * 子目录
         */
        var subCategories: List<CategoryVo>? = null
)