package com.dqpi.server.controller

import com.dqpi.server.consts.MallConsts
import com.dqpi.server.domain.common.ResponseVo
import com.dqpi.server.domain.entity.User
import com.dqpi.server.domain.vo.UserLoginVo
import com.dqpi.server.domain.vo.UserRegisterVo
import com.dqpi.server.enums.ResponseEnum
import com.dqpi.server.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.BeanUtils
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession
import javax.validation.Valid

@RestController
class UserController {
    
    private val log = LoggerFactory.getLogger(this::class.java)
    
    @Resource
    private lateinit var userService: UserService
    
    @PostMapping("/user/register")
    fun register(@Valid @RequestBody userRegisterVo: UserRegisterVo,
                 bindingResult: BindingResult): ResponseVo<String> {
        if (bindingResult.hasErrors()) {
            log.info("注册提交的信息有误, ${bindingResult.fieldError?.field}, " +
                    "${bindingResult.fieldError?.defaultMessage}")
            return ResponseVo.error(ResponseEnum.PARAM_ERROR, bindingResult)
        }
        
        val user = User()
        BeanUtils.copyProperties(userRegisterVo, user)
        return userService.register(user)
    }
    
    @PostMapping("/user/login")
    fun login(@Valid @RequestBody userLoginVo: UserLoginVo,
              bindingResult: BindingResult, 
              httpSession: HttpSession): ResponseVo<User> {
        if (bindingResult.hasErrors()) {
            return ResponseVo.error(ResponseEnum.PARAM_ERROR, bindingResult)
        }
        
        val userResponseVo = userService.login(userLoginVo.username, userLoginVo.password)
            
        //设置session
        log.info("/user/login sessionId=${httpSession.id}")
        httpSession.setAttribute(MallConsts.CURRENT_USER, userResponseVo.data)
            
        return userResponseVo
    }
    
    @GetMapping("/user")
    fun userInfo(httpSession: HttpSession): ResponseVo<User> {
        log.info("/user sessionId=${httpSession.id}")
        val user = httpSession.getAttribute(MallConsts.CURRENT_USER) as User
        return ResponseVo.success(data = user)
    }
    
    @PostMapping("/user/logout")
    fun logout(httpSession: HttpSession): ResponseVo<String> {
        log.info("/user/logout sessionId=${httpSession.id}")
        httpSession.removeAttribute(MallConsts.CURRENT_USER)
        return ResponseVo.success()
    }
}