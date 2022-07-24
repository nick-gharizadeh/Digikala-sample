package com.example.digikalasample.data.repository

import com.example.digikalasample.data.model.Resource
import com.example.digikalasample.data.model.review.Review
import com.example.digikalasample.network.DigiKalaApiService
import javax.inject.Inject

class ReviewRepository @Inject constructor(private val productApiService: DigiKalaApiService) :
    BaseRepo() {

    suspend fun getReviews(productId: String): Resource<List<Review>> {
        return safeApiCall {
            productApiService.getReviews(productId = productId)

        }
    }

    suspend fun postReview(review: Review): Resource<Review> {
        return safeApiCall {
            productApiService.postReview(review = review)
        }
    }

    suspend fun deleteReview(id: Int): Resource<Review> {
        return safeApiCall { productApiService.deleteReview(id = id) }
    }

    suspend fun updateReview(id: Int, review: String): Resource<Review> {
        return safeApiCall { productApiService.updateReview(id = id, review = review) }
    }
}