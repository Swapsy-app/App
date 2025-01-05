package com.example.freeupcopy.ui.presentation.profile_screen.posted_products_screen

import androidx.lifecycle.ViewModel
import com.example.freeupcopy.ui.presentation.profile_screen.posted_products_screen.components.DeliveredState
import com.example.freeupcopy.ui.presentation.profile_screen.posted_products_screen.components.ListedState
import com.example.freeupcopy.ui.presentation.profile_screen.posted_products_screen.components.PendingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PostedProductsViewModel @Inject constructor(

): ViewModel() {
    private val _state = MutableStateFlow(PostedProductsUiState())
    val state = _state.asStateFlow()

    private val _listedState = MutableStateFlow(ListedState())
    val listedState = _listedState.asStateFlow()

    private val _pendingState = MutableStateFlow(PendingState())
    val pendingState = _pendingState.asStateFlow()

    private val _deliveredState = MutableStateFlow(DeliveredState())
    val deliveredStat = _deliveredState.asStateFlow()

    fun onEvent(event: PostedProductsUiEvent) {
        when (event) {
            is PostedProductsUiEvent.OnTabSelected -> {
                _state.update {
                    it.copy(currentTabIndex = event.index)
                }
            }
            is PostedProductsUiEvent.OnListingFilterSelected -> {
                _state.update {
                    it.copy(
                        listingFilterIndex = if (event.index == it.listingFilterIndex) null else event.index
                    )
                }
            }
            is PostedProductsUiEvent.OnPendingFilterSelected -> {
                _state.update {
                    it.copy(pendingFilterIndex = if (event.index == it.pendingFilterIndex) null else event.index)
                }
            }
            is PostedProductsUiEvent.OnDeliveredFilterSelected -> {
                _state.update {
                    it.copy(deliveredFilterIndex = if (event.index == it.deliveredFilterIndex) null else event.index)
                }
            }
            is PostedProductsUiEvent.OnSearchButtonClicked -> {
                _state.update {
                    it.copy(isSearching = !it.isSearching)
                }
            }
        }
    }
}