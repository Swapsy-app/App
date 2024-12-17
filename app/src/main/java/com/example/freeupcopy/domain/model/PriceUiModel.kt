package com.example.freeupcopy.domain.model

import com.example.freeupcopy.domain.enums.PricingModel

data class PriceUiModel(
    //val pricingModels: List<String>,
    val pricingModels: List<PricingModel> = emptyList(),
    val earningCoin: String? = null,
    val earningCash: String? = null,
    val earningCashCoin: Pair<String?, String?>? = null
)