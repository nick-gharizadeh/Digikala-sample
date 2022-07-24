package com.example.digikalasample.data.repository

import com.example.digikalasample.data.model.Resource
import com.example.digikalasample.data.model.coupon.Coupon
import com.example.digikalasample.data.model.order.Order
import com.example.digikalasample.data.model.product.Category
import com.example.digikalasample.data.model.product.Product
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


}
