package com.example.freeupcopy.domain.model

import com.example.freeupcopy.domain.enums.PricingModel

data class ProfileProductListing(
    val imageUrl: String,
    val productId: String,
    val title: String,
    val pricingModels: List<PricingModel>,
    val cashPrice: String? = null,
    val coinPrice: String? = null,
    val combinedPrice: Pair<String, String>? = null,
    val favoriteCount: String,
    val shareCount: String,
    val offerCount: String
)
