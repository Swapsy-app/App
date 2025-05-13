package com.example.freeupcopy.data.remote.dto.product

import com.example.freeupcopy.data.remote.dto.sell.SellerId

data class Bargain(
    val _id: String,
    val productId: String,
    val sellerId: String,
    val buyerId: SellerId?, // Only when populated
    val offeredPrice: Float?,
    val offeredIn: String,
    val sellerReceives: String?,
    val message: String?,
    val status: String, // "pending", "accepted"
    val createdAt: String
)