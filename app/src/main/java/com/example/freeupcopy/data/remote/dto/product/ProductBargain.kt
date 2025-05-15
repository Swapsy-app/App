package com.example.freeupcopy.data.remote.dto.product

data class ProductBargain(
    val _id: String,
    val productId: String,
    val sellerId: String,
    val buyerId: User?,
    val offeredIn: String,
    val status: String,
    val message: String?,
    val offeredPrice: Float?,
    val sellerReceives: String?
)