package com.example.digikalasample.data.repository

import com.example.digikalasample.data.model.Resource
import com.example.digikalasample.data.model.customer.Customer
import com.example.digikalasample.network.DigiKalaApiService
import javax.inject.Inject

class CustomerRepository @Inject constructor(private val productApiService: DigiKalaApiService) :
    BaseRepo() {

    suspend fun createCustomer(
        firstName: String,
        lastName: String,
        email: String
    ): Resource<Customer?> {
        return safeApiCall {
            productApiService.createCustomer(
                firstName = firstName,
                lastName = lastName,
                email = email
            )
        }

    }


    suspend fun getCustomer(id: Int): Resource<Customer?> {
        return safeApiCall {
            productApiService.getCustomer(id = id)
        }
    }

}