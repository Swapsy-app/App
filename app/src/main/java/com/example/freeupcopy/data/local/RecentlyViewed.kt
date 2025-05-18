package com.example.freeupcopy.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_views")
data class RecentlyViewed(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: String,
    val title: String,
    val timestamp: Long = System.currentTimeMillis(),
    val imageUrl: String
)
