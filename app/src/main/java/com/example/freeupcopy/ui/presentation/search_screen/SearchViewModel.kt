package com.example.freeupcopy.ui.presentation.search_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freeupcopy.data.local.RecentSearch
import com.example.freeupcopy.data.local.RecentSearchesDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val recentSearchesDao: RecentSearchesDao
) : ViewModel() {
    private val _state = MutableStateFlow(SearchUiState())
    val state = _state.asStateFlow()

    init {
        // Load recent searches
        viewModelScope.launch {
            recentSearchesDao.getRecentSearches().collect { recentSearches ->
                _state.update {
                    it.copy(recentSearches = recentSearches)
                }
            }

        }
    }

    fun onEvent(event: SearchUiEvent) {
        when (event) {
            is SearchUiEvent.SearchQueryChanged -> {
                _state.update {
                    it.copy(searchQuery = event.query)
                }
            }

            is SearchUiEvent.OnSearch -> {
                //do search
                viewModelScope.launch {
                    recentSearchesDao.insertRecentSearches(
                        RecentSearch(
                            recentSearch = _state.value.searchQuery
                        )
                    )
                }
            }

            is SearchUiEvent.OnClearSearch -> {
                _state.update {
                    it.copy(searchQuery = "")
                }
            }

            is SearchUiEvent.DeleteRecentSearch -> {
                // Delete the recent search
            }
        }
    }
}