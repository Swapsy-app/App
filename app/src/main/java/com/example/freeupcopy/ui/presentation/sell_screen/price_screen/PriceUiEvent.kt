package com.example.freeupcopy.ui.presentation.sell_screen.price_screen

import com.example.freeupcopy.domain.enums.PricingModel

sealed class PriceUiEvent {
    data class MrpChange(val mrp: String) : PriceUiEvent()
    data class SellingCoinChange(val sellingCoin: String) : PriceUiEvent()
    data class SellingCashChange(val sellingCash: String) : PriceUiEvent()
    data class SellingccCashChange(val cash: String) : PriceUiEvent()
    data class SellingccCoinChange(val coin: String) : PriceUiEvent()
    data class EarningCashChange(val cash: String) : PriceUiEvent()
    data class EarningCoinChange(val coin: String) : PriceUiEvent()
    data class EarningccCashChange(val cash: String) : PriceUiEvent()
    data class EarningccCoinChange(val coin: String) : PriceUiEvent()

    data class SellingCoinCashChange(val coin: String, val cash: String) : PriceUiEvent()
    data class EarningCoinCashChange(val coin: String, val cash: String) : PriceUiEvent()
    data class TogglePricingModel(val pricingModel: PricingModel) : PriceUiEvent()
}