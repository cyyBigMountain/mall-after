package com.dqpi.server.service.impl

import com.dqpi.server.consts.MallConsts
import com.dqpi.server.dao.ProductDao
import com.dqpi.server.domain.common.PageVo
import com.dqpi.server.domain.common.ResponseVo
import com.dqpi.server.domain.entity.Product
import com.dqpi.server.domain.vo.ProductDetailVo
import com.dqpi.server.domain.vo.ProductVo
import com.dqpi.server.enums.ProductStatusEnum
import com.dqpi.server.enums.ResponseEnum
import com.dqpi.server.service.CategoryService
import com.dqpi.server.service.ProductService
import org.slf4j.LoggerFactory
import org.springframework.beans.BeanUtils
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import javax.annotation.Resource

@Service
class ProductServiceImpl: ProductService {
    
    private val log = LoggerFactory.getLogger(this::class.java)
    
    @Resource
    private lateinit var categoryService: CategoryService
    
    @Resource
    private lateinit var productDao: ProductDao
    
    /**
     * 分页查询所有商品列表
     */
    override fun list(categoryId: Int?, pageNum: Int, pageSize: Int): ResponseVo<PageVo<List<ProductVo>>> {
        val categoryIdSet = HashSet<Int?>()
        categoryService.findSubCategoryId(categoryId ?: MallConsts.ROOT_PARENT_ID, categoryIdSet)
        categoryIdSet.add(categoryId)
        
        //分页查询数据库
        val pageRequest = PageRequest.of(pageNum - 1, pageSize)
        val pageProduct = productDao.findAllByCategoryIdInAndStatus(categoryIdSet, pageable = pageRequest)
        
        //组装分页返回数据
        val pageVo = PageVo<List<ProductVo>>()
        pageVo.pageNum = pageProduct.number + 1
        pageVo.pageSize = pageProduct.size
        pageVo.data = pageProduct.map(::productToProductVo).toList()
        pageVo.totalElements = pageProduct.totalElements
        
        return ResponseVo.success(data = pageVo)
    }

    /**
     * 查询商品详情
     */
    override fun detail(productId: Int): ResponseVo<ProductDetailVo> {
        val productOpt = productDao.findById(productId)
        
        //判断数据库是否存在该商品
        if (productOpt.isPresent) {
            productOpt.get().let { 
                if (it.status == ProductStatusEnum.DELETE.code 
                        || it.status == ProductStatusEnum.OFF_SALE.code) {
                    return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE)
                }

                val productDetailVo = it.let(::productToProductDetailVo)
                return ResponseVo.success(data = productDetailVo)
            }
        }
        
        return ResponseVo.error(ResponseEnum.ERROR)
    }
    
    /**
     * product转productDetailVo
     */
    private fun productToProductDetailVo(product: Product): ProductDetailVo {
        val productDetailVo = ProductDetailVo()
        BeanUtils.copyProperties(product, productDetailVo)
        productDetailVo.stock = if (product.stock!! > 100) 100 else product.stock
        return productDetailVo
    }
    
    /**
     * product转productVo
     */
    private fun productToProductVo(product: Product): ProductVo {
        val productVo = ProductVo()
        BeanUtils.copyProperties(product, productVo)
        return productVo
    }
}