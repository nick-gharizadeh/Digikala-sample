package com.example.digikalasample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digikalasample.data.model.FilterItem
import com.example.digikalasample.data.model.product.Product
import com.example.digikalasample.data.model.statusLiveData
import com.example.digikalasample.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(private val productRepository: ProductsRepository) :
    ViewModel() {
    val searchedProductsList = MutableLiveData<List<Product>>()
    var orderCriterion: String? = "popularity"
    var orderSortType: String? = "asc"
    var lastSearch: String = ""

    fun getProductsBySearch(
        searchQuery: String,
        orderBy: String = "popularity",
        order: String = "asc"
    ) {
        viewModelScope.launch {
            val searchResponse = productRepository.getProductsBySearch(searchQuery, orderBy, order)
            if (searchResponse.message == null)
                searchedProductsList.value = searchResponse.data!!
            else
                statusLiveData.postValue(searchResponse.message)
        }
    }

    private fun getProductsBySearch(
        searchQuery: String,
        orderBy: String = "popularity",
        order: String = "asc", attribute: String, attributeTerm: String
    ) {
        viewModelScope.launch {
            val searchResponse = productRepository.getProductsBySearch(
                searchQuery,
                orderBy,
                order,
                attribute,
                attributeTerm
            )
            if (searchResponse.message == null)
                searchedProductsList.value = searchResponse.data!!
            else
                statusLiveData.postValue(searchResponse.message)

        }
    }


    fun doFilterByColor(filterItem: FilterItem) {
        orderSortType?.let {
            orderCriterion?.let { it1 ->
                getProductsBySearch(
                    lastSearch, it1,
                    it, "pa_color", filterItem.id.toString()
                )
            }
        }
    }


    fun doFilterBySize(filterItem: FilterItem) {
        orderSortType?.let {
            orderCriterion?.let { it1 ->
                getProductsBySearch(
                    lastSearch, it1,
                    it, "pa_size", filterItem.id.toString()
                )
            }
        }
    }

    override fun onCleared() {
        searchedProductsList.value = emptyList()
        super.onCleared()
    }
}