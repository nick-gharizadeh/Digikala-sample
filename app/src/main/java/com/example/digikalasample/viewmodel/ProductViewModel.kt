package com.example.digikalasample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digikalasample.data.model.Category
import com.example.digikalasample.data.model.Comment
import com.example.digikalasample.data.model.Product
import com.example.digikalasample.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productRepository: ProductsRepository) :
    ViewModel() {
    var product: Product? = null
    val popularProductList = MutableLiveData<List<Product?>>()
    val ratingProductList = MutableLiveData<List<Product?>>()
    val newestProductList = MutableLiveData<List<Product?>>()
    val categoriesList = MutableLiveData<List<Category?>>()
    val productByCategoriesList = MutableLiveData<List<Product?>>()
    val relatedProductById = MutableLiveData<Product?>()
    var reviewsList = MutableLiveData<List<Comment?>>()
    val searchedProductsList = MutableLiveData<List<Product?>>()

    init {
        callServices()
    }

    fun callServices() {
        getAllCategories()
        getProducts("popularity", popularProductList)
        getProducts("rating", ratingProductList)
        getProducts("date", newestProductList)
        getProductById(608)
    }

    private fun getProducts(orderBy: String, relatedLiveData: MutableLiveData<List<Product?>>) {
        viewModelScope.launch {
            val list = productRepository.getProducts(orderBy = orderBy)
            relatedLiveData.value = list
        }
    }

    fun getProductsBySearch(searchQuery: String) {
        viewModelScope.launch {
            val list = productRepository.getProductsBySearch(searchQuery)
            searchedProductsList.value = list
        }
    }


    fun getProductsByCategory(category: Int) {
        viewModelScope.launch {
            val list = productRepository.getProductsByCategory(category = category)
            productByCategoriesList.value = list
        }
    }

    private fun getAllCategories() {
        viewModelScope.launch {
            val list = productRepository.getCategories()
            categoriesList.value = list
        }
    }

    private fun getProductById(id:Int) {
        viewModelScope.launch {
            val list = productRepository.getProductById(id)
            relatedProductById.value=list
        }
    }


     fun getReviews(id:String) {
        viewModelScope.launch {
            val list = productRepository.getReviews(id)
            reviewsList.value=list
        }
    }


}