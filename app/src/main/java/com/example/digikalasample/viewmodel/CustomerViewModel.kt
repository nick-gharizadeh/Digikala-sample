package com.example.digikalasample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digikalasample.data.model.customer.Customer
import com.example.digikalasample.data.model.statusLiveData
import com.example.digikalasample.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CustomerViewModel @Inject constructor(private val productRepository: ProductsRepository) :
    ViewModel() {

    var mCustomer = MutableLiveData<Customer?>()
    var mCustomerId: Int? = null


    fun getCustomer(id: Int) {
        viewModelScope.launch {
            val customer = productRepository.getCustomer(id)
            if (customer.message == null)
                mCustomer.postValue(customer.data)
            else
                statusLiveData.postValue(customer.message)
        }
    }


    fun createCustomer(firstName: String, lastName: String, email: String) {
        viewModelScope.launch {
            val customer = productRepository.createCustomer(
                firstName = firstName,
                lastName = lastName,
                email = email
            )
            if (customer.message == null) {
                mCustomer.postValue(customer.data)
                mCustomerId = customer.data?.id
            } else
                statusLiveData.postValue(customer.message)
        }
    }
}