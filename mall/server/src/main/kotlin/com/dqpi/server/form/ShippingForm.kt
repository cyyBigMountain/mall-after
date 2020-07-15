package com.dqpi.server.form

import javax.validation.constraints.NotBlank


data class ShippingForm(
        /**
         * 收货人姓名
         */
        @field:NotBlank
        var receiverName: String? = null,

        /**
         * 收货人固定电话
         */
        @field:NotBlank
        var receiverPhone: String? = null,

        /**
         * 收货人移动电话
         */
        @field:NotBlank
        var receiverMobile: String? = null,

        /**
         * 收货人省份
         */
        @field:NotBlank
        var receiverProvince: String? = null,

        /**
         * 收货人城市
         */
        @field:NotBlank
        var receiverCity: String? = null,

        /**
         * 收货人区/县
         */
        @field:NotBlank
        var receiverDistrict: String? = null,

        /**
         * 收货人详细地址
         */
        @field:NotBlank
        var receiverAddress: String? = null,

        /**
         * 收货人邮编
         */
        @field:NotBlank
        var receiverZip: String? = null
)