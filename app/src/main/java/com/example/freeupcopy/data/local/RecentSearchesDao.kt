package com.example.freeupcopy.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentSearchesDao {

    @Query("SELECT * FROM recent_searches ORDER BY id DESC")
    fun getRecentSearches(): Flow<List<RecentSearch>>

    @Upsert
    suspend fun insertRecentSearches(recentSearch: RecentSearch)

    @Delete
    suspend fun deleteRecentSearches(recentSearch: RecentSearch)
}