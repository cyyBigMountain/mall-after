package com.dqpi.server.domain.vo

import javax.validation.constraints.NotBlank

data class UserLoginVo(
        @field:NotBlank
        var username: String?,

        @field:NotBlank
        var password: String?
)