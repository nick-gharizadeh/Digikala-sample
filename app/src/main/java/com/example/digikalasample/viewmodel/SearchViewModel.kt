package com.example.digikalasample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digikalasample.data.FilterTypeEnum
import com.example.digikalasample.data.OrderByEnum
import com.example.digikalasample.data.OrderSortEnum
import com.example.digikalasample.data.model.FilterItem
import com.example.digikalasample.data.model.product.Product
import com.example.digikalasample.data.model.statusLiveData
import com.example.digikalasample.data.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(private val searchRepository: SearchRepository) :
    ViewModel() {
    val searchedProductsList = MutableLiveData<List<Product>>()
    var orderCriterion: String? = OrderByEnum.POPULARITY.orderTypeString
    var orderSortType: String? = OrderSortEnum.ASC.orderSortString
    var lastSearch: String = ""

    fun getProductsBySearch(
        searchQuery: String,
        orderBy: String = OrderByEnum.POPULARITY.orderTypeString,
        order: String = OrderSortEnum.ASC.orderSortString
    ) {
        viewModelScope.launch {
            val searchResponse = searchRepository.getProductsBySearch(searchQuery, orderBy, order)
            if (searchResponse.message == null)
                searchedProductsList.value = searchResponse.data!!
            else
                statusLiveData.postValue(searchResponse.message)
        }
    }

    private fun getProductsBySearch(
        searchQuery: String,
        orderBy: String = OrderByEnum.POPULARITY.orderTypeString,
        order: String = OrderSortEnum.ASC.orderSortString, attribute: String, attributeTerm: String
    ) {
        viewModelScope.launch {
            val searchResponse = searchRepository.getProductsBySearch(
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
                    it, FilterTypeEnum.Color.filterTypeString, filterItem.id.toString()
                )
            }
        }
    }


    fun doFilterBySize(filterItem: FilterItem) {
        orderSortType?.let {
            orderCriterion?.let { it1 ->
                getProductsBySearch(
                    lastSearch, it1,
                    it, FilterTypeEnum.Size.filterTypeString, filterItem.id.toString()
                )
            }
        }
    }

    override fun onCleared() {
        searchedProductsList.value = emptyList()
        super.onCleared()
    }
}