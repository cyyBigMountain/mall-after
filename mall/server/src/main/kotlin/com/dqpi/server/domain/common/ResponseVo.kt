package com.dqpi.server.domain.common

import com.dqpi.server.enums.ResponseEnum
import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.validation.BindingResult

@JsonInclude(JsonInclude.Include.NON_NULL)
class ResponseVo<T>(
        val status: Int,
        val message: String? = null,
        val data: T? = null
) {
    companion object {
        fun <T>success(message: String): ResponseVo<T> {
            return ResponseVo(ResponseEnum.SUCCESS.code, message = message)
        }
        
        fun <T>success(data: T): ResponseVo<T> {
            return ResponseVo(ResponseEnum.SUCCESS.code, data = data)
        }

        fun <T>success(): ResponseVo<T> {
            return ResponseVo(ResponseEnum.SUCCESS.code, message = ResponseEnum.SUCCESS.message)
        }
        
        fun <T>error(responseEnum: ResponseEnum): ResponseVo<T> {
            return ResponseVo(responseEnum.code, message = responseEnum.message)
        }

        fun <T>error(responseEnum: ResponseEnum, message: String): ResponseVo<T> {
            return ResponseVo(responseEnum.code, message = message)
        }

        fun <T>error(responseEnum: ResponseEnum, bindingResult: BindingResult): ResponseVo<T> {
            return ResponseVo(responseEnum.code, 
            message = "${bindingResult.fieldError?.field} ${bindingResult.fieldError?.defaultMessage}")
        }
    }
}