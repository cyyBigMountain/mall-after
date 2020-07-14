package com.dqpi.server.dao

import com.dqpi.server.domain.entity.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryDao: JpaRepository<Category, Int>