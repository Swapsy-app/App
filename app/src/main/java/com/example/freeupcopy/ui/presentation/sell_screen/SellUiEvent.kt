package com.example.freeupcopy.ui.presentation.sell_screen

import com.example.freeupcopy.data.remote.dto.sell.Size
import com.example.freeupcopy.domain.model.Price
import com.example.freeupcopy.domain.model.Weight
import java.io.File

sealed class SellUiEvent {
    data class TitleChange(val title: String) : SellUiEvent()
    data class DescriptionChange(val description: String) : SellUiEvent()
    data class WeightChange(val weight: Weight) : SellUiEvent()
    data class ConditionChange(val condition: String) : SellUiEvent()
    data class ManufacturingCountryChange(val country: String) : SellUiEvent()
    data class PriceChange(val price: Price) : SellUiEvent()
    data class AddressChange(val address: com.example.freeupcopy.data.remote.dto.sell.address.Address?) : SellUiEvent()

    data class SizeChange(val size: Size) : SellUiEvent() // For SizeScreen
    data class SizeStringChange(val sizeString: String) : SellUiEvent()
    data class ColorChange(val color: String) : SellUiEvent()

    data class BrandChange(val brand: String) : SellUiEvent()
    data class FabricChange(val fabric: String) : SellUiEvent()
    data class OccasionChange(val occasion: String) : SellUiEvent()

    data class CategoryChanged(val category: String) : SellUiEvent()
    data class SpecialOptionsChanged(val category: String, val subCategory: String, val tertiary: String) : SellUiEvent()

    data class ChangeGst(val gst: String) : SellUiEvent()

    // New event for uploading images.
    data class AddUploadedImages(val images: List<String>) : SellUiEvent()
    data class RemoveImage(val imageUrl: String) : SellUiEvent()

    data class AddUploadedVideo(val videoUrl: String) : SellUiEvent() // New event
    object RemoveVideo : SellUiEvent() // New event

    data class PopUpToSellScreen(val popUp: Boolean) : SellUiEvent()

    object AddProduct : SellUiEvent()

    data class ShapeChange(val shape: String) : SellUiEvent()
    data class LoadShapes(val category: String) : SellUiEvent()
}