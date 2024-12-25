package com.example.freeupcopy.ui.presentation.sell_screen.category_screen

import com.example.freeupcopy.domain.model.CategoryUiModel

sealed class CategoryUiEvent {
    data class CategorySelected(val category: CategoryUiModel) : CategoryUiEvent()
    data object ExpandChange : CategoryUiEvent()
}