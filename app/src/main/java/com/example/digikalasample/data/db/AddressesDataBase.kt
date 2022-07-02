package com.example.digikalasample.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.digikalasample.data.model.Address


@Database(entities = [Address::class], version = 1)
abstract class AddressesDataBase : RoomDatabase() {
    abstract fun addressDao(): AddressDao?

    companion object {
        const val DATABASE_NAME: String = "addDB"
    }
}