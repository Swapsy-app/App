package com.example.freeupcopy.data.remote.dto.product

data class AddReplyRequest(
    val replyText: String,
    val taggedUsernames: List<String>? = null
)
