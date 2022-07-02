package com.example.digikalasample.data.model

import androidx.room.Entity


@Entity
data class Address(
    val id: Int,
    val name: String,
    val address: String,
    val lat: Float,
    val long: Float
)