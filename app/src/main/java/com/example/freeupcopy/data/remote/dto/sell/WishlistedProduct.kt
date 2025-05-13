package com.example.freeupcopy.data.remote.dto.sell

data class WishlistedProduct(
    val productId: String,
    val wishlistId: String,
    val image: String,
    val title: String,
    val brand: String?,
    val size: Size?,           // or whatever your size model is
    val price: Price,          // reuse your existing Price model
    val status: String,
    val seller: SellerId?,       // reuse your existing Seller model
    val createdAt: String
)