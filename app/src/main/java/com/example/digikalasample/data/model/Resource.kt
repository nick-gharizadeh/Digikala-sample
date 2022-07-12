package com.example.digikalasample.data.model

import androidx.lifecycle.MutableLiveData

var statusLiveData = MutableLiveData<String>()


sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data = data)
    class Error<T>(errorMessage: String) : Resource<T>(message = errorMessage)
    class Loading<T> : Resource<T>()
}