package com.example.digikalasample.data

import com.example.digikalasample.data.model.Category
import com.example.digikalasample.data.model.Product
import com.example.digikalasample.network.DigiKalaApiService
import retrofit2.http.Path
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(val productApiService: DigiKalaApiService) {

    suspend fun getProducts(orderBy: String): List<Product> {
        try {
            return productApiService.getProduct(orderBy = orderBy)
        }
        catch (e:UnknownHostException){
            return listOf()
        }
    }

    suspend fun getProductsByCategory(category: Int): List<Product> {
        try {
            return productApiService.getProduct(category = category)
        }
        catch (e:UnknownHostException) {
            return listOf()

        }
    }


    suspend fun getCategories(): List<Category> {
        try {
            return productApiService.getCategories()
        }
        catch (e:UnknownHostException) {
            return listOf()
        }

    }

    suspend fun getProductById(id: String): Product
    {
        return productApiService.getProductById(id)
    }

}
