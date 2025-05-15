package com.example.freeupcopy.ui.presentation.home_screen

import com.example.freeupcopy.data.remote.dto.product.User

data class HomeUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String = "",
)