package com.example.freeupcopy.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentSearchesDao {

    @Query("SELECT * FROM recent_searches ORDER BY timestamp DESC")
    fun getRecentSearches(): Flow<List<RecentSearch>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentSearch(recentSearch: RecentSearch)

    @Delete
    suspend fun deleteRecentSearch(recentSearch: RecentSearch)

    @Query("DELETE FROM recent_searches")
    suspend fun deleteAllRecentSearches()

    @Query("SELECT * FROM recent_searches WHERE LOWER(recentSearch) = LOWER(:search) LIMIT 1")
    suspend fun getSearchIfExists(search: String): RecentSearch?
}
