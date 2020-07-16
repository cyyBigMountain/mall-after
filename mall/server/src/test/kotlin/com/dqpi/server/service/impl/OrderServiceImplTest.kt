package com.dqpi.server.service.impl

import com.alibaba.fastjson.JSON
import com.dqpi.server.ServerApplicationTests
import com.dqpi.server.enums.ResponseEnum
import com.dqpi.server.service.OrderService
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.slf4j.LoggerFactory
import javax.annotation.Resource

class OrderServiceImplTest: ServerApplicationTests() {

    private val log = LoggerFactory.getLogger(this::class.java)
    
    @Resource
    private lateinit var orderService: OrderService

    private val uid = 1
    private val shippingId = 7
    
    @Test
    fun create() {
        val responseVo = orderService.create(uid, shippingId)
        log.info(JSON.toJSONString(responseVo, true))
        assertEquals(ResponseEnum.SUCCESS.code, responseVo.status)
    }
    
    @Test
    fun list(){
        val responseVo = orderService.list(uid, 1, 2)
        log.info(JSON.toJSONString(responseVo, true))
        assertEquals(ResponseEnum.SUCCESS.code, responseVo.status)
    }

    @Test
    fun detail(){
        val responseVo = orderService.detail(uid, 1594872489743)
        log.info(JSON.toJSONString(responseVo, true))
        assertEquals(ResponseEnum.SUCCESS.code, responseVo.status)
    }
    
    @Test
    fun cancel() {
        val responseVo = orderService.cancel(uid, 1594872489743)
        log.info(JSON.toJSONString(responseVo, true))
        assertEquals(ResponseEnum.SUCCESS.code, responseVo.status)
    }
}