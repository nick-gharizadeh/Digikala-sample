package com.example.digikalasample.network

import com.example.digikalasample.data.model.Product
import retrofit2.http.GET
import retrofit2.http.Query


const val CONSUMER_KEY = "ck_0e61e4a29d9130ed954bb8c11e7f827a7e8c2dfb"
const val CONSUMER_SECRET = "cs_46699340821cdba8425c23e67c0af8672e7dac88"
const val WOOCOMMERCE_REST_AUTHENTICATION_KEY =
    "?consumer_key=$CONSUMER_KEY&consumer_secret=$CONSUMER_SECRET"

interface DigiKalaApiService {

    @GET("products$WOOCOMMERCE_REST_AUTHENTICATION_KEY")
    suspend fun getProduct(
        @Query("per_page")
        perPage:Int = 10,
        @Query("page")
        numberOfPage:Int = 1,
        @Query("orderby")
        orderBy:String
    ): List<Product>

}