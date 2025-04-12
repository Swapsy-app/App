package com.example.freeupcopy.ui.presentation.sell_screen

import com.example.freeupcopy.data.remote.dto.sell.address.Address
import com.example.freeupcopy.domain.enums.SpecialOption
import com.example.freeupcopy.domain.model.Price
import com.example.freeupcopy.domain.model.Weight

data class SellUiState(
    val primaryCategory: String? = null,
    val subCategory: String? = null,
    val tertiaryCategory: String? = null,
    val specialOptions: List<SpecialOption> = emptyList(),

    val title: String = "",
    val description: String = "",
    val weight: Weight? = null,
    val condition: String? = null,
    val manufacturingCountry: String = "India",
    val price: Price? = null,
    val address: Address? = null,

    val images: List<String> = emptyList(),
    val video: String? = null,  // New field for the uploaded video URL.
    val deletedImages: List<String> = emptyList(),
    val deletedVideo: String? = null,  // New field for the deleted video URL.

    val popUpToSellScreen: Boolean = false,

    //Special Options
    val fabric: String? = null,
    val color: String? = null,
    val occasion: String? = null,
    val brand: String? = null,

    val modelNumber: String? = null,
    val includes: String? = null,
    val storageCapacity: String? = null,
    val ram: String? = null,
    val batteryCapacity: String? = null,
    val mobileNetwork: String? = null,
    val screenSize: String? = null,
    val simType: String? = null,
    val warranty: String? = null,

    val size: String? = null,
    val shape: String? = null,
    val length: String? = null,
    val expirationDate: String? = null,
    val gst: String = "",

    val isLoading: Boolean = false,
    val error: String = ""
)