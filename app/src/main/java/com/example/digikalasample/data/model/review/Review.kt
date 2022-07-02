package com.example.digikalasample.data.model.review


data class Review(
    val id: Int = 0,
    val product_id: Int,
    var review: String,
    val reviewer: String,
    val reviewer_avatar_urls: ReviewerAvatarUrls =ReviewerAvatarUrls("") ,
    val reviewer_email: String
)