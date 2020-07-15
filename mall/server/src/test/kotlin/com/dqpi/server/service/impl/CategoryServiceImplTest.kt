package com.dqpi.server.service.impl

import com.dqpi.server.ServerApplicationTests
import com.dqpi.server.enums.ResponseEnum
import com.dqpi.server.service.CategoryService
import org.junit.Assert
import org.junit.Test
import org.slf4j.LoggerFactory

import javax.annotation.Resource

class CategoryServiceImplTest: ServerApplicationTests() {
    
    private val log = LoggerFactory.getLogger(this::class.java)
    
    @Resource
    private lateinit var categoryService: CategoryService

    @Test
    fun findAll() {
        val responseVo = categoryService.findAll()
        Assert.assertEquals(ResponseEnum.SUCCESS.code, responseVo.status)
    }
    
    @Test
    fun findSubCategoryId(){
        val set = HashSet<Int?>()
        categoryService.findSubCategoryId(100001, set)
        log.info("set=$set")
    }
}