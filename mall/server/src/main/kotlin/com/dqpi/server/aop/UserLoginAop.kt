package com.dqpi.server.aop

import com.dqpi.server.consts.MallConsts
import com.dqpi.server.domain.entity.User
import com.dqpi.server.exception.UserLoginException
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Aspect
@Component
class UserLoginAop {
    
    private val log = LoggerFactory.getLogger(this::class.java)
    
    @Pointcut("!execution(* com..controller.UserController.login(..)) " +
            "&& !execution(* com..controller.UserController.register(..)) " +
            "&& !execution(* com..controller.CategoryController.findAll(..)) " +
            "&& !execution(* com..controller.ProductController.*(..))")
    private fun excludePointcut(){}

    @Before("excludePointcut() && execution(* com..controller.*.*(..))")
    fun userLoginValid() {
        log.info("正在拦截请求，并进行登录验证...")
        val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
        request.session.getAttribute(MallConsts.CURRENT_USER) as User? 
                ?: throw UserLoginException()
    }
}