package com.dqpi.server.dao

import com.dqpi.server.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserDao: JpaRepository<User, Int> {
    /**
     * 通过用户名查询是否存在
     */
    fun countByUsername(username: String?): Int

    /**
     * 通过邮箱查询是否存在
     */
    fun countByEmail(email: String?): Int

    /**
     * 通过用户名查询用户信息
     */
    fun findByUsername(username: String?): Optional<User>
}