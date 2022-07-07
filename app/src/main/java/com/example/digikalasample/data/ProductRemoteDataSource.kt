package com.example.digikalasample.data

import androidx.lifecycle.MutableLiveData
import com.example.digikalasample.data.model.coupon.Coupon
import com.example.digikalasample.data.model.customer.Customer
import com.example.digikalasample.data.model.order.Order
import com.example.digikalasample.data.model.product.Category
import com.example.digikalasample.data.model.product.Product
import com.example.digikalasample.data.model.review.Review
import com.example.digikalasample.network.DigiKalaApiService
import javax.inject.Inject

val errorThatOccur = MutableLiveData<Exception?>(null)

class ProductRemoteDataSource @Inject constructor(private val productApiService: DigiKalaApiService) {

    suspend fun getProducts(orderBy: String): List<Product> {
        return try {
            errorThatOccur.value = null
            productApiService.getProduct(orderBy = orderBy)
        } catch (e: Exception) {
            if (errorThatOccur.value == null)
                errorThatOccur.value = e
            listOf()
        }
    }

    suspend fun getRelatedProducts(includeList: List<Int>): List<Product> {
        return try {
            errorThatOccur.value = null
            productApiService.getProduct(include = includeList)
        } catch (e: Exception) {
            if (errorThatOccur.value == null)
                errorThatOccur.value = e
            listOf()
        }
    }


    suspend fun getProductsBySearch(
        searchQuery: String,
        orderBy: String = "popularity",
        order: String = "asc"
    ): List<Product> {
        return try {
            errorThatOccur.value = null
            productApiService.getProduct(
                searchQuery = searchQuery,
                orderBy = orderBy,
                order = order
            )
        } catch (e: Exception) {
            if (errorThatOccur.value == null)
                errorThatOccur.value = e
            listOf()
        }
    }

    suspend fun getProductsBySearch(
        searchQuery: String,
        orderBy: String = "popularity",
        order: String = "asc", attribute: String, attributeTerm: String
    ): List<Product> {
        return try {
            errorThatOccur.value = null
            productApiService.getProduct(
                searchQuery = searchQuery,
                orderBy = orderBy,
                order = order,
                attribute = attribute,
                terms = attributeTerm
            )
        } catch (e: Exception) {
            if (errorThatOccur.value == null)
                errorThatOccur.value = e
            listOf()
        }
    }

    suspend fun getProductsByCategory(category: Int): List<Product> {
        return try {
            errorThatOccur.value = null
            productApiService.getProduct(category = category)
        } catch (e: Exception) {
            if (errorThatOccur.value == null)
                errorThatOccur.value = e
            listOf()

        }
    }


    suspend fun getCategories(): List<Category> {
        return try {
            errorThatOccur.value = null
            productApiService.getCategories()
        } catch (e: Exception) {
            if (errorThatOccur.value == null)
                errorThatOccur.value = e
            listOf()
        }
    }

    suspend fun getProductById(id: Int): Product? {
        return try {
            errorThatOccur.value = null
            productApiService.getProductById(id = id)
        } catch (e: Exception) {
            if (errorThatOccur.value == null)
                errorThatOccur.value = e
            return null
        }
    }

    suspend fun getReviews(productId: String): List<Review> {
        return try {
            errorThatOccur.value = null
            productApiService.getReviews(productId = productId)
        } catch (e: Exception) {
            if (errorThatOccur.value == null)
                errorThatOccur.value = e
            listOf()
        }
    }

    suspend fun createCustomer(firstName: String, lastName: String, email: String): Customer? {
        return try {
            errorThatOccur.value = null
            productApiService.createCustomer(
                firstName = firstName,
                lastName = lastName,
                email = email
            )
        } catch (e: Exception) {
            if (errorThatOccur.value == null)
                errorThatOccur.value = e
            return null
        }
    }


    suspend fun getCustomer(id: Int): Customer? {
        return try {
            errorThatOccur.value = null
            productApiService.getCustomer(id = id)
        } catch (e: Exception) {
            if (errorThatOccur.value == null)
                errorThatOccur.value = e
            return null
        }
    }

    suspend fun createOrder(order: Order): Order? {
        return try {
            errorThatOccur.value = null
            productApiService.createOrder(order)
        } catch (e: Exception) {
            if (errorThatOccur.value == null)
                errorThatOccur.value = e
            val nullOrder: Order? = null
            nullOrder
        }
    }

    suspend fun getAllCoupons(): List<Coupon> {
        return try {
            errorThatOccur.value = null
            productApiService.getAllCoupons()
        } catch (e: Exception) {
            if (errorThatOccur.value == null)
                errorThatOccur.value = e
            listOf()
        }
    }

    suspend fun postReview(review: Review): Review? {
        return try {
            errorThatOccur.value = null
            productApiService.postReview(review = review)
        } catch (e: Exception) {
            if (errorThatOccur.value == null)
                errorThatOccur.value = e
            val nullReview: Review? = null
            nullReview
        }
    }

    suspend fun deleteReview(id: Int) {
        try {
            errorThatOccur.value = null
            productApiService.deleteReview(id = id)
        } catch (e: Exception) {
            if (errorThatOccur.value == null)
                errorThatOccur.value = e

        }
    }

    suspend fun updateReview(id: Int, review: String) {
        try {
            errorThatOccur.value = null
            productApiService.updateReview(id = id, review = review)
        } catch (e: Exception) {
            if (errorThatOccur.value == null)
                errorThatOccur.value = e

        }
    }

}
