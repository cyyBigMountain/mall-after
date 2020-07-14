package com.dqpi.server.domain.entity

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
@DynamicInsert
@DynamicUpdate
data class User(
        /**
         * id
         */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,

        /**
         * 用户名
         */
        var username: String? = null,

        /**
         * 用户密码
         */
        var password: String? = null,

        /**
         * 用户邮箱
         */
        var email: String? = null,

        /**
         * 用户电话
         */
        var phone: String? = null,

        /**
         * 找回密码问题
         */
        var question: String? = null,

        /**
         * 找回密码答案
         */
        var answer: String? = null,

        /**
         * 用户角色
         */
        var role: Int? = null,

        /**
         * 创建时间
         */
        var createTime: LocalDateTime? = null,

        /**
         * 修改时间
         */
        var updateTime: LocalDateTime? = null
)

