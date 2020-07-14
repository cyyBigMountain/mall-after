package com.dqpi.server.domain.entity

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
@DynamicUpdate
@DynamicInsert
data class Category(
        /**
         * id
         */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,

        /**
         * 父目录id
         */
        var parentId: Int? = null,

        /**
         * 目录名
         */
        var name: String? = null,

        /**
         * 目录状态
         */
        var status: Boolean? = null,

        /**
         * 订单排序
         */
        var sortOrder: Int? = null,

        /**
         * 创建时间
         */
        var createTime: LocalDateTime? = null,

        /**
         * 修改时间
         */
        var updateTime: LocalDateTime? = null     
) 