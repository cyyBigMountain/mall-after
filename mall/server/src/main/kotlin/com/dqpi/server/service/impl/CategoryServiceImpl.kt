package com.dqpi.server.service.impl

import com.dqpi.server.consts.MallConsts
import com.dqpi.server.dao.CategoryDao
import com.dqpi.server.domain.common.ResponseVo
import com.dqpi.server.domain.entity.Category
import com.dqpi.server.domain.vo.CategoryVo
import com.dqpi.server.service.CategoryService
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Service
import javax.annotation.Resource

@Service
class CategoryServiceImpl: CategoryService {
    
    @Resource
    private lateinit var categoryDao: CategoryDao
    
    /**
     * 查询所有类目
     */
    override fun findAll(): ResponseVo<List<CategoryVo>> {
        val categories = categoryDao.findAllByStatus()

        //查询所有根目录
        val categoryRootList = categories.asSequence()
                .filter { it.parentId == MallConsts.ROOT_PARENT_ID }
                .map(::categoryToCategoryVo)
                .sortedByDescending { it.sortOrder }
                .toList()
        
        //查询子目录
        findSubCategory(categoryRootList, categories)
        
        return ResponseVo.success(data = categoryRootList)
    }

    /**
     * 递归查询子目录
     */
    private fun findSubCategory(categoryParentList: List<CategoryVo>, categories: List<Category>) {
        //遍历父目录
        categoryParentList.forEach { categoryParent ->
            //遍历所有目录收集子目录
            val subCategorySubList = categories.asSequence()
                    .filter { it.parentId == categoryParent.id }
                    .map(::categoryToCategoryVo)
                    .sortedByDescending { it.sortOrder }
                    .toList()
            
            //父目录添加子目录
            categoryParent.subCategories = subCategorySubList
            
            //递归查询余下子目录
            findSubCategory(subCategorySubList, categories)
        }
    }

    /**
     * 类型转换
     */
    private fun categoryToCategoryVo(category: Category): CategoryVo {
        val categoryVo = CategoryVo()
        BeanUtils.copyProperties(category, categoryVo)
        return categoryVo
    }

}