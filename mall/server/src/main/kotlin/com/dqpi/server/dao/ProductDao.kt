package com.dqpi.server.dao

import com.dqpi.server.consts.MallConsts
import com.dqpi.server.domain.entity.Product
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.domain.Pageable


interface ProductDao: JpaRepository<Product, Int> {

    /**
     * 查询所有目录id集合内的产品, 默认所有在售状态
     */
    fun findAllByCategoryIdInAndStatus(categoryIdSet: Set<Int?>, 
                                       status: Int = MallConsts.ON_SALE,
                                       pageable: Pageable): Page<Product>

    /**
     * 查询所有商品id列表内的产品, 默认所有在售状态
     */
    fun findAllByIdInAndStatus(ids: List<Int>, 
                               status: Int = MallConsts.ON_SALE): List<Product>


    /**
     * 查询所有商品id列表内的产品
     */
    fun findAllByIdIn(ids: List<Int>): List<Product>
}