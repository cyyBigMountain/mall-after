package com.dqpi.server.service.impl

import com.dqpi.server.ServerApplicationTests
import com.dqpi.server.enums.ResponseEnum
import com.dqpi.server.service.ProductService
import org.junit.Assert
import org.junit.Test

import javax.annotation.Resource

class ProductServiceImplTest: ServerApplicationTests() {

    @Resource
    private lateinit var productService: ProductService
    
    @Test
    fun list() {
        val responseVo = productService.list(null, 2, 3)
        Assert.assertEquals(ResponseEnum.SUCCESS.code, responseVo.status)
    }
    
    @Test
    fun detail() {
        val responseVo = productService.detail(26)
        Assert.assertEquals(ResponseEnum.SUCCESS.code, responseVo.status)   
    }
}