package com.example.freeupcopy.data.remote.dto.sell

data class FetchWishlistResponse(
    val success: Boolean,
    val products: List<WishlistedProduct>,
    val total: Int,
    val page: Int,
    val totalPages: Int
)