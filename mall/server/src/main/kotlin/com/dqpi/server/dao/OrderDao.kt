package com.dqpi.server.dao

import com.dqpi.server.domain.entity.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderDao: JpaRepository<Order, Int>