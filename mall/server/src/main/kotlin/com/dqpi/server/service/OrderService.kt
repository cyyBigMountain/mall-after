package com.dqpi.server.service

import com.dqpi.server.domain.common.PageVo
import com.dqpi.server.domain.common.ResponseVo
import com.dqpi.server.domain.vo.OrderItemVo
import com.dqpi.server.domain.vo.OrderVo

interface OrderService {
    
    /**
     * 创建订单
     */
    fun create(uid: Int, shippingId: Int): ResponseVo<OrderVo>

    /**
     * 分页查询订单列表
     */
    fun list(uid: Int, pageNum: Int, pageSize: Int): ResponseVo<PageVo<List<OrderVo>>>

    /**
     * 订单详情
     */
    fun detail(uid: Int, orderNo: Long): ResponseVo<OrderVo>

    /**
     * 取消订单
     */
    fun cancel(uid: Int, orderNo: Long): ResponseVo<String>
}