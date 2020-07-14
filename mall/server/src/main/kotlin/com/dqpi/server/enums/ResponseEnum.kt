package com.dqpi.server.enums

enum class ResponseEnum(
        val code: Int,
        val message: String
) {
    ERROR(-1, "服务端错误"),
    SUCCESS(0, "成功"),
    PASSWORD_ERROR(1, "密码错误"),
    USERNAME_EXIST(2, "用户名已存在"),
    PARAM_ERROR(3, "参数错误"),
    EMAIL_EXIST(4, "邮箱已存在"),
    NEED_LOGIN(10, "用户未登录，请先登录"),
    USERNAME_OR_PASSWORD_ERROR(11, "用户名或密码错误")
}