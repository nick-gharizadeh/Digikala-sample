package com.example.digikalasample.data

import androidx.lifecycle.LiveData
import com.example.digikalasample.data.db.AddressesDataBase
import com.example.digikalasample.data.model.Address
import javax.inject.Inject


class AddressLocalDataSource @Inject constructor(val db: AddressesDataBase) {
    val allAddresses: LiveData<List<Address?>?>?


    init {
        allAddresses = db.addressDao()?.getAllAddress()

    }

    suspend fun insertMovie(address: Address) {
        db.addressDao()?.insert(address)
    }
}