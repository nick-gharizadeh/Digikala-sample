package com.example.digikalasample.network

import com.example.digikalasample.data.OrderByEnum
import com.example.digikalasample.data.OrderSortEnum
import com.example.digikalasample.data.model.coupon.Coupon
import com.example.digikalasample.data.model.customer.Customer
import com.example.digikalasample.data.model.order.Order
import com.example.digikalasample.data.model.product.Category
import com.example.digikalasample.data.model.product.Product
import com.example.digikalasample.data.model.review.Review
import retrofit2.Response
import retrofit2.http.*


const val CONSUMER_KEY = "ck_63f4c52da932ddad1570283b31f3c96c4bd9fd6f"
const val CONSUMER_SECRET = "cs_294e7de35430398f323b43c21dd1b29f67b5370b"

interface DigiKalaApiService {

    @GET("products")
    suspend fun getProduct(
        @Query("consumer_key")
        consumerKey: String = CONSUMER_KEY,
        @Query("consumer_secret")
        consumerSecret: String = CONSUMER_SECRET,
        @Query("per_page")
        perPage: Int = 20,
        @Query("page")
        numberOfPage: Int = 1,
        @Query("orderby")
        orderBy: String
    ): Response<List<Product>>

    @GET("products")
    suspend fun getProduct(
        @Query("consumer_key")
        consumerKey: String = CONSUMER_KEY,
        @Query("consumer_secret")
        consumerSecret: String = CONSUMER_SECRET,
        @Query("per_page")
        perPage: Int = 30,
        @Query("page")
        numberOfPage: Int = 1,
        @Query("include")
        include: List<Int>,
    ): Response<List<Product>>

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
    ): Response<List<Product>>

    @GET("products/{id}?")
    suspend fun getProductById(
        @Path("id") id: Int,
        @Query("consumer_key")
        consumerKey: String = CONSUMER_KEY,
        @Query("consumer_secret")
        consumerSecret: String = CONSUMER_SECRET
    ): Response<Product>

    @GET("products/categories")
    suspend fun getCategories(
        @Query("consumer_key")
        consumerKey: String = CONSUMER_KEY,
        @Query("consumer_secret")
        consumerSecret: String = CONSUMER_SECRET,
        @Query("per_page")
        perPage: Int = 30
    ): Response<List<Category>>

    //search

    @GET("products")
    suspend fun getProduct(
        @Query("consumer_key")
        consumerKey: String = CONSUMER_KEY,
        @Query("consumer_secret")
        consumerSecret: String = CONSUMER_SECRET,
        @Query("search") searchQuery: String,
        @Query("per_page")
        perPage: Int = 40,
        @Query("page")
        numberOfPage: Int = 1,
        @Query("orderby")
        orderBy: String = OrderByEnum.POPULARITY.orderTypeString,
        @Query("order")
        order: String = OrderSortEnum.ASC.orderSortString
    ): Response<List<Product>>

    @GET("products")
    suspend fun getProduct(
        @Query("consumer_key")
        consumerKey: String = CONSUMER_KEY,
        @Query("consumer_secret")
        consumerSecret: String = CONSUMER_SECRET,
        @Query("search") searchQuery: String,
        @Query("per_page")
        perPage: Int = 40,
        @Query("page")
        numberOfPage: Int = 1,
        @Query("orderby")
        orderBy: String = OrderByEnum.POPULARITY.orderTypeString,
        @Query("order")
        order: String = OrderSortEnum.ASC.orderSortString,
        @Query("attribute") attribute: String,
        @Query("attribute_term") terms: String,
    ): Response<List<Product>>

    @GET("products/reviews")
    suspend fun getReviews(
        @Query("consumer_key") consumerKey: String = CONSUMER_KEY,
        @Query("consumer_secret") consumerSecret: String = CONSUMER_SECRET,
        @Query("product") productId: String
    ): Response<List<Review>>


    @FormUrlEncoded
    @POST("customers")
    suspend fun createCustomer(
        @Query("consumer_key") consumerKey: String = CONSUMER_KEY,
        @Query("consumer_secret") consumerSecret: String = CONSUMER_SECRET,
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String,
        @Field("email") email: String
    ): Response<Customer?>

    @GET("customers/{id}?")
    suspend fun getCustomer(
        @Path("id") id: Int,
        @Query("consumer_key") consumerKey: String = CONSUMER_KEY,
        @Query("consumer_secret") consumerSecret: String = CONSUMER_SECRET,
    ): Response<Customer?>


    @POST("orders")
    suspend fun createOrder(
        @Body order: Order,
        @Query("consumer_key") consumerKey: String = CONSUMER_KEY,
        @Query("consumer_secret") consumerSecret: String = CONSUMER_SECRET
    ): Response<Order>

    @GET("coupons")
    suspend fun getAllCoupons(
        @Query("consumer_key") consumerKey: String = CONSUMER_KEY,
        @Query("consumer_secret") consumerSecret: String = CONSUMER_SECRET,
        @Query("per_page") perPage: Int = 100,
    ): Response<List<Coupon>>


    @POST("products/reviews")
    suspend fun postReview(
        @Body review: Review,
        @Query("consumer_key") consumerKey: String = CONSUMER_KEY,
        @Query("consumer_secret") consumerSecret: String = CONSUMER_SECRET
    ): Response<Review>


    @DELETE("products/reviews/{id}?")
    suspend fun deleteReview(
        @Path("id") id: Int,
        @Query("consumer_key") consumerKey: String = CONSUMER_KEY,
        @Query("consumer_secret") consumerSecret: String = CONSUMER_SECRET
    ): Response<Review>

    @PUT("products/reviews/{id}?")
    suspend fun updateReview(
        @Path("id") id: Int,
        @Query("consumer_key") consumerKey: String = CONSUMER_KEY,
        @Query("consumer_secret") consumerSecret: String = CONSUMER_SECRET,
        @Query("review") review: String?,
    ): Response<Review>
}