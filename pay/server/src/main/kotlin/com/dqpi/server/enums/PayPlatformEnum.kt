package com.dqpi.server.enums

import com.lly835.bestpay.enums.BestPayTypeEnum

enum class PayPlatformEnum(val code: Int) {
    ALIPAY(1),
    WX(2);

    companion object {
        fun getByBestPayTypeEnum(bestPayTypeEnum: BestPayTypeEnum): PayPlatformEnum {
            return when (true) {
                bestPayTypeEnum.platform.name == ALIPAY.name -> ALIPAY

                bestPayTypeEnum.platform.name == WX.name -> WX

                else -> throw RuntimeException("错误的支付平台: ${bestPayTypeEnum.name}")
            }
        }
    }
}