package com.dqpi.server.controller

import com.dqpi.server.consts.MallConsts
import com.dqpi.server.domain.common.PageVo
import com.dqpi.server.domain.common.ResponseVo
import com.dqpi.server.domain.entity.User
import com.dqpi.server.domain.vo.OrderVo
import com.dqpi.server.form.OrderCreateForm
import com.dqpi.server.service.OrderService
import org.springframework.web.bind.annotation.*
import javax.annotation.Resource
import javax.servlet.http.HttpSession
import javax.validation.Valid

@RestController
class OrderController {
    
    @Resource
    private lateinit var orderService: OrderService
    
    @PostMapping("/orders")
    fun create(@Valid @RequestBody orderCreateForm: OrderCreateForm, 
               httpSession: HttpSession): ResponseVo<OrderVo> {
        val user = httpSession.getAttribute(MallConsts.CURRENT_USER) as User
        return orderService.create(user.id!!, orderCreateForm.shippingId)
    }

    @GetMapping("/orders")
    fun list(@RequestParam(required = false, defaultValue = "1") pageNum: Int,
             @RequestParam(required = false, defaultValue = "10") pageSize: Int,
             httpSession: HttpSession): ResponseVo<PageVo<List<OrderVo>>> {
        val user = httpSession.getAttribute(MallConsts.CURRENT_USER) as User
        return orderService.list(user.id!!, pageNum, pageSize)
    }

    @GetMapping("/orders/{orderNo}")
    fun detail(@PathVariable orderNo: Long, httpSession: HttpSession): ResponseVo<OrderVo> {
        val user = httpSession.getAttribute(MallConsts.CURRENT_USER) as User
        return orderService.detail(user.id!!, orderNo)
    }

    @PutMapping("/orders/{orderNo}")
    fun cancel(@PathVariable orderNo: Long, httpSession: HttpSession): ResponseVo<String> {
        val user = httpSession.getAttribute(MallConsts.CURRENT_USER) as User
        return orderService.cancel(user.id!!, orderNo)
    }
}