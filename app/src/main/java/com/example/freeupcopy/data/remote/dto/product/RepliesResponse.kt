package com.example.freeupcopy.data.remote.dto.product

data class RepliesResponse(
    val currentPage: Int,
    val totalPages: Int,
    val totalCount: Int,
    val data: List<Reply>
)