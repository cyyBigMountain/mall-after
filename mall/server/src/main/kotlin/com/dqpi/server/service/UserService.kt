package com.dqpi.server.service

import com.dqpi.server.domain.common.ResponseVo
import com.dqpi.server.domain.entity.User

interface UserService {
    /**
     * 注册
     */
    fun register(user: User): ResponseVo<String>

    /**
     * 登录
     */
    fun login(username: String?, password: String?): ResponseVo<User>
}