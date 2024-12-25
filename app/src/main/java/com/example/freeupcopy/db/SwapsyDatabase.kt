package com.example.freeupcopy.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.freeupcopy.data.local.Address
import com.example.freeupcopy.data.local.AddressDao

@Database(
    entities = [Address::class],
    version = 1
)
abstract class SwapsyDatabase: RoomDatabase() {
    abstract val addressDao: AddressDao
}