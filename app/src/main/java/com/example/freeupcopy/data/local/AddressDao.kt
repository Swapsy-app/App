package com.example.freeupcopy.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressDao {

    @Query("SELECT * FROM address ORDER BY id DESC")
    fun getAddresses(): Flow<List<Address>>

    @Query("SELECT * FROM address WHERE id = :id")
    suspend fun getAddressById(id: Int): Address?

    @Upsert
    suspend fun insertAddress(address: Address)

    @Delete
    suspend fun deleteAddress(address: Address)
}