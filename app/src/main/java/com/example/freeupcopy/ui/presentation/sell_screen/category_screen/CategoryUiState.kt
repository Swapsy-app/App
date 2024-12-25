package com.example.freeupcopy.ui.presentation.sell_screen.category_screen

import com.example.freeupcopy.domain.model.CategoryUiModel

data class CategoryUiState(
    val selectedCategory: CategoryUiModel? = null,
    val isExpanded: Boolean = false
)