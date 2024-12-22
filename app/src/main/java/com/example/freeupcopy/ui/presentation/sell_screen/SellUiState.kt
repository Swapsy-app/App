package com.example.freeupcopy.ui.presentation.sell_screen

import com.example.freeupcopy.data.local.Address
import com.example.freeupcopy.domain.model.Price
import com.example.freeupcopy.domain.model.Weight

data class SellUiState(
    val title: String = "",
    val description: String = "",

    val weight: Weight? = null,
    val condition: String? = null,
    val manufacturingCountry: String? = null,
    //val priceUiModel: PriceUiModel? = null
    val price: Price? = null,
    val address: Address? = null,
//    val defaultAddressId: Int? = null,

    val isLoading: Boolean = false,
    val error: String = ""
)