package com.example.freeupcopy.ui.presentation.product_listing

import com.example.freeupcopy.domain.enums.AvailabilityOption
import com.example.freeupcopy.domain.enums.ConditionOption
import com.example.freeupcopy.domain.enums.Filter
import com.example.freeupcopy.domain.enums.FilterSpecialOption
import com.example.freeupcopy.domain.enums.FilterTertiaryCategory
import com.example.freeupcopy.domain.enums.NewPricingModel
import com.example.freeupcopy.domain.enums.SellerBadge
import com.example.freeupcopy.domain.enums.SellerRatingOption

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

    // my state
    val selectedFilter: Filter = Filter.AVAILABILITY,
    val isFilterBottomSheet: Boolean = false,
    val isSortBottomSheet: Boolean = false,

    val availabilityOptions: List<AvailabilityOption> = emptyList(),
    val conditionOptions: List<ConditionOption> = emptyList(),
    val sellerRatingOptions: List<SellerRatingOption> = emptyList(),
    val sellerBadgeOptions: List<SellerBadge> = emptyList(),
    val pricingModelOptions: List<NewPricingModel> = emptyList(),
    val selectedCashRange: Float? = null,
    val selectedCoinRange: Float? = null,
    val isCombinedPriceSelected: Boolean = false,
    val selectedTertiaryCategory: List<FilterTertiaryCategory> = emptyList(),

    val availableFilters: List<Filter> = listOf(Filter.AVAILABILITY, Filter.CONDITION, Filter.SELLER_RATING, Filter.PRICE, Filter.CATEGORY, Filter.BRAND),

    val selectedSpecialOptions: List<FilterSpecialOption> = emptyList()
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