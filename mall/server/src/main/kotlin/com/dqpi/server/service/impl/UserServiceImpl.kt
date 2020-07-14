package com.dqpi.server.service.impl

import com.dqpi.server.dao.UserDao
import com.dqpi.server.domain.common.ResponseVo
import com.dqpi.server.domain.entity.User
import com.dqpi.server.enums.ResponseEnum
import com.dqpi.server.enums.RoleEnum
import com.dqpi.server.service.UserService
import org.springframework.stereotype.Service
import org.springframework.util.DigestUtils
import javax.annotation.Resource

@Service
class UserServiceImpl: UserService {
    
    @Resource
    private lateinit var userDao: UserDao
    
    /**
     * 注册
     */
    override fun register(user: User): ResponseVo<String> {
        //用户名判断是否存在
        val countByUsername = userDao.countByUsername(user.username)
        require(countByUsername <= 0) { 
            return ResponseVo.error(ResponseEnum.USERNAME_EXIST)
        }

        //邮箱判断是否存在
        val countByEmail = userDao.countByEmail(user.email)
        require(countByEmail <= 0) {
            return ResponseVo.error(ResponseEnum.EMAIL_EXIST)
        }

        user.role = RoleEnum.CUSTOMER.code
        //MD5摘要算法(Spring自带)
        user.password = DigestUtils.md5DigestAsHex(user.password!!.toByteArray())
        
        //写入数据库
        try {
            userDao.save(user)
        }catch (e: Exception) {
            return ResponseVo.error(ResponseEnum.ERROR)
        }
        
        return ResponseVo.success()
    }

    /**
     * 登录
     */
    override fun login(username: String?, password: String?): ResponseVo<User> {
        val userOptional = userDao.findByUsername(username)
        userOptional.orElse(null).let {
            return when (true) {
                //用户不存在
                it == null -> ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR)

                //密码错误
                !it.password.equals(DigestUtils.md5DigestAsHex(password!!.toByteArray()),
                          true) -> {
                    ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR)
                }

                else -> { it.password = ""; ResponseVo.success(data = it) }
            }
        }
    }

}