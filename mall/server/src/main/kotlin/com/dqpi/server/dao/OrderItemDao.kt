package com.dqpi.server.dao

import com.dqpi.server.domain.entity.OrderItem
import org.springframework.data.jpa.repository.JpaRepository

interface OrderItemDao: JpaRepository<OrderItem, Int> {
    /**
     * 根据订单编号集合查询订单列表
     */
    fun findAllByOrderNoIn(orderNoSet: Set<Long>): List<OrderItem>
}