package com.example.digikalasample.data.repository

import com.example.digikalasample.data.model.Resource
import com.example.digikalasample.data.model.coupon.Coupon
import com.example.digikalasample.data.model.customer.Customer
import com.example.digikalasample.data.model.order.Order
import com.example.digikalasample.data.model.product.Category
import com.example.digikalasample.data.model.product.Product
import com.example.digikalasample.data.model.review.Review
import com.example.digikalasample.network.DigiKalaApiService
import javax.inject.Inject

class ProductsRepository @Inject constructor(private val productApiService: DigiKalaApiService) :
    BaseRepo() {
    suspend fun getProducts(orderBy: String, perPage: Int = 20): Resource<List<Product>> {
        return safeApiCall { productApiService.getProduct(orderBy = orderBy, perPage = perPage) }
    }

    suspend fun getRelatedProducts(includeList: List<Int>): Resource<List<Product>> {
        return safeApiCall { productApiService.getProduct(include = includeList) }
    }


    suspend fun getProductsBySearch(
        searchQuery: String,
        orderBy: String = "popularity",
        order: String = "asc"
    ): Resource<List<Product>> {
        return safeApiCall {
            productApiService.getProduct(
                searchQuery = searchQuery,
                orderBy = orderBy,
                order = order
            )
        }
    }

    suspend fun getProductsBySearch(
        searchQuery: String,
        orderBy: String = "popularity",
        order: String = "asc", attribute: String, attributeTerm: String
    ): Resource<List<Product>> {
        return safeApiCall {
            productApiService.getProduct(
                searchQuery = searchQuery,
                orderBy = orderBy,
                order = order,
                attribute = attribute,
                terms = attributeTerm
            )
        }
    }

    suspend fun getProductsByCategory(category: Int): Resource<List<Product>> {
        return safeApiCall {
            productApiService.getProduct(category = category)
        }
    }


    suspend fun getCategories(): Resource<List<Category>> {
        return safeApiCall {
            productApiService.getCategories()
        }
    }

    suspend fun getProductById(id: Int): Resource<Product> {
        return safeApiCall {
            productApiService.getProductById(id = id)
        }
    }

    suspend fun getReviews(productId: String): Resource<List<Review>> {
        return safeApiCall {
            productApiService.getReviews(productId = productId)

        }
    }

    suspend fun createCustomer(
        firstName: String,
        lastName: String,
        email: String
    ): Resource<Customer?> {
        return safeApiCall {
            productApiService.createCustomer(
                firstName = firstName,
                lastName = lastName,
                email = email
            )
        }

    }


    suspend fun getCustomer(id: Int): Resource<Customer?> {
        return safeApiCall {
            productApiService.getCustomer(id = id)
        }
    }

    suspend fun createOrder(order: Order): Resource<Order> {
        return safeApiCall {
            productApiService.createOrder(order)
        }
    }

    suspend fun getAllCoupons(): Resource<List<Coupon>> {
        return safeApiCall {
            productApiService.getAllCoupons()

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
