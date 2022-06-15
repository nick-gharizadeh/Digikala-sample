package com.example.digikalasample.data

import com.example.digikalasample.data.model.Category
import com.example.digikalasample.data.model.Product
import com.example.digikalasample.network.DigiKalaApiService
import java.net.UnknownHostException
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(private val productApiService: DigiKalaApiService) {

    suspend fun getProducts(orderBy: String): List<Product> {
        return try {
            productApiService.getProduct(orderBy = orderBy)
        } catch (e: UnknownHostException) {
            listOf()
        }
    }


    suspend fun getProductsBySearch(searchQuery: String): List<Product> {
        return try {
            productApiService.getProduct(searchQuery = searchQuery)
        } catch (e: UnknownHostException) {
            listOf()
        }
    }

    suspend fun getProductsByCategory(category: Int): List<Product> {
        return try {
            productApiService.getProduct(category = category)
        } catch (e: UnknownHostException) {
            listOf()

        }
    }


    suspend fun getCategories(): List<Category> {
        return try {
            productApiService.getCategories()
        } catch (e: UnknownHostException) {
            listOf()
        }
    }

    suspend fun getProductById(id: Int): Product? {
        return try {
            productApiService.getProductById(id = id)
        } catch (e: UnknownHostException) {
         val product = null
            return product
        }
    }

}
