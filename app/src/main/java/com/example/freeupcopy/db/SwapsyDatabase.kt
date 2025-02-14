package com.example.freeupcopy.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.freeupcopy.data.local.Address
import com.example.freeupcopy.data.local.AddressDao
import com.example.freeupcopy.data.local.RecentSearch
import com.example.freeupcopy.data.local.RecentSearchesDao

@Database(
    entities = [Address::class, RecentSearch::class],
    version = 1
)
abstract class SwapsyDatabase: RoomDatabase() {
    abstract val addressDao: AddressDao
    abstract val recentSearchesDao: RecentSearchesDao
}