package com.example.freeupcopy.data.remote.dto.product

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val _id: String,
    val username: String,
    val avatar: String?
) : Parcelable