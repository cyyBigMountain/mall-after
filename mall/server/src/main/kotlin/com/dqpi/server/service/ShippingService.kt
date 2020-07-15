package com.dqpi.server.service

import com.dqpi.server.domain.common.PageVo
import com.dqpi.server.domain.common.ResponseVo
import com.dqpi.server.domain.entity.Shipping
import com.dqpi.server.form.ShippingForm

interface ShippingService {
    /**
     * 地址添加
     */
    fun add(uid: Int, shippingForm: ShippingForm): ResponseVo<Map<String, Int>>

    /**
     * 地址删除
     */
    fun delete(uid: Int, shippingId: Int): ResponseVo<String>

    /**
     * 地址更新
     */
    fun update(uid: Int, shippingId: Int, shippingForm: ShippingForm): ResponseVo<String>

    /**
     * 地址列表
     */
    fun list(uid: Int, pageNum: Int, pageSize: Int): ResponseVo<PageVo<List<Shipping>>>
}