package com.example.freeupcopy.ui.presentation.sell_screen.size_screen

import com.example.freeupcopy.data.remote.dto.sell.Size

data class SizeUiState(
    val selectedSize: Size? = null,
    val expandedAttribute: String? = null,
    val selectedAttributes: Map<String, String> = emptyMap(),
    val isFreeSize: Boolean = false,
    val sizeString: String = "",

    val isLoading: Boolean = false,
    val error: String = ""
)