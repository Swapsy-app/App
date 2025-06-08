package com.example.freeupcopy.data.remote.dto.sell

data class ShapesResponse(
    val message: String,
    val category: String,
    val shapes: List<ShapeItem>
)

data class ShapeItem(
    val name: String,
    val imageUrl: String
)