package com.dqpi.server.service.impl

import com.alibaba.fastjson.JSON
import com.dqpi.server.ServerApplicationTests
import com.dqpi.server.enums.ResponseEnum
import com.dqpi.server.form.CartAddForm
import com.dqpi.server.form.CartUpdateForm
import com.dqpi.server.service.CartService
import org.junit.Assert
import org.junit.Test
import org.slf4j.LoggerFactory

import javax.annotation.Resource

class CartServiceImplTest: ServerApplicationTests() {

    private val log = LoggerFactory.getLogger(this::class.java)
    
    @Resource
    private lateinit var cartService: CartService
    
    @Test
    fun add() {
        val responseVo = cartService.add(1, CartAddForm(26, true))
        Assert.assertEquals(ResponseEnum.SUCCESS.code, responseVo.status)
    }
    
    @Test
    fun list() {
        val responseVo = cartService.list(1)
        Assert.assertEquals(ResponseEnum.SUCCESS.code, responseVo.status)
    }
    
    @Test
    fun update() {
        val responseVo = cartService.update(1, 26, CartUpdateForm(5, false))
        Assert.assertEquals(ResponseEnum.SUCCESS.code, responseVo.status)
    }
    
    @Test
    fun delete() {
        val responseVo = cartService.delete(1, 27)
        Assert.assertEquals(ResponseEnum.SUCCESS.code, responseVo.status)
    }
    
    @Test
    fun selectAll() {
        val responseVo = cartService.selectAll(1)
        Assert.assertEquals(ResponseEnum.SUCCESS.code, responseVo.status)
    }

    @Test
    fun unSelectAll() {
        val responseVo = cartService.unSelectAll(1)
        Assert.assertEquals(ResponseEnum.SUCCESS.code, responseVo.status)
    }

    @Test
    fun sum() {
        val responseVo = cartService.sum(1)
        Assert.assertEquals(ResponseEnum.SUCCESS.code, responseVo.status)
    }   
}