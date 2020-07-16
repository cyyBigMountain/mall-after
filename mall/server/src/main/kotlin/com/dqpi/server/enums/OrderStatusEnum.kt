package com.dqpi.server.enums

enum class OrderStatusEnum(val code: Int, val desc: String) {
    CANCELED(0, "已取消"),
    
    NO_PAY(10, "未付款"),
    
    PAID(20, "已付款"),
    
    SHIPPED(40, "已发货"),
    
    TRADE_SUCCESS(50, "交易成功"),
    
    TRADE_CLOSE(60, "交易关闭")
}