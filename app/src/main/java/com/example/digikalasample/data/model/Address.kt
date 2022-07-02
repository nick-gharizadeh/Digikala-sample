package com.example.digikalasample.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Address(
    @PrimaryKey var id: Int,
    @ColumnInfo var name: String?,
    @ColumnInfo var addressField: String?,
    @ColumnInfo var theLat: String,
    @ColumnInfo var theLong: String
)