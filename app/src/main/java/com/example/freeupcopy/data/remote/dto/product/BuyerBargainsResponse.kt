package com.example.freeupcopy.data.remote.dto.product

data class BuyerBargainsResponse(
    val totalPages: Int,
    val currentPage: Int,
    val totalBargains: Int,
    val bargains: List<BuyerBargain>
)