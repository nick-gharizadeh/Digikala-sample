package com.example.digikalasample.data.model.address

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Address(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo var name: String?,
    @ColumnInfo var addressField: String?,
    @ColumnInfo var theLat: String,
    @ColumnInfo var theLong: String
) : Serializable