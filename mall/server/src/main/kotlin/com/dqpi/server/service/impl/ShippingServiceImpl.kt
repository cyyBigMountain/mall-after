package com.dqpi.server.service.impl

import com.dqpi.server.dao.ShippingDao
import com.dqpi.server.domain.common.PageVo
import com.dqpi.server.domain.common.ResponseVo
import com.dqpi.server.domain.entity.Shipping
import com.dqpi.server.enums.ResponseEnum
import com.dqpi.server.form.ShippingForm
import com.dqpi.server.service.ShippingService
import org.springframework.beans.BeanUtils
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.annotation.Resource

@Service
class ShippingServiceImpl: ShippingService {
    
    @Resource
    private lateinit var shippingDao: ShippingDao
    
    /**
     * 地址添加
     */
    override fun add(uid: Int, shippingForm: ShippingForm): ResponseVo<Map<String, Int>> {
        val shipping = Shipping()
        BeanUtils.copyProperties(shippingForm, shipping)
        shipping.userId = uid
        return try {
            ResponseVo.success(data = hashMapOf("shippingId" to shippingDao.save(shipping).id!!))
        } catch (e: Exception) {
            ResponseVo.error(ResponseEnum.ERROR)
        }
    }

    /**
     * 地址删除
     */
    @Transactional
    override fun delete(uid: Int, shippingId: Int): ResponseVo<String> {
        return try {
            shippingDao.deleteByIdAndUserId(shippingId, uid)
            ResponseVo.success()
        }catch (e: Exception) {
            ResponseVo.error(ResponseEnum.DELETE_SHIPPING_FAIL)
        }
    }

    /**
     * 地址更新
     */
    override fun update(uid: Int, shippingId: Int, shippingForm: ShippingForm): ResponseVo<String> {
        val shippingOpt = shippingDao.findByIdAndUserId(shippingId, uid)
        if (shippingOpt.isPresent) {
            shippingOpt.get().let {
                BeanUtils.copyProperties(shippingForm, it)
                shippingDao.save(it)
                return ResponseVo.success()
            }
        }

        return ResponseVo.error(ResponseEnum.ERROR)
    }

    /**
     * 地址列表
     */
    override fun list(uid: Int, pageNum: Int, pageSize: Int): ResponseVo<PageVo<List<Shipping>>> {
        val pageRequest = PageRequest.of(pageNum - 1, pageSize)
        val pageShipping = shippingDao.findAllByUserId(uid, pageRequest)

        val pageVo = PageVo<List<Shipping>>()
        pageVo.pageNum = pageShipping.number + 1
        pageVo.pageSize = pageShipping.size
        pageVo.totalElements = pageShipping.totalElements
        pageVo.pageData = pageShipping.content

        return ResponseVo.success(data = pageVo)
    }

}