package com.example.freeupcopy.data.remote.dto.product

data class ProductBargainListResponse(
    val userType: String, // "seller", "buyer", "public"
    val currentPage: Int,
    val totalBargains: Int,
    val hasNextPage: Boolean,
    val bargains: List<ProductBargain>
)