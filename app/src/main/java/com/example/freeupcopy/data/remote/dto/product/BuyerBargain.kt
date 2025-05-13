package com.example.freeupcopy.data.remote.dto.product

import com.example.freeupcopy.data.remote.dto.sell.SellerId

data class BuyerBargain(
    val _id: String,
    val status: String,
    val offeredPrice: Float?,
    val sellerReceives: String?,
    val product: ProductSummary,
    val seller: SellerId
)