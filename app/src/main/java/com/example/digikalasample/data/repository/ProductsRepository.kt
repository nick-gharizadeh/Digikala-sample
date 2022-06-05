package com.example.digikalasample.data.repository

import com.example.digikalasample.data.ProductRemoteDataSource
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    val productRemoteDataSource: ProductRemoteDataSource,
) {


}
