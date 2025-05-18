package com.example.freeupcopy.data.remote.dto.product

data class SellerBargain(
    val _id: String,
    val status: String,
    val offeredPrice: Float?,
    val sellerReceives: String?,
    val message: String?,
    val product: ProductSummary,
    val buyer: User,
    val createdAt: String
)