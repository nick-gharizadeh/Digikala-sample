package com.example.digikalasample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digikalasample.data.model.customer.Customer
import com.example.digikalasample.data.model.product.Product
import com.example.digikalasample.data.model.review.Review
import com.example.digikalasample.data.model.statusLiveData
import com.example.digikalasample.data.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ReviewViewModel @Inject constructor(private val reviewRepository: ReviewRepository) :
    ViewModel() {

    var reviewsList = MutableLiveData<List<Review?>>()
    var mCustomer = MutableLiveData<Customer?>()
    var mProduct: Product? = null


    fun getReviews(id: String) {
        viewModelScope.launch {
            val response = reviewRepository.getReviews(id)
            if (response.message == null)
                reviewsList.value = response.data!!
            else
                statusLiveData.postValue(response.message)
        }
    }

    fun postReview(
        review: Review
    ) {
        viewModelScope.launch {
            val response = reviewRepository.postReview(review)
            if (response.message != null)
                statusLiveData.postValue(response.message)
        }

    }

    fun deleteReview(id: Int) {
        viewModelScope.launch {
            val response = reviewRepository.deleteReview(id)
            if (response.message != null)
                statusLiveData.postValue(response.message)
        }
    }

    fun updateReview(id: Int, review: String) {
        viewModelScope.launch {
            val response = reviewRepository.updateReview(id, review)
            if (response.message != null)
                statusLiveData.postValue(response.message)
        }
    }
}