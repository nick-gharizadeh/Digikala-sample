package com.example.digikalasample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digikalasample.data.model.address.Address
import com.example.digikalasample.data.repository.AddressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddressViewModel @Inject constructor(private val addressRepository: AddressRepository) : ViewModel() {

    val allAddresses: LiveData<List<Address?>?>?


    init {
        allAddresses = addressRepository.getLocalAddresses()
    }


    fun insertAddress(address: Address) {
        viewModelScope.launch {
            addressRepository.insertAddress(address)
        }
    }

    fun updateAddress(address: Address) {
        viewModelScope.launch {
            addressRepository.updateAddress(address)
        }
    }

    fun deleteAddress(address: Address) {
        viewModelScope.launch {
            addressRepository.deleteAddress(address)
        }
    }
}