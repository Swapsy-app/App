package com.example.freeupcopy.domain.model

import com.example.freeupcopy.domain.enums.Currency


data class Comment(
    val id: String,
    val username: String,
    val userId: String,
    val text: String,
    val replies: List<Reply>,
    val timeStamp : String
)

data class Reply(
    val id: String,
    val user: String,
    val userId: String,
    val text: String,
    val timeStamp : String
)

data class BargainOffer(
    val id : String,
    val username : String,
    val userId: String,
    val message: String,
    val amount: String,
    val currency: Currency,
    val timeStamp : String
)