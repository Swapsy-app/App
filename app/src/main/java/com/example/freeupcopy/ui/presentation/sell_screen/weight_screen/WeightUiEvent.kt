package com.example.freeupcopy.ui.presentation.sell_screen.weight_screen


//currently this class is not used
sealed class WeightUiEvent {
    data class WeightTypeSelected(val weightType: String) : WeightUiEvent()
}