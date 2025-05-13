package com.example.freeupcopy.data.remote.dto.product

import com.example.freeupcopy.data.remote.dto.sell.SellerId

data class ProductBargain(
    val _id: String,
    val productId: String,
    val sellerId: String,
    val buyerId: SellerId?,
    val offeredIn: String,
    val status: String,
    val message: String?,
    val offeredPrice: Float?,
    val sellerReceives: String?
)