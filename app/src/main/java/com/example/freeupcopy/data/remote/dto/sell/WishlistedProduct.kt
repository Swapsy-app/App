package com.example.freeupcopy.data.remote.dto.sell

import com.example.freeupcopy.data.remote.dto.product.User

data class WishlistedProduct(
    val productId: String,
    val wishlistId: String,
    val image: String,
    val title: String,
    val brand: String?,
    val size: Size?,           // or whatever your size model is
    val price: ProductPrice,          // reuse your existing Price model
    val status: String,
    val seller: User?,       // reuse your existing Seller model
    val createdAt: String
)