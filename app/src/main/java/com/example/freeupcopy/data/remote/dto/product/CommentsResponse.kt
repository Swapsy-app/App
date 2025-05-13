package com.example.freeupcopy.data.remote.dto.product

data class CommentsResponse(
    val currentPage: Int,
    val totalPages: Int,
    val totalCount: Int,
    val data: List<Comment>
)