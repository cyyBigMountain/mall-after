package com.dqpi.server.domain.vo

import javax.validation.constraints.NotBlank

data class UserRegisterVo(
        @field:NotBlank
        var username: String?,

        @field:NotBlank
        var password: String?,

        @field:NotBlank
        var email: String?
)