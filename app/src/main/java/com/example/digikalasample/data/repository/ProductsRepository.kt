package com.example.digikalasample.data.repository

import com.example.digikalasample.data.ProductRemoteDataSource
import com.example.digikalasample.data.model.coupon.Coupon
import com.example.digikalasample.data.model.customer.Customer
import com.example.digikalasample.data.model.order.Order
import com.example.digikalasample.data.model.product.Category
import com.example.digikalasample.data.model.product.Product
import com.example.digikalasample.data.model.review.Review
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val productRemoteDataSource: ProductRemoteDataSource,
) {
    suspend fun getProducts(orderBy: String, perPage: Int = 20): List<Product> {
        return productRemoteDataSource.getProducts(orderBy = orderBy, perPage = perPage)
    }

    suspend fun getRelatedProducts(includeList: List<Int>): List<Product> {
        return productRemoteDataSource.getRelatedProducts(includeList)
    }

    suspend fun getProductsBySearch(
        searchQuery: String,
        orderBy: String = "popularity",
        order: String = "asc"
    ): List<Product> {
        return productRemoteDataSource.getProductsBySearch(searchQuery, orderBy, order)
    }

    suspend fun getProductsBySearch(
        searchQuery: String,
        orderBy: String = "popularity",
        order: String = "asc", attribute: String, attributeTerm: String
    ): List<Product> {
        return productRemoteDataSource.getProductsBySearch(
            searchQuery,
            orderBy,
            order,
            attribute,
            attributeTerm
        )
    }

    suspend fun getProductsByCategory(category: Int): List<Product> {
        return productRemoteDataSource.getProductsByCategory(category = category)
    }

    suspend fun getCategories(): List<Category> {
        return productRemoteDataSource.getCategories()
    }


    suspend fun getProductById(id: Int): Product? {
        return productRemoteDataSource.getProductById(id)
    }

    suspend fun getReviews(productId: String): List<Review> {
        return productRemoteDataSource.getReviews(productId = productId)
    }

    suspend fun createCustomer(firstName: String, lastName: String, email: String): Customer? {
        return productRemoteDataSource.createCustomer(
            firstName = firstName,
            lastName = lastName,
            email = email
        )
    }

    suspend fun getCustomer(id: Int): Customer? {
        return productRemoteDataSource.getCustomer(id = id)
    }

    suspend fun createOrder(order: Order): Order? {
        return productRemoteDataSource.createOrder(
            order
        )
    }

    suspend fun getAllCoupons(): List<Coupon> {
        return productRemoteDataSource.getAllCoupons()
    }


    suspend fun postReview(review: Review): Review? {
        return productRemoteDataSource.postReview(review)
    }

    suspend fun deleteReview(id: Int) {
        return productRemoteDataSource.deleteReview(id)

    }

    suspend fun updateReview(id: Int, review: String) {
        return productRemoteDataSource.updateReview(id = id, review = review)

    }

}
