package com.example.freeupcopy.ui.presentation.search_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.local.RecentSearch
import com.example.freeupcopy.data.local.RecentSearchesDao
import com.example.freeupcopy.data.local.RecentlyViewed
import com.example.freeupcopy.data.local.RecentlyViewedDao
import com.example.freeupcopy.domain.repository.SellRepository
import com.example.freeupcopy.ui.presentation.product_listing.ProductListingUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val recentSearchesDao: RecentSearchesDao,
    private val recentlyViewedDao: RecentlyViewedDao,
    private val sellRepository: SellRepository
) : ViewModel() {
    private val _state = MutableStateFlow(SearchUiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                recentSearchesDao.getRecentSearches(),
                recentlyViewedDao.getRecentlyViewed()
            ) { recentSearches, recentlyViewed ->
                Pair(recentSearches, recentlyViewed)
            }.collect { (recentSearches, recentlyViewed) ->
                Log.e(
                    "SearchViewModel",
                    "Recent Searches: $recentSearches\nRecently Viewed: $recentlyViewed"
                )
                _state.update { currentState ->
                    currentState.copy(
                        recentSearches = recentSearches,
                        recentlyViewed = recentlyViewed
                    )
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
                            val updatedSearch =
                                existingSearch.copy(timestamp = System.currentTimeMillis())
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
                    val updatedSearch =
                        event.recentSearch.copy(timestamp = System.currentTimeMillis())
                    recentSearchesDao.insertRecentSearch(updatedSearch)
                    _state.update {
                        it.copy(searchQuery = updatedSearch.recentSearch)
                    }
                }
            }

            is SearchUiEvent.ClearAllRecentlyViewed -> {
                viewModelScope.launch {
                    recentlyViewedDao.deleteAllRecentlyViewed()
                }
            }

            is SearchUiEvent.ProductClicked -> {
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true) }

                    val existingRecentlyViewed = recentlyViewedDao.getRecentlyViewExists(event.productId)
                    if(existingRecentlyViewed != null) {
                        val updateRecentlyViewed = existingRecentlyViewed.copy(timestamp = System.currentTimeMillis())
                        recentlyViewedDao.insertRecentlyViewed(updateRecentlyViewed)
                    }

                    _state.update { it.copy(isLoading = false) }
                }
            }

            is SearchUiEvent.IsLoading -> {
                _state.update {
                    it.copy(isLoading = event.isLoading)
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