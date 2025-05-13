package com.example.freeupcopy.data.remote.dto.product

data class Comment(
    val _id: String,
    val productId: String,
    val userId: User,
    val commentText: String,
    val taggedUsers: List<User>,
    val replyCount: Int,
    val createdAt: String,
    val updatedAt: String
)