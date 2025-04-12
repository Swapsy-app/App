package com.example.freeupcopy.ui.presentation.search_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.local.RecentSearch
import com.example.freeupcopy.data.local.RecentSearchesDao
import com.example.freeupcopy.domain.repository.SellRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val recentSearchesDao: RecentSearchesDao,
    private val sellRepository: SellRepository
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
                getSuggestions(event.query)
            }

            is SearchUiEvent.OnSearch -> {
                viewModelScope.launch {
                    val query = _state.value.searchQuery.trim()
                    if (query.isNotBlank()) {
                        val existingSearch = recentSearchesDao.getSearchIfExists(query)
                        if (existingSearch == null) {
                            val recentSearch = RecentSearch(recentSearch = query)
                            recentSearchesDao.insertRecentSearch(recentSearch)
                        } else {
                            val updatedSearch = existingSearch.copy(timestamp = System.currentTimeMillis())
                            recentSearchesDao.insertRecentSearch(updatedSearch)
                        }
                    }
                }
            }


            is SearchUiEvent.OnClearSearch -> {
                _state.update {
                    it.copy(
                        searchQuery = "",
                        suggestions = emptyList()
                    )
                }
            }

            is SearchUiEvent.DeleteRecentSearch -> {
                viewModelScope.launch {
                    recentSearchesDao.deleteRecentSearch(event.recentSearch)
                }
            }

            is SearchUiEvent.ClearAllRecentSearches -> {
                viewModelScope.launch {
                    recentSearchesDao.deleteAllRecentSearches()
                }
            }

            is SearchUiEvent.SelectRecentSearch -> {
                viewModelScope.launch {
                    val updatedSearch = event.recentSearch.copy(timestamp = System.currentTimeMillis())
                    recentSearchesDao.insertRecentSearch(updatedSearch)
                    _state.update {
                        it.copy(searchQuery = updatedSearch.recentSearch)
                    }
                }
            }


        }
    }

    private fun getSuggestions(query: String) {
        viewModelScope.launch {
            sellRepository.getAutoComplete(query).collect { suggestion ->
                when (suggestion) {
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                suggestions = suggestion.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                error = suggestion.message ?: "An unexpected error occurred",
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

}