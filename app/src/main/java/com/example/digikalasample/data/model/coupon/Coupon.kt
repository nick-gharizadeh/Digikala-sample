package com.example.digikalasample.data.model.coupon

data class Coupon(
    val amount: String,
    val code: String,
    val date_created: String,
    val date_created_gmt: String,
    val date_expires: String?,
    val date_expires_gmt: String?,
    val date_modified: String?,
    val date_modified_gmt: String?,
    val description: String,
    val discount_type: String,
    val exclude_sale_items: Boolean,
    val excluded_product_categories: List<Any?>,
    val excluded_product_ids: List<Any?>,
    val free_shipping: Boolean,
    val id: Int,
    val individual_use: Boolean,
    val maximum_amount: String,
    val meta_data: List<Any>,
    val minimum_amount: String,
    val product_categories: List<Any?>,
    val product_ids: List<Any>,
    val usage_count: Int,
    val used_by: List<Any?>
)