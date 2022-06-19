package com.example.digikalasample.data.repository

import com.example.digikalasample.data.ProductRemoteDataSource
import com.example.digikalasample.data.model.Category
import com.example.digikalasample.data.model.Comment
import com.example.digikalasample.data.model.Product
import java.net.UnknownHostException
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val productRemoteDataSource: ProductRemoteDataSource,
) {
    suspend fun getProducts(orderBy:String ):List<Product>{
        return productRemoteDataSource.getProducts(orderBy = orderBy)
    }

    suspend fun getProductsBySearch(searchQuery:String ):List<Product>{
        return productRemoteDataSource.getProductsBySearch(searchQuery)
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

    suspend fun getReviews(productId: String):List<Comment> {
       return productRemoteDataSource.getReviews(productId = productId)
    }

}
