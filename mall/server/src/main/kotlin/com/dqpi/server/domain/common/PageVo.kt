package com.dqpi.server.domain.common

data class PageVo<T>(
        var pageNum: Int? = null,
        var pageSize: Int? = null,
        var totalElements: Long? = null,
        var data: T? = null
)