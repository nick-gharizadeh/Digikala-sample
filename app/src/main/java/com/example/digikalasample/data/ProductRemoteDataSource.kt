package com.example.digikalasample.data

import androidx.lifecycle.MutableLiveData
import com.example.digikalasample.data.model.product.Category
import com.example.digikalasample.data.model.review.Review
import com.example.digikalasample.data.model.product.Product
import com.example.digikalasample.network.DigiKalaApiService
import javax.inject.Inject

val errorThatOccur = MutableLiveData<Exception?>(null)

class ProductRemoteDataSource @Inject constructor(private val productApiService: DigiKalaApiService) {

    suspend fun getProducts(orderBy: String): List<Product> {
        return try {
            errorThatOccur.value = null
            productApiService.getProduct(orderBy = orderBy)
        } catch (e: Exception) {
            if (errorThatOccur.value == null)
                errorThatOccur.value = e
            listOf()
        }
    }


    suspend fun getProductsBySearch(searchQuery: String,orderBy: String = "popularity",order :String="asc"): List<Product> {
        return try {
            errorThatOccur.value = null
            productApiService.getProduct(searchQuery = searchQuery, orderBy = orderBy, order = order)
        } catch (e: Exception) {
            if (errorThatOccur.value == null)
                errorThatOccur.value = e
            listOf()
        }
    }

    suspend fun getProductsByCategory(category: Int): List<Product> {
        return try {
            errorThatOccur.value = null
            productApiService.getProduct(category = category)
        } catch (e: Exception) {
            if (errorThatOccur.value == null)
                errorThatOccur.value = e
            listOf()

        }
    }


    suspend fun getCategories(): List<Category> {
        return try {
            errorThatOccur.value = null
            productApiService.getCategories()
        } catch (e: Exception) {
            if (errorThatOccur.value == null)
                errorThatOccur.value = e
            listOf()
        }
    }

    suspend fun getProductById(id: Int): Product? {
        return try {
            errorThatOccur.value = null
            productApiService.getProductById(id = id)
        } catch (e: Exception) {
            if (errorThatOccur.value == null)
                errorThatOccur.value = e
            val product = null
            return product
        }
    }

    suspend fun getReviews(productId: String):List<Review> {
       return productApiService.getReviews(productId = productId)
    }

}
