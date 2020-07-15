package com.dqpi.server.domain.entity

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
@DynamicUpdate
@DynamicInsert
data class Shipping(
        /**
         * id
         */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,

        /**
         * 用户id
         */
        var userId: Int? = null,

        /**
         * 收货人姓名
         */
        var receiverName: String? = null,

        /**
         * 收货人固定电话
         */
        var receiverPhone: String? = null,

        /**
         * 收货人移动电话
         */
        var receiverMobile: String? = null,

        /**
         * 收货人省份
         */
        var receiverProvince: String? = null,

        /**
         * 收货人城市
         */
        var receiverCity: String? = null,

        /**
         * 收货人区/县
         */
        var receiverDistrict: String? = null,

        /**
         * 收货人详细地址
         */
        var receiverAddress: String? = null,

        /**
         * 收货人邮编
         */
        var receiverZip: String? = null,

        /**
         * 创建时间
         */
        var createTime: LocalDateTime? = null,

        /**
         * 修改时间
         */
        var updateTime: LocalDateTime? = null
)