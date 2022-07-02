package com.example.digikalasample.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.digikalasample.data.model.Address

@Dao
interface AddressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(address: Address?)

    @Query("SELECT * from address  ")
    fun getAllAddress(): LiveData<List<Address?>?>?

    @Query("DELETE FROM address WHERE address=(:address)")
    suspend fun deleteAddress(address: Address)

}