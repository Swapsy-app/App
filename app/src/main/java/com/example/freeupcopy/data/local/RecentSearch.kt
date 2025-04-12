package com.example.freeupcopy.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_searches")
data class RecentSearch(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val recentSearch: String,
    val timestamp: Long = System.currentTimeMillis()
)
