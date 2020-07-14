package com.dqpi.server.controller

import com.dqpi.server.domain.common.ResponseVo
import com.dqpi.server.domain.vo.CategoryVo
import com.dqpi.server.service.CategoryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.Resource

@RestController
class CategoryController {
    
    @Resource
    private lateinit var categoryService: CategoryService
    
    @GetMapping("/categories")
    fun findAll(): ResponseVo<List<CategoryVo>> {
        return categoryService.findAll()
    }
    
}