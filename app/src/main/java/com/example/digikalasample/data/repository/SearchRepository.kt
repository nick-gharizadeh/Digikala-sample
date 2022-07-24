package com.example.digikalasample.data.repository

import com.example.digikalasample.data.OrderByEnum
import com.example.digikalasample.data.OrderSortEnum
import com.example.digikalasample.data.model.Resource
import com.example.digikalasample.data.model.product.Product
import com.example.digikalasample.network.DigiKalaApiService
import javax.inject.Inject

class SearchRepository @Inject constructor(private val productApiService: DigiKalaApiService) :
    BaseRepo() {

    suspend fun getProductsBySearch(
        searchQuery: String,
        orderBy: String = OrderByEnum.POPULARITY.orderTypeString,
        order: String = OrderSortEnum.ASC.orderSortString
    ): Resource<List<Product>> {
        return safeApiCall {
            productApiService.getProduct(
                searchQuery = searchQuery,
                orderBy = orderBy,
                order = order
            )
        }
    }

    suspend fun getProductsBySearch(
        searchQuery: String,
        orderBy: String = OrderByEnum.POPULARITY.orderTypeString,
        order: String = OrderSortEnum.ASC.orderSortString, attribute: String, attributeTerm: String
    ): Resource<List<Product>> {
        return safeApiCall {
            productApiService.getProduct(
                searchQuery = searchQuery,
                orderBy = orderBy,
                order = order,
                attribute = attribute,
                terms = attributeTerm
            )
        }
    }
}