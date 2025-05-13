package com.example.freeupcopy.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentlyViewedDao {

    @Query("SELECT * FROM recent_views ORDER BY timestamp DESC")
    fun getRecentlyViewed(): Flow<List<RecentlyViewed>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentlyViewed(recentlyViewed: RecentlyViewed)

    @Delete
    suspend fun deleteRecentlyViewed(recentlyViewed: RecentlyViewed)

    @Query("DELETE FROM recent_views")
    suspend fun deleteAllRecentlyViewed()

    @Query("SELECT * FROM recent_views WHERE LOWER(productId) = LOWER(:productId) LIMIT 1")
    suspend fun getRecentlyViewExists(productId: String): RecentlyViewed?

    @Query("SELECT COUNT(*) FROM recent_views")
    suspend fun getCount(): Int

    @Query("DELETE FROM recent_views WHERE id IN (SELECT id FROM recent_views ORDER BY timestamp ASC LIMIT :limit)")
    suspend fun deleteOldest(limit: Int): Int

    @Transaction
    suspend fun insertWithLimit(recentlyViewed: RecentlyViewed) {
        // Insert the new record.
        insertRecentlyViewed(recentlyViewed)
        // Check the total count.
        val count = getCount()
        // If more than 10 records exist, delete the oldest entries to keep only 10.
        if (count > 10) {
            deleteOldest(count - 10)
        }
    }
}
