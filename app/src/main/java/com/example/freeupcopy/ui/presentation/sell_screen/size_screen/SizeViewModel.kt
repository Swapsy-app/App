package com.example.freeupcopy.ui.presentation.sell_screen.size_screen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.example.freeupcopy.data.remote.dto.sell.Size
import com.example.freeupcopy.ui.navigation.CustomNavType
import com.example.freeupcopy.ui.navigation.Screen
import com.example.freeupcopy.utils.ValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.reflect.typeOf

@HiltViewModel
class SizeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _state = MutableStateFlow(SizeUiState())
    val state = _state.asStateFlow()

    // Use toRoute with typeMap to properly deserialize the Size object
    private val sizeScreen: Screen.SizeScreen = savedStateHandle.toRoute(
        typeMap = mapOf(
            typeOf<Size?>() to CustomNavType.SizeType
        )
    )

    private val size: Size? = sizeScreen.size

    init {
        if(size != null) {
            Log.e("sizeViewModel", "Size received: $size")
            _state.update {
                it.copy(
                    selectedSize = size,
                    selectedAttributes = size.attributes?.associate { attr ->
                        attr.name to attr.value
                    } ?: emptyMap(),
                    isFreeSize = size.freeSize,
                    sizeString = size.sizeString ?: ""
                )
            }
        }
    }

    // Add this computed property for display value
    fun getDisplayValue(): String {
        val currentState = _state.value
        return when {
            currentState.selectedAttributes.isNotEmpty() -> {
                // Format attributes as "Bust: 32in, Waist: 30in, Hip: 34in"
                currentState.selectedAttributes.entries.joinToString(", ") { (name, value) ->
                    "$name: $value"
                }
            }
            currentState.isFreeSize -> "Free Size"
            currentState.sizeString.isNotEmpty() -> currentState.sizeString
            currentState.selectedSize != null -> {
                // Format existing size for display
                when {
                    currentState.selectedSize.freeSize -> "Free Size"
                    currentState.selectedSize.sizeString?.isNotEmpty() == true -> currentState.selectedSize.sizeString
                    currentState.selectedSize.attributes?.isNotEmpty() == true -> {
                        currentState.selectedSize.attributes.joinToString(", ") { attr ->
                            "${attr.name}: ${attr.value}"
                        }
                    }
                    else -> ""
                }
            }
            else -> ""
        }
    }

    fun onEvent(event: SizeUiEvent) {
        when (event) {
            is SizeUiEvent.AttributeSelected -> {
                _state.update {
                    it.copy(
                        selectedAttributes = it.selectedAttributes + (event.attributeName to event.value),
                        expandedAttribute = null
                    )
                }
            }
            is SizeUiEvent.ToggleAttributeExpansion -> {
                _state.update {
                    it.copy(
                        expandedAttribute = if (it.expandedAttribute == event.attributeName) null else event.attributeName
                    )
                }
            }
            is SizeUiEvent.FreeSizeToggled -> {
                _state.update {
                    it.copy(isFreeSize = event.isFreeSize)
                }
            }
            is SizeUiEvent.SizeStringChanged -> {
                _state.update {
                    it.copy(sizeString = event.sizeString)
                }
            }
            is SizeUiEvent.ClearError -> {
                _state.update {
                    it.copy(error = "")
                }
            }
        }
    }

    fun validateSize(): ValidationResult {
        val currentState = _state.value
        return when {
            currentState.selectedAttributes.isNotEmpty() && currentState.selectedAttributes.values.any { it.isNotEmpty() } ->
                ValidationResult(true, null)
            currentState.isFreeSize ->
                ValidationResult(true, null)
            currentState.sizeString.isNotEmpty() ->
                ValidationResult(true, null)
            else ->
                ValidationResult(false, "Please select a size option")
        }
    }
}
