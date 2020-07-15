package com.dqpi.server.service

import com.dqpi.server.domain.common.PageVo
import com.dqpi.server.domain.common.ResponseVo
import com.dqpi.server.domain.vo.ProductDetailVo
import com.dqpi.server.domain.vo.ProductVo

interface ProductService {
    
    /**
     * 分页查询所有商品列表
     */
    fun list(categoryId: Int?, pageNum: Int, pageSize: Int): ResponseVo<PageVo<List<ProductVo>>>

    /**
     * 查询商品详情
     */
    fun detail(productId: Int): ResponseVo<ProductDetailVo>
}