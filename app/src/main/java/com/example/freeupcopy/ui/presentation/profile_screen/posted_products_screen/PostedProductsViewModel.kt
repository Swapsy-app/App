package com.example.freeupcopy.ui.presentation.profile_screen.posted_products_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.freeupcopy.data.pref.SwapGoPref
import com.example.freeupcopy.domain.use_case.GetUserProductsUseCase
import com.example.freeupcopy.ui.presentation.profile_screen.posted_products_screen.components.DeliveredState
import com.example.freeupcopy.ui.presentation.profile_screen.posted_products_screen.components.ListedState
import com.example.freeupcopy.ui.presentation.profile_screen.posted_products_screen.components.PendingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class PostedProductsViewModel @Inject constructor(
    private val getUserProductsUseCase: GetUserProductsUseCase,
    private val swapGoPref: SwapGoPref
): ViewModel() {
    private val _state = MutableStateFlow(PostedProductsUiState())
    val state = _state.asStateFlow()

    private val _listedState = MutableStateFlow(ListedState())
    val listedState = _listedState.asStateFlow()

    private val _pendingState = MutableStateFlow(PendingState())
    val pendingState = _pendingState.asStateFlow()

    private val _deliveredState = MutableStateFlow(DeliveredState())
    val deliveredStat = _deliveredState.asStateFlow()

    private val _currentUserId = MutableStateFlow<String?>(null)

    // Create paging flows for each tab with status filtering
    val listedProducts = _currentUserId
        .flatMapLatest { userId ->
            if (userId != null) {
                getUserProductsUseCase(userId, "available") // or null for all statuses
            } else {
                flowOf(PagingData.empty())
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            PagingData.empty()
        )

    val pendingProducts = _currentUserId
        .flatMapLatest { userId ->
            if (userId != null) {
                getUserProductsUseCase(userId, "underReview") // pending status
            } else {
                flowOf(PagingData.empty())
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            PagingData.empty()
        )

    val deliveredProducts = _currentUserId
        .flatMapLatest { userId ->
            if (userId != null) {
                getUserProductsUseCase(userId, "sold") // delivered/sold status
            } else {
                flowOf(PagingData.empty())
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            PagingData.empty()
        )

    init {
        getCurrentUser()
    }

    private fun getCurrentUser() {
        viewModelScope.launch {
            swapGoPref.getUser().collect { user ->
                _currentUserId.value = user?._id
            }
        }
    }

    fun onEvent(event: PostedProductsUiEvent) {
        when (event) {
            is PostedProductsUiEvent.IsLoading -> {
                 _state.update {
                    it.copy(isLoading = event.isLoading)
                 }
            }

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
                // Apply filter to listed products based on sub-status
//                applyListingFilter(event.index)
            }
            is PostedProductsUiEvent.OnPendingFilterSelected -> {
                _state.update {
                    it.copy(pendingFilterIndex = if (event.index == it.pendingFilterIndex) null else event.index)
                }
                // Apply filter to pending products
//                applyPendingFilter(event.index)
            }
            is PostedProductsUiEvent.OnDeliveredFilterSelected -> {
                _state.update {
                    it.copy(deliveredFilterIndex = if (event.index == it.deliveredFilterIndex) null else event.index)
                }
                // Apply filter to delivered products
//                applyDeliveredFilter(event.index)
            }
            is PostedProductsUiEvent.OnSearchButtonClicked -> {
                _state.update {
                    it.copy(isSearching = !it.isSearching)
                }
            }
        }
    }
}