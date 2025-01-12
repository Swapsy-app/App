package com.example.freeupcopy.ui.presentation.profile_screen.posted_products_screen

sealed class PostedProductsUiEvent {
    data class OnTabSelected(val index: Int) : PostedProductsUiEvent()
    data class OnListingFilterSelected(val index: Int) : PostedProductsUiEvent()
    data class OnPendingFilterSelected(val index: Int) : PostedProductsUiEvent()
    data class OnDeliveredFilterSelected(val index: Int) : PostedProductsUiEvent()
    data object OnSearchButtonClicked : PostedProductsUiEvent()
}
