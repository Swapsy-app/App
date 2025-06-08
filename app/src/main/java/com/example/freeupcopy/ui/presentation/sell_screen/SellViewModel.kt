package com.example.freeupcopy.ui.presentation.sell_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.remote.dto.sell.Cash
import com.example.freeupcopy.data.remote.dto.sell.Category
import com.example.freeupcopy.data.remote.dto.sell.Coin
import com.example.freeupcopy.data.remote.dto.sell.Mix
import com.example.freeupcopy.data.remote.dto.sell.Price
import com.example.freeupcopy.data.remote.dto.sell.ProductRequest
import com.example.freeupcopy.data.remote.dto.sell.Size
import com.example.freeupcopy.domain.enums.PricingModel
import com.example.freeupcopy.domain.model.CategoryUiModel
import com.example.freeupcopy.domain.model.SubCategory
import com.example.freeupcopy.domain.repository.SellRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SellViewModel @Inject constructor(
    private val sellRepository: SellRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SellUiState())
    val state = _state.asStateFlow()

    init {
        loadGst()
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
                var primary: CategoryUiModel? = null
                var secondary: SubCategory? = null
                // Iterate over all predefined categories.
                for (cat in CategoryUiModel.predefinedCategories) {
                    for (sub in cat.subcategories) {
                        if (sub.tertiaryCategories.any { it.name.equals(event.category, ignoreCase = true) }) {
                            primary = cat
                            secondary = sub
                            break
                        }
                    }
                    if (primary != null) break
                }
                _state.update {
                    it.copy(
                        primaryCategory = primary?.name,   // Expected "Women"
                        subCategory = secondary?.name,       // Expected e.g. "Ethnic"
                        tertiaryCategory = event.category,    // e.g. "Sarees"
                        size = null,
                        brand = null,
                        color = null,
                        fabric = null,
                        occasion = null,
                        shape = null,
                        modelNumber = null,
                        includes = null,
                        storageCapacity = null,
                        ram = null,
                        batteryCapacity = null,
                        mobileNetwork = null,
                        screenSize = null,
                        simType = null,
                        warranty = null,
                        length = null,
                        expirationDate = null,


                    )
                }
            }

            is SellUiEvent.BrandChange -> {
                _state.update { it.copy(brand = event.brand) }
            }

            is SellUiEvent.ColorChange -> {
                _state.update { it.copy(color = event.color) }
            }
            is SellUiEvent.FabricChange -> {
                _state.update { it.copy(fabric = event.fabric) }
            }

            is SellUiEvent.OccasionChange -> {
                _state.update { it.copy(occasion = event.occasion) }
            }

            is SellUiEvent.SizeChange -> {
                _state.update { it.copy(size = event.size) }
            }

            is SellUiEvent.SizeStringChange -> {
                _state.update { currentState ->
                    val updatedSize = Size(
                        attributes = null,        // Always reset
                        freeSize = false,        // Always reset
                        sizeString = event.sizeString
                    )
                    currentState.copy(size = updatedSize)
                }
            }

            is SellUiEvent.ChangeGst -> {
                _state.update { it.copy(gst = event.gst) }
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

            is SellUiEvent.AddUploadedImages -> {
                _state.update { currentState ->
                    val updatedImages = currentState.images.toMutableList()
                    event.images.forEach { imageUrl ->
                        if (imageUrl !in updatedImages && imageUrl !in currentState.deletedImages) {
                            updatedImages.add(imageUrl)
                        }
                    }
                    currentState.copy(images = updatedImages)
                }
            }

            is SellUiEvent.PopUpToSellScreen -> {
                _state.update { it.copy(popUpToSellScreen = event.popUp) }
            }

            is SellUiEvent.RemoveImage -> {
                _state.update { currentState ->
                    val updatedImages = currentState.images.toMutableList().apply {
                        remove(event.imageUrl)
                    }
                    val updatedDeleted = currentState.deletedImages.toMutableList().apply {
                        add(event.imageUrl)
                    }
                    currentState.copy(
                        images = updatedImages,
                        deletedImages = updatedDeleted
                    )
                }
            }

            is SellUiEvent.AddUploadedVideo -> {
                _state.update { currentState ->
                    // Only add the video if it hasn't been deleted.
                    if (event.videoUrl != currentState.deletedVideo) {
                        currentState.copy(video = event.videoUrl)
                    } else {
                        currentState
                    }
                }
            }
            is SellUiEvent.RemoveVideo -> {
                _state.update { currentState ->
                    // Save the current video in deletedVideo and set video to null.
                    currentState.copy(
                        video = null,
                        deletedVideo = currentState.video
                    )
                }
            }
            is SellUiEvent.AddProduct -> {
                viewModelScope.launch {
                    val currentState = _state.value
                    val productRequest = ProductRequest(
                        category = Category(
                            primaryCategory = currentState.primaryCategory ?: "",
                            secondaryCategory = currentState.subCategory,
                            tertiaryCategory = currentState.tertiaryCategory
                        ),
                        images = currentState.images,
                        video = currentState.video,
                        title = currentState.title,
                        description = currentState.description,
                        pickupAddress = currentState.address?._id,
                        condition = currentState.condition ?: "",
                        manufacturingCountry = currentState.manufacturingCountry ?: "",
                        weight = 0.0,
                        brand = currentState.brand,
                        occasion = currentState.occasion,
                        color = currentState.color,
                        shape = currentState.shape,
                        fabric = currentState.fabric,
                        quantity = 1,
                        shippingMethod = "Free",
                        size = currentState.size, // Handle size if needed
                        price = currentState.price?.let { p ->
                            Price(
                                mrp = p.mrp.toDoubleOrNull() ?: 0.0,
                                cash = if (p.pricingModel.contains(PricingModel.CASH)) {
                                    Cash(
                                        enteredAmount = p.sellingCash?.toDoubleOrNull(),
                                        sellerReceivesCash = p.earningCash?.toDoubleOrNull()
                                    )
                                } else null,
                                coin = if (p.pricingModel.contains(PricingModel.COINS)) {
                                    Coin(
                                        enteredAmount = p.sellingCoin?.toDoubleOrNull(),
                                        sellerReceivesCoin = p.earningCoin?.toDoubleOrNull()
                                    )
                                } else null,
                                mix = if (p.pricingModel.contains(PricingModel.CASH_AND_COINS)) {
                                    Mix(
                                        enteredCash = p.sellingCashCoin?.first?.toDoubleOrNull(),
                                        enteredCoin = p.sellingCashCoin?.second?.toDoubleOrNull(),
                                        sellerReceivesCash = p.earningCashCoin?.first?.toDoubleOrNull(),
                                        sellerReceivesCoin = p.earningCashCoin?.second?.toDoubleOrNull()
                                    )
                                } else null
                            )
                        } ?: Price(
                            mrp = 0.0,
                            cash = null,
                            coin = null,
                            mix = null
                        )
                    )

                    Log.e("SellViewModel", "AddProduct: $productRequest")
                    sellRepository.addProduct(productRequest).collect { resource ->
                        when (resource) {
                            is Resource.Loading -> {
                                _state.update { it.copy(isLoading = true, error = "") }
                            }
                            is Resource.Success -> {
                                _state.update { it.copy(isLoading = false, error = "successfully uploaded") }
                                // Handle successful product addition, e.g., navigate back or show a success message
                            }
                            is Resource.Error -> {
                                _state.update { it.copy(isLoading = false, error = resource.message ?: "An error occurred") }
                            }
                        }
                    }
                }

            }

            is SellUiEvent.ShapeChange -> {
                _state.update { it.copy(shape = event.shape) }
            }

            is SellUiEvent.LoadShapes -> {
                getShapesForCategory(event.category)
            }
        }
    }

    private fun getShapesForCategory(category: String) {
        viewModelScope.launch {
            sellRepository.getShapes(category).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true, error = "") }
                    }
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                shapes = resource.data?.shapes ?: emptyList()
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = resource.message ?: "Error fetching shapes"
                            )
                        }
                    }
                }
            }
        }
    }


    private fun loadGst() {
        viewModelScope.launch {
            sellRepository.getGst().collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true, error = "") }
                    }
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                gst = response.data?.gst ?: "",
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = response.message ?: "An error occurred"
                            )
                        }
                    }
                }

            }

        }
    }

    // Helper to convert File to MultipartBody.Part.
    private fun prepareFilePart(partName: String, file: File): MultipartBody.Part {
        val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }

    // Function to handle image uploads.
    private fun uploadImages(imageFiles: List<File>) {
        viewModelScope.launch {
            // Convert files into MultipartBody.Part list.
            val parts = imageFiles.map { file ->
                prepareFilePart("images", file)
            }
            sellRepository.uploadImages(parts).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = true,
                                error = "",
                                popUpToSellScreen = false
                            )
                        }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                images = resource.data?.images ?: emptyList(),
                                popUpToSellScreen = true
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = resource.message ?: "Error uploading images",
                                popUpToSellScreen = false
                            )
                        }
                    }
                }
            }
        }
    }
}