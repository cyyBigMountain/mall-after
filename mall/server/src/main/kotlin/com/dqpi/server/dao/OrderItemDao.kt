package com.dqpi.server.dao

import com.dqpi.server.domain.entity.OrderItem
import org.springframework.data.jpa.repository.JpaRepository

interface OrderItemDao: JpaRepository<OrderItem, Int>