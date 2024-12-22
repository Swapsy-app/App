package com.example.freeupcopy.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(
    tableName = "address",
    //indices = [Index(value = ["photoId"], unique = true)]
)
data class Address(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val address: String
)