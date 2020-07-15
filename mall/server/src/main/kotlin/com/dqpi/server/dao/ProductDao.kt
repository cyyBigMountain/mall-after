package com.dqpi.server.dao

import com.dqpi.server.consts.MallConsts
import com.dqpi.server.domain.entity.Product
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.domain.Pageable


interface ProductDao: JpaRepository<Product, Int> {

    /**
     * 查询所有id集合内的产品, 默认所有在售状态
     */
    fun findAllByCategoryIdInAndStatus(categoryIdSet: Set<Int?>, 
                                       status: Int = MallConsts.ON_SALE,
                                       pageable: Pageable): Page<Product>
}