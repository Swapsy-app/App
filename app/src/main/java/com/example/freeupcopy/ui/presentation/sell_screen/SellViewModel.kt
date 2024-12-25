package com.example.freeupcopy.ui.presentation.sell_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.domain.enums.SpecialOption
import com.example.freeupcopy.domain.model.CategoryUiModel
import com.example.freeupcopy.domain.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellViewModel @Inject constructor(
    private val repository: LocationRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SellUiState())
    val state = _state.asStateFlow()

    init {
        loadDefaultAddress()
    }

    fun onEvent(event: SellUiEvent) {
        when (event) {
            is SellUiEvent.TitleChange -> {
                _state.update { it.copy(title = event.title) }
            }

            is SellUiEvent.DescriptionChange -> {
                _state.update { it.copy(description = event.description) }
            }

            is SellUiEvent.WeightChange -> {
                _state.update { it.copy(weight = event.weight) }
            }

            is SellUiEvent.ConditionChange -> {
                _state.update { it.copy(condition = event.condition) }
            }

            is SellUiEvent.ManufacturingCountryChange -> {
                _state.update { it.copy(manufacturingCountry = event.country) }
            }

            is SellUiEvent.PriceChange -> {
                _state.update { it.copy(price = event.price) }
            }

            is SellUiEvent.AddressChange -> {
                _state.update { it.copy(address = event.address) }
            }

            is SellUiEvent.CategoryChanged -> {
                _state.update { it.copy(leafCategory = event.category) }
            }

            is SellUiEvent.BrandChange -> {
                _state.update { it.copy(brand = event.brand) }
            }

            is SellUiEvent.SizeChange -> {
                _state.update { it.copy(size = event.size) }
            }

            is SellUiEvent.SpecialOptionsChanged -> {
                val category = CategoryUiModel.predefinedCategories
                    .find { it.name == event.category }

                val subCategory = category?.subcategories
                    ?.find { it.name == event.subCategory }

                val tertiaryCategory = subCategory?.tertiaryCategories
                    ?.find { it.name == event.tertiary }

                val specialOptions = buildSet {
                    subCategory?.specialSubCatOption?.let { addAll(it) }
                    tertiaryCategory?.specialOption?.let { addAll(it) }
                }

                _state.update { it.copy(specialOptions = specialOptions.toList()) }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun loadDefaultAddress() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = "") }
            repository.getDefaultAddressId()
                .take(1)
                .flatMapLatest { defaultAddressId ->
                    defaultAddressId?.let {
                        repository.getAddressById(it)
                    } ?: flow { emit(Resource.Success(null)) }
                }
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _state.update {
                                it.copy(address = result.data, isLoading = false)
                            }
                        }
                        is Resource.Error -> {
                            _state.update {
                                it.copy(isLoading = false, error = result.message ?: "An error occurred")
                            }
                        }
                        is Resource.Loading -> {
                            _state.update {
                                it.copy(isLoading = true)
                            }
                        }
                    }
                }
        }
    }
}