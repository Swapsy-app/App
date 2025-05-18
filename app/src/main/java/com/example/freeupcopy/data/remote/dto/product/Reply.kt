package com.example.freeupcopy.data.remote.dto.product

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Reply(
    val _id: String,
    val commentId: String,
    val userId: User,
    val replyText: String,
    val taggedUsers: List<User>,
    val createdAt: String,
    val updatedAt: String
): Parcelable
