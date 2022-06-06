package com.example.digikalasample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digikalasample.data.model.Product
import com.example.digikalasample.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(val productRepository: ProductsRepository) :
    ViewModel() {
    val popularProductList = MutableLiveData<List<Product?>>()
    val ratingProductList = MutableLiveData<List<Product?>>()
    val newestProductList = MutableLiveData<List<Product?>>()

    init {
        getProducts("popularity", popularProductList)
        getProducts("rating", ratingProductList)
        getProducts("date", newestProductList)
    }

    fun getProducts(orderBy: String, relatedLiveData: MutableLiveData<List<Product?>>) {
        viewModelScope.launch {
            val list = productRepository.getProducts(orderBy = orderBy)
            relatedLiveData.value = list
        }
    }
}