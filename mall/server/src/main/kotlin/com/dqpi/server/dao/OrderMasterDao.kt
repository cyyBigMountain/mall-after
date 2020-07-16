package com.dqpi.server.dao

import com.dqpi.server.domain.entity.OrderMaster
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface OrderMasterDao: JpaRepository<OrderMaster, Int> {
    /**
     * 根据用户id分页查询所有订单
     */
    fun findAllByUserId(uid: Int, pageable: Pageable): Page<OrderMaster>

    /**
     * 根据订单编号查询订单详情
     */
    fun findByOrderNo(orderNo: Long): Optional<OrderMaster>
}