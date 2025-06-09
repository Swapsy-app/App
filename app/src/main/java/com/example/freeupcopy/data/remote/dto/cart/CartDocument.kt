package com.example.freeupcopy.data.remote.dto.cart

data class CartDocument(
    val buyerId: String,
    val sellerId: String,
    val products: List<String>,
    val createdAt: String,
    val updatedAt: String
)