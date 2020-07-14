package com.dqpi.server.service.impl

import com.dqpi.server.ServerApplicationTests
import com.dqpi.server.domain.entity.User
import com.dqpi.server.enums.ResponseEnum
import com.dqpi.server.enums.RoleEnum
import com.dqpi.server.service.UserService
import org.junit.Assert
import org.junit.Test

import org.springframework.transaction.annotation.Transactional
import javax.annotation.Resource

@Transactional
class UserServiceImplTest: ServerApplicationTests() {
    
    companion object {
        const val USERNAME = "jack"
        const val PASSWORD = "jack"
    }
    
    @Resource
    private lateinit var userService: UserService

    @Test
    fun register() {
        val user = User(username = USERNAME, 
                        password = PASSWORD, 
                        email = "jack@qq.com",
                        role = RoleEnum.CUSTOMER.code)
        userService.register(user)
    }
    
    @Test
    fun login(){
        val responseVo = userService.login(USERNAME, PASSWORD)
        Assert.assertEquals(ResponseEnum.SUCCESS.code, responseVo.status)
    }
}