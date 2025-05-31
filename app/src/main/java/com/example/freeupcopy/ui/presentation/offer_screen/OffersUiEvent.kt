package com.example.freeupcopy.ui.presentation.offer_screen

import com.example.freeupcopy.domain.enums.OfferTabOption

sealed class OffersUiEvent {
    data class ChangeTab(val tab: OfferTabOption) : OffersUiEvent()
    data class ChangeStatus(val status: String?) : OffersUiEvent()
    data class AcceptOffer(val bargainId: String) : OffersUiEvent()
    object RefreshOffers : OffersUiEvent()
    object ClearError : OffersUiEvent()
}