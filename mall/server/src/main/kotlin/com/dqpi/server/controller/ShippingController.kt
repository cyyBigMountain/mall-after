package com.dqpi.server.controller

import com.dqpi.server.consts.MallConsts
import com.dqpi.server.domain.common.PageVo
import com.dqpi.server.domain.common.ResponseVo
import com.dqpi.server.domain.entity.Shipping
import com.dqpi.server.domain.entity.User
import com.dqpi.server.form.ShippingForm
import com.dqpi.server.service.ShippingService
import org.springframework.web.bind.annotation.*
import javax.annotation.Resource
import javax.servlet.http.HttpSession
import javax.validation.Valid

@RestController
class ShippingController {
    
    @Resource
    private lateinit var shippingService: ShippingService
    
    @PostMapping("/shippings")
    fun add(@Valid @RequestBody shippingForm: ShippingForm, httpSession: HttpSession): ResponseVo<Map<String, Int>> {
        val user = httpSession.getAttribute(MallConsts.CURRENT_USER) as User
        return shippingService.add(user.id!!, shippingForm)
    }
    
    @DeleteMapping("/shippings/{shippingId}")
    fun delete(@PathVariable shippingId: Int, httpSession: HttpSession): ResponseVo<String> {
        val user = httpSession.getAttribute(MallConsts.CURRENT_USER) as User
        return shippingService.delete(user.id!!, shippingId)
    }

    @PutMapping("/shippings/{shippingId}")
    fun update(@PathVariable shippingId: Int,
               @Valid @RequestBody shippingForm: ShippingForm,
               httpSession: HttpSession): ResponseVo<String> {
        val user = httpSession.getAttribute(MallConsts.CURRENT_USER) as User
        return shippingService.update(user.id!!, shippingId, shippingForm)
    }

    @GetMapping("/shippings")
    fun list(@RequestParam(required = false, defaultValue = "1") pageNum: Int,
             @RequestParam(required = false, defaultValue = "10") pageSize: Int,
             httpSession: HttpSession): ResponseVo<PageVo<List<Shipping>>> {
        val user = httpSession.getAttribute(MallConsts.CURRENT_USER) as User
        return shippingService.list(user.id!!, pageNum, pageSize)
    }
}