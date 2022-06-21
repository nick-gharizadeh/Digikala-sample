package com.example.digikalasample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.digikalasample.data.model.product.Product

class ShoppingCartViewModel: ViewModel() {
    val shoppingCardList = MutableLiveData<List<Product?>>()

    fun addToShoppingCard(product:Product){
        shoppingCardList.value?.plus(product)
        }



    }


