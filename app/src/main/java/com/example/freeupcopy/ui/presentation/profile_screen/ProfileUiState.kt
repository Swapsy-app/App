package com.example.freeupcopy.ui.presentation.profile_screen

import com.example.freeupcopy.data.remote.dto.product.User
import com.example.freeupcopy.domain.enums.PricingModel
import com.example.freeupcopy.domain.model.ProfileProductListing

data class ProfileUiState(
    val user: User? = null,
    val userRating: String = "4.52",
    val cashBalance: String = "120",
    val coinBalance: String = "2000",
    val isPackingMaterialOn: Boolean = true,
    val isBundleOffersOn: Boolean = false,
    val isOnlineModeOn: Boolean = true,

    val listedCount: String = "5",
    val pendingCount: String = "2",
    val deliveredCount: String = "3",
    val isListedActionRequired: Boolean = true,
    val isPendingActionRequired: Boolean = false,
    val isDeliveredActionRequired: Boolean = false,

    val error: String = "",
    val isLoading: Boolean = false,
)
