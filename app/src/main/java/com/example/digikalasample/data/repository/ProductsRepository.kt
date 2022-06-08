package com.example.digikalasample.data.repository

import com.example.digikalasample.data.ProductRemoteDataSource
import com.example.digikalasample.data.model.Category
import com.example.digikalasample.data.model.Product
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val productRemoteDataSource: ProductRemoteDataSource,
) {
    suspend fun getProducts(orderBy:String ):List<Product>{
        return productRemoteDataSource.getProducts(orderBy = orderBy)
    }

    suspend fun getProductsByCategory(category: Int): List<Product> {
        return productRemoteDataSource.getProductsByCategory(category = category)
    }

    suspend fun getCategories(): List<Category> {
    return productRemoteDataSource.getCategories()
    }

}
