package com.example.freeupcopy.ui.presentation.offer_screen

import com.example.freeupcopy.domain.enums.OfferTabOption

data class OffersUiState(
    val selectedTab: OfferTabOption = OfferTabOption.SENT,
    val selectedStatus: String? = null,
    val isLoading: Boolean = false,
    val error: String = "",
    val refreshTrigger: Long = 0L // Add this for explicit refresh
)
