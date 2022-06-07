package com.example.digikalasample.data

import com.example.digikalasample.data.model.Category
import com.example.digikalasample.data.model.Product
import com.example.digikalasample.network.DigiKalaApiService
import retrofit2.http.Path
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(val productApiService: DigiKalaApiService) {

    suspend fun getProducts(orderBy: String): List<Product> {
        return productApiService.getProduct(orderBy = orderBy)
    }

    suspend fun getCategories(): List<Category> {
        return productApiService.getCategories()
    }

    suspend fun getProductById(id: String): Product
    {
        return productApiService.getProductById(id)
    }

}
