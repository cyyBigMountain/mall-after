package com.dqpi.server.service

import com.dqpi.server.domain.common.ResponseVo
import com.dqpi.server.domain.vo.CartVo
import com.dqpi.server.form.CartAddForm
import com.dqpi.server.form.CartUpdateForm

interface CartService {
    /**
     * 购物车添加商品
     */
    fun add(uid: Int, cartAddForm: CartAddForm): ResponseVo<CartVo>

    /**
     * 查询购物车商品
     */
    fun list(uid: Int): ResponseVo<CartVo>

    /**
     * 更新购物车商品
     */
    fun update(uid: Int, productId: Int, cartUpdateForm: CartUpdateForm): ResponseVo<CartVo>

    /**
     * 删除购物车商品
     */
    fun delete(uid: Int, productId: Int): ResponseVo<CartVo>

    /**
     * 全选
     */
    fun selectAll(uid: Int): ResponseVo<CartVo>

    /**
     * 全不选
     */
    fun unSelectAll(uid: Int): ResponseVo<CartVo>

    /**
     * 商品总数
     */
    fun sum(uid: Int): ResponseVo<Int>
}