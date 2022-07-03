package com.example.digikalasample.data.model.review


data class Review(
    val id: Int = 0,
    val product_id: Int,
    var review: String,
    val reviewer: String,
    val reviewer_avatar_urls: ReviewerAvatarUrls = ReviewerAvatarUrls("https://secure.gravatar.com/avatar/3606b9210b62161cc00ca5c0e05993d1?s=48&d=mm&r=g"),
    val reviewer_email: String
)