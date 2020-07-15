package com.dqpi.server.controller

import com.dqpi.server.consts.MallConsts
import com.dqpi.server.domain.common.ResponseVo
import com.dqpi.server.domain.entity.User
import com.dqpi.server.domain.vo.CartVo
import com.dqpi.server.form.CartAddForm
import com.dqpi.server.form.CartUpdateForm
import com.dqpi.server.service.CartService
import org.springframework.web.bind.annotation.*
import javax.annotation.Resource
import javax.servlet.http.HttpSession
import javax.validation.Valid

@RestController
class CartController {
    
    @Resource
    private lateinit var cartService: CartService
    
    @GetMapping("/carts")
    fun list(httpSession: HttpSession): ResponseVo<CartVo> {
        val user = httpSession.getAttribute(MallConsts.CURRENT_USER) as User
        return cartService.list(user.id!!)
    }

    @PostMapping("/carts")
    fun add(@Valid @RequestBody cartAddForm: CartAddForm, httpSession: HttpSession): ResponseVo<CartVo> {
        val user = httpSession.getAttribute(MallConsts.CURRENT_USER) as User
        return cartService.add(user.id!!, cartAddForm)
    }

    @PutMapping("/carts/{productId}")
    fun update(@Valid @RequestBody cartUpdateForm: CartUpdateForm,
               @PathVariable productId: Int,
               httpSession: HttpSession): ResponseVo<CartVo> {
        val user = httpSession.getAttribute(MallConsts.CURRENT_USER) as User
        return cartService.update(user.id!!, productId, cartUpdateForm)
    }

    @DeleteMapping("/carts/{productId}")
    fun delete(@PathVariable productId: Int, httpSession: HttpSession): ResponseVo<CartVo> {
        val user = httpSession.getAttribute(MallConsts.CURRENT_USER) as User
        return cartService.delete(user.id!!, productId)
    }

    @PutMapping("/carts/selectAll")
    fun selectAll(httpSession: HttpSession): ResponseVo<CartVo> {
        val user = httpSession.getAttribute(MallConsts.CURRENT_USER) as User
        return cartService.selectAll(user.id!!)
    }

    @PutMapping("/carts/unSelectAll")
    fun unSelectAll(httpSession: HttpSession): ResponseVo<CartVo> {
        val user = httpSession.getAttribute(MallConsts.CURRENT_USER) as User
        return cartService.unSelectAll(user.id!!)
    }

    @GetMapping("/carts/products/sum")
    fun sum(httpSession: HttpSession): ResponseVo<Int> {
        val user = httpSession.getAttribute(MallConsts.CURRENT_USER) as User
        return cartService.sum(user.id!!)
    }
}