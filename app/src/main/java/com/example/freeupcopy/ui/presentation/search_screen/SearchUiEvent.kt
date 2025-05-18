package com.example.freeupcopy.ui.presentation.search_screen

import com.example.freeupcopy.data.local.RecentSearch

sealed class SearchUiEvent {
    data class SearchQueryChanged(val query: String): SearchUiEvent()
    data object OnSearch: SearchUiEvent()
    data object OnClearSearch: SearchUiEvent()
    data class DeleteRecentSearch(val recentSearch: RecentSearch): SearchUiEvent()
    data object ClearAllRecentSearches: SearchUiEvent()
    data class SelectRecentSearch(val recentSearch: RecentSearch): SearchUiEvent()

    data class IsLoading(val isLoading: Boolean): SearchUiEvent()

    data object ClearAllRecentlyViewed: SearchUiEvent()
    data class ProductClicked(val productId: String) : SearchUiEvent()
}