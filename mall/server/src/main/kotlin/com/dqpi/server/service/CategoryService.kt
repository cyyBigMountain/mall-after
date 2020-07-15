package com.dqpi.server.service

import com.dqpi.server.domain.common.ResponseVo
import com.dqpi.server.domain.vo.CategoryVo

interface CategoryService {
    /**
     * 查询所有类目
     */
    fun findAll(): ResponseVo<List<CategoryVo>>

    /**
     * 查询所有子类目id
     */
    fun findSubCategoryId(id: Int, resultSet: HashSet<Int?>)
}