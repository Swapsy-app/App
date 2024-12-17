package com.example.freeupcopy.ui.presentation.sell_screen

import com.example.freeupcopy.domain.model.Price
import com.example.freeupcopy.domain.model.Weight

sealed class SellUiEvent {
    data class WeightChange(val weight: Weight) : SellUiEvent()
    data class ConditionChange(val condition: String) : SellUiEvent()
    data class ManufacturingCountryChange(val country: String) : SellUiEvent()
    data class PriceChange(val price: Price) : SellUiEvent()

}