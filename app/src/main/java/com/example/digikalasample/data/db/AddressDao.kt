package com.example.digikalasample.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.digikalasample.data.model.address.Address

@Dao
interface AddressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(address: Address?)

    @Query("SELECT * from address ")
    fun getAllAddress(): LiveData<List<Address?>?>?

    @Delete
    suspend fun deleteAddress(address: Address?)

    @Update
    suspend fun updateAddress(address: Address?)

}