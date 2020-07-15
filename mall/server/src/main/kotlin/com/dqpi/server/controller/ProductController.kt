package com.dqpi.server.controller

import com.dqpi.server.domain.common.PageVo
import com.dqpi.server.domain.common.ResponseVo
import com.dqpi.server.domain.vo.ProductDetailVo
import com.dqpi.server.domain.vo.ProductVo
import com.dqpi.server.service.ProductService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.annotation.Resource

@RestController
class ProductController {
    
    @Resource
    private lateinit var productService: ProductService
    
    @GetMapping("/products")
    fun list(@RequestParam(required = false) categoryId: Int?,
             @RequestParam(required = false, defaultValue = "1") pageNum: Int,
             @RequestParam(required = false, defaultValue = "10") pageSize: Int)
             :ResponseVo<PageVo<List<ProductVo>>> {
        return productService.list(categoryId, pageNum, pageSize) 
    }
    
    @GetMapping("/products/{productId}")
    fun detail(@PathVariable productId: Int): ResponseVo<ProductDetailVo> {
        return productService.detail(productId)
    }
}