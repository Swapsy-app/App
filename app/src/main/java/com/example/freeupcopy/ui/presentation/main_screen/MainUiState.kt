package com.example.freeupcopy.ui.presentation.main_screen

data class MainUiState(
    val isUserLoggedIn: Boolean = false,
    val isUserVerified: Boolean = false,

    val currentPage: Int = 0,

    val isLoading: Boolean = false,
    val error : String = ""
)