package com.example.digikalasample.data.model.order

import com.example.digikalasample.data.model.coupon.CouponLine

data class Order(
    val customer_id: Int,
    val id: Int = 0,
    val line_items: List<LineItem>,
    val billing: Billing,
    val coupon_lines: List<CouponLine>
)