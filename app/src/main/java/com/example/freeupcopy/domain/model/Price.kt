package com.example.freeupcopy.domain.model

import com.example.freeupcopy.domain.enums.PricingModel
import kotlinx.serialization.Serializable

@Serializable
data class Price(
    val mrp: String,
    val pricingModel: List<PricingModel>,
    val sellingCoin: String? = null,
    val sellingCash: String? = null,
    val sellingCashCoin: Pair<String?, String?>? = null,
    val earningCash: String? = null,
    val earningCoin: String? = null,
    val earningCashCoin: Pair<String?, String?>? = null
)

fun Price.toUiModel(): PriceUiModel {
    return PriceUiModel(
        pricingModels = pricingModel,
        earningCoin = earningCoin,
        earningCash = earningCash,
        earningCashCoin = earningCashCoin
    )
}

