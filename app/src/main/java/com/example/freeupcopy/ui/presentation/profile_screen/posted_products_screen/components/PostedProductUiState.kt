package com.example.freeupcopy.ui.presentation.profile_screen.posted_products_screen.components

import com.example.freeupcopy.domain.enums.PricingModel

data class ListedState(
    val items: List<ListItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class PendingState(
    val items: List<ListItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class DeliveredState(
    val items: List<ListItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class ListItem(
    val imageUrl: String,
    val title: String,
    val pricingModels: List<PricingModel>,
    val cashPrice: String? = null,
    val coinPrice: String? = null,
    val combinedPrice: Pair<String, String>? = null,
    val favoriteCount: String,
    val shareCount: String,
    val offerCount: String,
    val productId: String,
    val viewCount: String,
    val isConfirmPending: Boolean,
    val isShippingGuide: Boolean
)