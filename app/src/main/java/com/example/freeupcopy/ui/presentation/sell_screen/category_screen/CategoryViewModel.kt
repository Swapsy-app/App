package com.example.freeupcopy.ui.presentation.sell_screen.category_screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(): ViewModel() {

    private val _state = MutableStateFlow(CategoryUiState())
    val state: StateFlow<CategoryUiState> = _state.asStateFlow()

    fun onEvent(event: CategoryUiEvent) {
        when (event) {
            is CategoryUiEvent.CategorySelected -> {
                _state.update { it.copy(selectedCategory = event.category, isExpanded = false) }
            }
            is CategoryUiEvent.ExpandChange -> {
                _state.update { it.copy(isExpanded = !it.isExpanded) }
            }
        }
    }
}