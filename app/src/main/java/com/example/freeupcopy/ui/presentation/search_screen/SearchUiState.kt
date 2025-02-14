package com.example.freeupcopy.ui.presentation.search_screen

import com.example.freeupcopy.data.local.RecentSearch

data class SearchUiState(
    val searchQuery: String = "",
    val recentSearches: List<RecentSearch> = emptyList(),

    val isLoading: Boolean = false,
    val error: String = ""
)