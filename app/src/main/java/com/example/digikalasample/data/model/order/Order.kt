package com.example.digikalasample.data.model.order

data class Order(
    val customer_id: Int,
    val id: Int = 0,
    val line_items: List<LineItem>,
    val total: String,
    val shipping: Shipping
)