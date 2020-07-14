package com.dqpi.server.service

import com.dqpi.server.domain.common.ResponseVo
import com.dqpi.server.domain.vo.CategoryVo

interface CategoryService {
    /**
     * 查询所有类目
     */
    fun findAll(): ResponseVo<List<CategoryVo>>
}