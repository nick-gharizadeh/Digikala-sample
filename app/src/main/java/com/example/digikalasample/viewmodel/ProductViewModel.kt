package com.example.digikalasample.viewmodel

import androidx.lifecycle.ViewModel
import com.example.digikalasample.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(val productRepository: ProductsRepository) :
    ViewModel() {

}