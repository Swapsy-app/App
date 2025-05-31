package com.example.freeupcopy.ui.presentation.offer_screen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.remote.dto.product.BuyerBargain
import com.example.freeupcopy.data.remote.dto.product.SellerBargain
import com.example.freeupcopy.domain.enums.OfferStatusOption
import com.example.freeupcopy.domain.enums.OfferTabOption
import com.example.freeupcopy.domain.repository.ProductRepository
import com.example.freeupcopy.domain.use_case.GetBuyerOffersUseCase
import com.example.freeupcopy.domain.use_case.GetSellerOffersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfferViewModel @Inject constructor(
    private val getBuyerOffersUseCase: GetBuyerOffersUseCase,
    private val getSellerOffersUseCase: GetSellerOffersUseCase,
    savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository
): ViewModel() {

    private val userId: String? = savedStateHandle["userId"]

    private val _state = MutableStateFlow(OffersUiState())
    val state = _state.asStateFlow()

    // Update the flows to also react to refreshTrigger
    @OptIn(ExperimentalCoroutinesApi::class)
    val buyerOffers: StateFlow<PagingData<BuyerBargain>> = _state
        .map { "${it.selectedStatus}_${it.refreshTrigger}" } // Combine status and trigger
        .distinctUntilChanged()
        .flatMapLatest { _ ->
            if (userId != null) {
                getBuyerOffersUseCase(userId, _state.value.selectedStatus)
            } else {
                flowOf(PagingData.empty())
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            PagingData.empty()
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val sellerOffers: StateFlow<PagingData<SellerBargain>> = _state
        .map { "${it.selectedStatus}_${it.refreshTrigger}" } // Combine status and trigger
        .distinctUntilChanged()
        .flatMapLatest { _ ->
            if (userId != null) {
                getSellerOffersUseCase(userId, _state.value.selectedStatus)
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
        if (userId != null) {
            Log.e("OfferViewModel", "userId: $userId")
            _state.update {
                it.copy(
                    selectedTab = OfferTabOption.SENT,
                    selectedStatus = null
                )
            }
        } else {
            _state.update {
                it.copy(error = "User ID not found")
            }
        }
    }

    fun onEvent(event: OffersUiEvent) {
        when (event) {
            is OffersUiEvent.ChangeTab -> {
                _state.update {
                    it.copy(
                        selectedTab = event.tab,
                        selectedStatus = null // Reset status when changing tabs
                    )
                }
            }

            is OffersUiEvent.ChangeStatus -> {
                val currentStatus = _state.value.selectedStatus
                val newStatus = if (currentStatus == event.status) {
                    null
                } else {
                    event.status
                }

                _state.update {
                    it.copy(selectedStatus = newStatus)
                }
            }

            is OffersUiEvent.AcceptOffer -> {
                acceptOffer(event.bargainId)
            }

            is OffersUiEvent.RefreshOffers -> {
                _state.update {
                    it.copy(refreshTrigger = System.currentTimeMillis())
                }
            }

            is OffersUiEvent.ClearError -> {
                _state.update {
                    it.copy(error = "")
                }
            }
        }
    }

    fun updateLoadingState(isLoading: Boolean) {
        _state.update {
            it.copy(isLoading = isLoading)
        }
    }

    // Get available status options based on selected tab
    fun getAvailableStatusOptions(): List<OfferStatusOption> {
        return when (_state.value.selectedTab) {
            OfferTabOption.SENT -> listOf(
                OfferStatusOption.PENDING,
                OfferStatusOption.ACCEPTED
            )
            OfferTabOption.RECEIVED -> listOf(
                OfferStatusOption.PENDING,
                OfferStatusOption.ACCEPTED
            )
        }
    }

    private fun acceptOffer(bargainId: String) {
        viewModelScope.launch {
            productRepository.acceptOffer(bargainId).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true, error = "") }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = "",
                                refreshTrigger = System.currentTimeMillis() // Trigger refresh
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = result.message ?: "Failed to accept offer"
                            )
                        }
                    }
                }
            }
        }
    }
}
