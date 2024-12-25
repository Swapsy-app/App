package com.example.freeupcopy.ui.presentation.sell_screen.price_screen

import com.example.freeupcopy.domain.enums.PricingModel

data class PriceUiState(
    val pricingModel: List<PricingModel> = emptyList(),
    val mrp: String = "",
    val sellingCoin: String? = null,
    val sellingCash: String? = null,
    val combinedCash: String? = null,
    val combinedCoin: String? = null,
    val sellingCoinCash: Pair<String?, String?>? = null,
    val earningCash: String? = null,
    val earningCoin: String? = null,
    val earningccCash: String? = null,
    val earningccCoin: String? = null,
    val earningCoinCash: Pair<String?, String?>? = null,

    val error: String = "",
    val isLoading: Boolean = false
)