package com.example.digikalasample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digikalasample.data.model.customer.Customer
import com.example.digikalasample.data.model.product.Product
import com.example.digikalasample.data.model.review.Review
import com.example.digikalasample.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ReviewViewModel @Inject constructor(private val productRepository: ProductsRepository) :
    ViewModel() {

    var reviewsList = MutableLiveData<List<Review?>>()
    var mCustomer = MutableLiveData<Customer?>()
    var mProduct: Product? = null


    fun getReviews(id: String) {
        viewModelScope.launch {
            val list = productRepository.getReviews(id)
            reviewsList.value = list.data!!
        }
    }

    fun postReview(
        review: Review
    ) {
        viewModelScope.launch {
            productRepository.postReview(review)
        }

    }

    fun deleteReview(id: Int) {
        viewModelScope.launch {
            productRepository.deleteReview(id)
        }
    }

    fun updateReview(id: Int, review: String) {
        viewModelScope.launch {
            productRepository.updateReview(id, review)
        }
    }
}