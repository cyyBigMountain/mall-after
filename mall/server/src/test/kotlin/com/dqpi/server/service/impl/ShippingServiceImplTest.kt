package com.dqpi.server.service.impl

import com.dqpi.server.ServerApplicationTests
import com.dqpi.server.enums.ResponseEnum
import com.dqpi.server.form.ShippingForm
import com.dqpi.server.service.ShippingService
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import javax.annotation.Resource

class ShippingServiceImplTest: ServerApplicationTests() {

    @Resource
    private lateinit var shippingService: ShippingService
    
    private val uid = 1
    
    private val shippingId = 7
    
    private lateinit var shippingForm: ShippingForm
    
    @Before
    fun before(){
        shippingForm = ShippingForm("mountain", "18078938073", "15765983348", "杭州", "杭州", "北京", "123456", "123456")
    }
    
    @Test
    fun add() {
        val responseVo = shippingService.add(uid, shippingForm)
        Assert.assertEquals(ResponseEnum.SUCCESS.code, responseVo.status)
    }

    @Test
    fun delete() {
        val responseVo = shippingService.delete(uid, shippingId)
        Assert.assertEquals(ResponseEnum.SUCCESS.code, responseVo.status)
    }

    @Test
    fun update() {
        shippingForm.receiverZip = "666666"
        val responseVo = shippingService.update(uid, shippingId, shippingForm)
        Assert.assertEquals(ResponseEnum.SUCCESS.code, responseVo.status)
    }

    @Test
    fun list() {
        val responseVo = shippingService.list(uid, 1, 10)
        Assert.assertEquals(ResponseEnum.SUCCESS.code, responseVo.status)
    }
}