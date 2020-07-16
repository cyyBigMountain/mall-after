package com.dqpi.server.dao

import com.dqpi.server.domain.entity.Shipping
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ShippingDao: JpaRepository<Shipping, Int> {
    /**
     * 根据地址id和用户id删除
     */
    fun deleteByIdAndUserId(id: Int, userId: Int): Int

    /**
     * 根据地址id和用户id查询地址
     */
    fun findByIdAndUserId(id: Int, userId: Int): Optional<Shipping>

    /**
     * 分页查询用户地址列表
     */
    fun findAllByUserId(uid: Int, pageable: Pageable): Page<Shipping>

    /**
     * 根据id集合查询所有地址
     */
    fun findAllByIdIn(idSet: Set<Int>): List<Shipping>
    
    
}