package com.example.freeupcopy.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.freeupcopy.data.local.Address
import com.example.freeupcopy.data.local.AddressDao
import com.example.freeupcopy.data.local.RecentSearch
import com.example.freeupcopy.data.local.RecentSearchesDao
import com.example.freeupcopy.data.local.RecentlyViewed
import com.example.freeupcopy.data.local.RecentlyViewedDao

@Database(
    entities = [Address::class, RecentSearch::class, RecentlyViewed::class],
    version = 1,
    exportSchema = false
)
abstract class SwapsyDatabase: RoomDatabase() {
    abstract val addressDao: AddressDao
    abstract val recentSearchesDao: RecentSearchesDao
    abstract val recentlyViewedDao: RecentlyViewedDao
}