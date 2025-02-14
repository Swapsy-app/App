package com.example.freeupcopy.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "recent_searches",
    //indices = [Index(value = ["photoId"], unique = true)]
)
data class RecentSearch(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val recentSearch: String
)