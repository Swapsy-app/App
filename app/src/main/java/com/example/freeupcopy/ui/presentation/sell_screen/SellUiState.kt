package com.example.freeupcopy.ui.presentation.sell_screen

import com.example.freeupcopy.data.remote.dto.sell.ShapeItem
import com.example.freeupcopy.data.remote.dto.sell.Size
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

    // With this:
    val size: Size? = null, // For structured size data from SizeScreen
//    val optionalSize: String? = null, // For simple string size input

    val shape: String? = null,
    val length: String? = null,
    val expirationDate: String? = null,
    val gst: String = "",

    val isLoading: Boolean = false,
    val error: String = "",

    val shapes: List<ShapeItem> = emptyList(),
){
    // Updated computed property for size display
    val sizeDisplayValue: String
        get() = when {
            size != null -> {
                when {
//                    size.freeSize -> "Free Size"
                    size.sizeString?.isNotEmpty() == true -> size.sizeString
                    size.attributes?.isNotEmpty() == true -> {
                        size.attributes.joinToString(" | ") { attr ->
                            // Extract only the part before "/" using substringBefore
                            val shortValue = attr.value.substringBefore("/").trim()
                            "${attr.name} $shortValue"
                        }
                    }
                    else -> ""
                }
            }
            else -> ""
        }
}