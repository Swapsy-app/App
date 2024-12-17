package com.example.freeupcopy.ui.presentation.sell_screen

import com.example.freeupcopy.domain.model.Price
import com.example.freeupcopy.domain.model.Weight

data class SellUiState(
    val weight: Weight? = null,
    val condition: String? = null,
    val manufacturingCountry: String? = null,
    //val priceUiModel: PriceUiModel? = null
    val price: Price? = null
)