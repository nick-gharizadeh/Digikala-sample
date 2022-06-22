package com.example.digikalasample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digikalasample.data.model.product.Category
import com.example.digikalasample.data.model.product.Product
import com.example.digikalasample.data.model.review.Review
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
    val specialProduct = MutableLiveData<Product?>()
    var shoppingCardList: List<Product?> = emptyList()
    var reviewsList = MutableLiveData<List<Review?>>()
    val searchedProductsList = MutableLiveData<List<Product?>>()
    var orderCriterion: String? = null

    init {
        callServices()
    }

    fun callServices() {
        getAllCategories()
        getProducts("popularity", popularProductList)
        getProducts("rating", ratingProductList)
        getProducts("date", newestProductList)
        getSpecialProduct()
    }

    private fun getProducts(orderBy: String, relatedLiveData: MutableStateFlow<List<Product?>>) {
        viewModelScope.launch {
            relatedLiveData.emit(productRepository.getProducts(orderBy = orderBy))
        }
    }

    fun getProductsBySearch(
        searchQuery: String,
        orderBy: String = "popularity",
        order: String = "asc"
    ) {
        viewModelScope.launch {
            val list = productRepository.getProductsBySearch(searchQuery, orderBy, order)
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

    suspend fun getProductById(id: Int): Product? {
        return productRepository.getProductById(id)
    }

    // 📌 get slider photos
    private fun getSpecialProduct(id: Int = 608) {
        viewModelScope.launch {
            val list = productRepository.getProductById(id)
            specialProduct.value = list
        }
    }

    fun getReviews(id: String) {
        viewModelScope.launch {
            val list = productRepository.getReviews(id)
            reviewsList.value = list
        }
    }

    fun addToShoppingCard(product: Product? = this.product) {
        shoppingCardList = shoppingCardList.plus(product)
    }

    fun removeFromShoppingCard(product: Product? ) {
        shoppingCardList = shoppingCardList.minus(product)
    }
}