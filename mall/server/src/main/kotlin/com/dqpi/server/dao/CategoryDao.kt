package com.dqpi.server.dao

import com.dqpi.server.domain.entity.Category
import org.springframework.data.jpa.repository.JpaRepository


interface CategoryDao: JpaRepository<Category, Int> {
    /**
     * 根据类目状态查询所有类目, 默认查询所有正常状态的类目
     */
    fun findAllByStatus(status: Int = 1): List<Category>
}