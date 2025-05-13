package com.example.freeupcopy.data.remote.dto.product

data class AddCommentRequest(
    val commentText: String,
    val taggedUsernames: List<String>? = null
)