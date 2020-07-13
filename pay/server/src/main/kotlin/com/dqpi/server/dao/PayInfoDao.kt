package com.dqpi.server.dao

import com.dqpi.server.domain.entity.PayInfo
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PayInfoDao: JpaRepository<PayInfo, Int> {
    fun findByOrderNo(orderNo: Long): Optional<PayInfo>
}
