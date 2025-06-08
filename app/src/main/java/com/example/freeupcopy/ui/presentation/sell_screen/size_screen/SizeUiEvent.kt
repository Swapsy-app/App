package com.example.freeupcopy.ui.presentation.sell_screen.size_screen

sealed class SizeUiEvent {
    data class AttributeSelected(val attributeName: String, val value: String) : SizeUiEvent()
    data class ToggleAttributeExpansion(val attributeName: String) : SizeUiEvent()
    data class FreeSizeToggled(val isFreeSize: Boolean) : SizeUiEvent()
    data class SizeStringChanged(val sizeString: String) : SizeUiEvent()
    object ClearError : SizeUiEvent()
}