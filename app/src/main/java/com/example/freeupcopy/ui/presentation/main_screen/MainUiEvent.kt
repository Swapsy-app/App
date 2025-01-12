package com.example.freeupcopy.ui.presentation.main_screen

sealed class MainUiEvent {
    data class CurrentPageChange(val currentPage: Int) : MainUiEvent()
}