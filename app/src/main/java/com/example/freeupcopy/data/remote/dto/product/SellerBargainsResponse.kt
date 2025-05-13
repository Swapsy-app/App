package com.example.freeupcopy.data.remote.dto.product

data class SellerBargainsResponse(
    val totalPages: Int,
    val currentPage: Int,
    val totalBargains: Int,
    val bargains: List<SellerBargain>
)