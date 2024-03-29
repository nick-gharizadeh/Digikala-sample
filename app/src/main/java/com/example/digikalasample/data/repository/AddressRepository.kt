package com.example.digikalasample.data.repository

import androidx.lifecycle.LiveData
import com.example.digikalasample.data.db.AddressesDataBase
import com.example.digikalasample.data.model.address.Address
import javax.inject.Inject

class AddressRepository @Inject constructor(private val db: AddressesDataBase) {
    private val allAddresses: LiveData<List<Address?>?>?

    fun getLocalAddresses(): LiveData<List<Address?>?>? {
        return allAddresses
    }

    init {
        allAddresses = db.addressDao()?.getAllAddress()
    }

    suspend fun insertAddress(address: Address) {
        db.addressDao()?.insert(address)
    }

    suspend fun updateAddress(address: Address) {
        db.addressDao()?.updateAddress(address)
    }

    suspend fun deleteAddress(address: Address) {
        db.addressDao()?.deleteAddress(address)
    }

}