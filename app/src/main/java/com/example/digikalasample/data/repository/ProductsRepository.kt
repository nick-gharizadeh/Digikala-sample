package com.example.digikalasample.data.repository

import com.example.digikalasample.data.ProductRemoteDataSource
import com.example.digikalasample.data.model.product.Category
import com.example.digikalasample.data.model.review.Review
import com.example.digikalasample.data.model.product.Product
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val productRemoteDataSource: ProductRemoteDataSource,
) {
    suspend fun getProducts(orderBy:String ):List<Product>{
        return productRemoteDataSource.getProducts(orderBy = orderBy)
    }

    suspend fun getProductsBySearch(searchQuery:String,orderBy: String = "popularity",order :String="asc" ):List<Product>{
        return productRemoteDataSource.getProductsBySearch(searchQuery,orderBy,order)
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

    suspend fun getReviews(productId: String):List<Review> {
       return productRemoteDataSource.getReviews(productId = productId)
    }

}
