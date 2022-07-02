package com.example.digikalasample.data.repository

import androidx.lifecycle.LiveData
import com.example.digikalasample.data.AddressLocalDataSource
import com.example.digikalasample.data.model.Address
import javax.inject.Inject

class AddressRepository @Inject constructor(
    private val AddressLocalDataSource: AddressLocalDataSource
) {
    val allAddresses: LiveData<List<Address?>?>?

    init {
        allAddresses = AddressLocalDataSource.allAddresses

    }

    fun getLocalAddresses(): LiveData<List<Address?>?>? {
        return allAddresses
    }

    suspend fun insertAddress(address: Address) {
        AddressLocalDataSource.insertMovie(address)
    }

}