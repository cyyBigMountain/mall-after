package com.dqpi.server.exceptionhandle

import com.dqpi.server.domain.common.ResponseVo
import com.dqpi.server.enums.ResponseEnum
import com.dqpi.server.exception.UserLoginException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import java.util.*

@ControllerAdvice
class RuntimeExceptionHandle {
    
    @ExceptionHandler(RuntimeException::class)
    @ResponseBody
    fun handle(e: RuntimeException): ResponseVo<String> {
        return ResponseVo.error(ResponseEnum.ERROR, e.message.toString())
    }   
    
    
    @ExceptionHandler(UserLoginException::class)
    @ResponseBody
    fun handle(e: UserLoginException): ResponseVo<String> {
        return ResponseVo.error(ResponseEnum.NEED_LOGIN)
    }
    
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseBody
    fun handle(e: MethodArgumentNotValidException): ResponseVo<String> {
        val bindingResult = e.bindingResult
        return ResponseVo.error(ResponseEnum.PARAM_ERROR,
        "${bindingResult.fieldError!!.field} ${bindingResult.fieldError!!.defaultMessage}")
    }
}