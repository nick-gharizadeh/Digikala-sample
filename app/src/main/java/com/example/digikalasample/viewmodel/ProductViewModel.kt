package com.example.digikalasample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digikalasample.data.model.Category
import com.example.digikalasample.data.model.Review
import com.example.digikalasample.data.model.Product
import com.example.digikalasample.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productRepository: ProductsRepository) :
    ViewModel() {
    var product: Product? = null
    val popularProductList = MutableStateFlow<List<Product?>>(emptyList())
    val ratingProductList = MutableStateFlow<List<Product?>>(emptyList())
    val newestProductList = MutableStateFlow<List<Product?>>(emptyList())
    val categoriesList = MutableStateFlow<List<Category?>>(emptyList())
    val productByCategoriesList = MutableLiveData<List<Product?>>()
    val relatedProductById = MutableLiveData<Product?>()
    var reviewsList = MutableLiveData<List<Review?>>()
    val searchedProductsList = MutableLiveData<List<Product?>>()
    var orderCriterion : String? = null

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

    private fun getProducts(orderBy: String, relatedLiveData: MutableStateFlow<List<Product?>>) {
        viewModelScope.launch {
            relatedLiveData.emit(productRepository.getProducts(orderBy = orderBy))
        }
    }

    fun getProductsBySearch(searchQuery: String,orderBy: String = "popularity",order :String="asc") {
        viewModelScope.launch {
            val list = productRepository.getProductsBySearch(searchQuery,orderBy,order)
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
            categoriesList.emit(productRepository.getCategories())
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