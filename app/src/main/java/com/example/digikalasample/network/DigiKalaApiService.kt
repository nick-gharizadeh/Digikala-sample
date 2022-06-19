package com.example.digikalasample.network

import com.example.digikalasample.data.model.Category
import com.example.digikalasample.data.model.Review
import com.example.digikalasample.data.model.Product
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


const val CONSUMER_KEY = "ck_0e61e4a29d9130ed954bb8c11e7f827a7e8c2dfb"
const val CONSUMER_SECRET = "cs_46699340821cdba8425c23e67c0af8672e7dac88"

interface DigiKalaApiService {

    @GET("products")
    suspend fun getProduct(
        @Query("consumer_key")
        consumerKey: String = CONSUMER_KEY,
        @Query("consumer_secret")
        consumerSecret: String = CONSUMER_SECRET,
        @Query("per_page")
        perPage: Int = 15,
        @Query("page")
        numberOfPage: Int = 1,
        @Query("orderby")
        orderBy: String
    ): List<Product>

    @GET("products")
    suspend fun getProduct(
        @Query("consumer_key")
        consumerKey: String = CONSUMER_KEY,
        @Query("consumer_secret")
        consumerSecret: String = CONSUMER_SECRET,
        @Query("category")
        category: Int,
        @Query("per_page")
        perPage: Int = 20,
        @Query("page")
        numberOfPage: Int = 1,
    ): List<Product>

    @GET("products/{id}?")
    suspend fun getProductById(
        @Path("id") id: Int,
        @Query("consumer_key")
        consumerKey: String = CONSUMER_KEY,
        @Query("consumer_secret")
        consumerSecret: String = CONSUMER_SECRET
    ): Product

    @GET("products/categories")
    suspend fun getCategories(
        @Query("consumer_key")
        consumerKey: String = CONSUMER_KEY,
        @Query("consumer_secret")
        consumerSecret: String = CONSUMER_SECRET,
        @Query("per_page")
        perPage: Int = 30
    ): List<Category>

    //search

    @GET("products")
    suspend fun getProduct(
        @Query("consumer_key")
        consumerKey: String = CONSUMER_KEY,
        @Query("consumer_secret")
        consumerSecret: String = CONSUMER_SECRET,
        @Query("search") searchQuery: String,
        @Query("per_page")
        perPage: Int = 20,
        @Query("page")
        numberOfPage: Int = 1
    ): List<Product>

    @GET("products/reviews")
    suspend fun getReviews(
        @Query("consumer_key") consumerKey:String = CONSUMER_KEY,
        @Query("consumer_secret") consumerSecret:String = CONSUMER_SECRET,
        @Query("product") productId: String
    ):List<Review>

}