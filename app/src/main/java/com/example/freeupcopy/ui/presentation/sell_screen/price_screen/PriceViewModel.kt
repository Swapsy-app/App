package com.example.freeupcopy.ui.presentation.sell_screen.price_screen

import androidx.lifecycle.ViewModel
import com.example.freeupcopy.domain.model.Price
import com.example.freeupcopy.domain.enums.PricingModel
import com.example.freeupcopy.utils.ValidationResult
import com.example.freeupcopy.utils.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PriceViewModel @Inject constructor(

) : ViewModel() {

    private val _priceUiState = MutableStateFlow(PriceUiState())
    val priceUiState: StateFlow<PriceUiState> = _priceUiState

    fun getPrice (price: Price) {
        _priceUiState.value = priceUiState.value.copy(
            pricingModel = price.pricingModel,
            mrp = price.mrp,
            sellingCoin = price.sellingCoin,
            sellingCash = price.sellingCash,
            combinedCash = price.sellingCashCoin?.first,
            combinedCoin = price.sellingCashCoin?.second,
            sellingCoinCash = price.sellingCashCoin,
            earningCash = price.earningCash,
            earningCoin = price.earningCoin,
            earningccCash = price.earningCashCoin?.first,
            earningccCoin = price.earningCashCoin?.second,
            earningCoinCash = price.earningCashCoin
        )
    }

    fun onEvent(event: PriceUiEvent) {
        when (event) {
            is PriceUiEvent.MrpChange -> {
                _priceUiState.value = priceUiState.value.copy(mrp = event.mrp)
            }
            is PriceUiEvent.SellingCoinChange -> {
                _priceUiState.value = priceUiState.value.copy(sellingCoin = event.sellingCoin)
            }
            is PriceUiEvent.SellingCashChange -> {
                _priceUiState.value = priceUiState.value.copy(sellingCash = event.sellingCash)
            }
            is PriceUiEvent.SellingccCashChange -> {
                _priceUiState.value = priceUiState.value.copy(combinedCash = event.cash)
            }
            is PriceUiEvent.SellingccCoinChange -> {
                _priceUiState.value = priceUiState.value.copy(combinedCoin = event.coin)
            }
            is PriceUiEvent.EarningCashChange -> {
                _priceUiState.value = priceUiState.value.copy(earningCash = event.cash)
            }
            is PriceUiEvent.EarningCoinChange -> {
                _priceUiState.value = priceUiState.value.copy(earningCoin = event.coin)
            }

            is PriceUiEvent.SellingCoinCashChange -> {
                _priceUiState.value = priceUiState.value.copy(
                    sellingCoinCash = Pair(event.coin, event.cash)
                )
            }
            is PriceUiEvent.EarningCoinCashChange -> {
                _priceUiState.value = priceUiState.value.copy(
                    earningCoinCash = Pair(event.coin, event.cash)
                )
            }

            is PriceUiEvent.EarningccCashChange -> {
                _priceUiState.value = priceUiState.value.copy(earningccCash = event.cash)
            }
            is PriceUiEvent.EarningccCoinChange -> {
                _priceUiState.value = priceUiState.value.copy(earningccCoin = event.coin)
            }

            is PriceUiEvent.TogglePricingModel -> {
                val updatedList = _priceUiState.value.pricingModel.toMutableList()
                if (updatedList.contains(event.pricingModel)) {
                    // Reset respective states when removing a pricing model
                    updatedList.remove(event.pricingModel)
                    when (event.pricingModel) {
                        PricingModel.CASH -> {
                            _priceUiState.value = _priceUiState.value.copy(
                                sellingCash = "",
                                earningCash = ""
                            )
                        }
                        PricingModel.COINS -> {
                            _priceUiState.value = _priceUiState.value.copy(
                                sellingCoin = "",
                                earningCoin = ""
                            )
                        }
                        PricingModel.CASH_AND_COINS -> {
                            _priceUiState.value = _priceUiState.value.copy(
                                combinedCash = "",
                                combinedCoin = "",
                                earningccCash = "",
                                earningccCoin = "",
//                                sellingCoinCash = null,
//                                earningCoinCash = null
                            )
                        }
                    }
                } else {
                    updatedList.add(event.pricingModel)
                }
                _priceUiState.value = _priceUiState.value.copy(pricingModel = updatedList)
            }
        }
    }
    fun validateAll(): ValidationResult {
        // Validate MRP
        val mrpResult = Validator.validateMrp(priceUiState.value.mrp)
        if (!mrpResult.isValid) return mrpResult

        // Validate Selling Cash
        if (priceUiState.value.pricingModel.contains(PricingModel.CASH)) {
            val sellingCashResult = Validator.validateCashAmount(priceUiState.value.sellingCash)
            if (!sellingCashResult.isValid) return sellingCashResult
        }

        // Validate Selling Coins
        if (priceUiState.value.pricingModel.contains(PricingModel.COINS)) {
            val sellingCoinResult = Validator.validateCoinAmount(priceUiState.value.sellingCoin)
            if (!sellingCoinResult.isValid) return sellingCoinResult
        }

        // Validate Combined Cash and Coins for "CASH_AND_COINS"
        if (priceUiState.value.pricingModel.contains(PricingModel.CASH_AND_COINS)) {
            val combinedCashResult = Validator.validateCashAmount(priceUiState.value.combinedCash)
            if (!combinedCashResult.isValid) return combinedCashResult

            val combinedCoinResult = Validator.validateCoinAmount(priceUiState.value.combinedCoin)
            if (!combinedCoinResult.isValid) return combinedCoinResult
        }
        // if no pricing model is selected
        if (priceUiState.value.pricingModel.isEmpty()) {
            return ValidationResult(false, "Select at least one pricing model")
        }

        return ValidationResult(true)
    }
}