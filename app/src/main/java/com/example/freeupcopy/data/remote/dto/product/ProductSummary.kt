package com.example.freeupcopy.data.remote.dto.product

import com.example.freeupcopy.data.remote.dto.sell.MixPrice

data class ProductSummary(
    val _id: String,
    val title: String,
    val image: String?,
    val mrp: Float?,
    val cashPrice: Float?,
    val coinPrice: Float?,
    val mixPrice: MixPrice?
)