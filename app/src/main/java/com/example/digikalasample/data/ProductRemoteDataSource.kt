package com.example.digikalasample.data

import androidx.lifecycle.MutableLiveData
import com.example.digikalasample.data.model.Category
import com.example.digikalasample.data.model.Product
import com.example.digikalasample.network.DigiKalaApiService
import java.net.UnknownHostException
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


    suspend fun getProductsBySearch(searchQuery: String): List<Product> {
        return try {
            errorThatOccur.value = null
            productApiService.getProduct(searchQuery = searchQuery)
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

}
