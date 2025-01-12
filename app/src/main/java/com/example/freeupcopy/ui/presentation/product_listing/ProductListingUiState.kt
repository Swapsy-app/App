package com.example.freeupcopy.ui.presentation.product_listing

import com.example.freeupcopy.ui.presentation.sell_screen.weight_screen.WeightUiEvent

data class ProductListingUiState(

    val isBottomSheetOpen : Boolean = false,

    val isFilterSelected: Boolean = false,
    val isSortSelected : Boolean = false,
    val isSortApplied : Boolean = false,

    val sortingOption: SortOption = SortOption.Recommended,

    val isAvailablitySelected : Boolean = false,
    val isAvailableSelected : Boolean = false,
    val isSoldOutSelected : Boolean = false,

    val isConditionSelected : Boolean = false,
    val isNewWithTagsSelected : Boolean = false,
    val isLikeNewSelected : Boolean = false,
    val isGoodSelected : Boolean = false,
    val isUsedSelected : Boolean = false,

    val isSellerRatingSelected : Boolean = false,
    val isRating4_0Selected : Boolean = false,
    val isRating4_5Selected : Boolean = false,
    val isRating4_7Selected : Boolean = false,

    val isPriceSelected : Boolean = false,
    val cashSelected : Float = 50000f,
    val isCashSelected : Boolean = true,
    val coinsSelected : Float = 50000f,
    val isCoinSelected : Boolean = true,
    val isOfferSelected : Boolean = true,

    val isSellerActiveSelected : Boolean = false,
    val isSellerActiveThisWeekSelected : Boolean = false,
    val isSellerActiveThisMonthSelected : Boolean = false,

    val isCategorySelected : Boolean = false,

    val isSizeSelected : Boolean = false,

    val filterSectionOpen : FilterClassOptions? = null,

    val isNewArrivalSelected : Boolean = false,
    val isFreeInCoinSelected : Boolean = false,
    val isSwapGoAssuredSelected : Boolean = false,

)

enum class FilterClassOptions{
    Availiblity,
    Condition,
    SellerRating,
    Price,
    SellerActive,
    Category,
    Size
}

enum class SortOption{
    PriceLowToHigh,
    PriceHighToLow,
    Recommended
}