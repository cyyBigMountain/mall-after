package com.dqpi.server.service

import com.dqpi.server.domain.entity.PayInfo
import com.lly835.bestpay.enums.BestPayTypeEnum
import com.lly835.bestpay.model.PayResponse
import java.math.BigDecimal

interface PayService {
    /**
     * 发起支付 
     */
    fun create(orderNo: String, amount: BigDecimal, payType: BestPayTypeEnum): PayResponse

    /**
     * 异步通知处理
     */
    fun asyncNotify(notifyData: String): String

    /**
     * 通过订单编号查询
     */
    fun queryByOrderNo(orderNo: String): PayInfo?
}