package com.example.freeupcopy.ui.presentation.search_screen

import com.example.freeupcopy.data.local.RecentSearch
import com.example.freeupcopy.data.local.RecentlyViewed

data class SearchUiState(

    val searchQuery: String = "",
    val recentSearches: List<RecentSearch> = emptyList(),
    val suggestions: List<String> = emptyList(),

    val recentlyViewed: List<RecentlyViewed> = emptyList(),

    val isLoading: Boolean = false,
    val error: String = ""
)