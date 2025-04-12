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

    val searchQuery: String = "",
    val selectedFilter: Filter = Filter.AVAILABILITY,
    val isFilterBottomSheet: Boolean = false,
    val isSortBottomSheet: Boolean = false,
    val isBottomSheetOpen: Boolean = false,

    // New fields for the sort functionality:
    val appliedSortOption: String? = null, // Used for the query.
    val tempSortOption: String? = null,    // Used for UI selection in the sort bottom sheet.
    val selectedSortOption: String? = null,

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

    val selectedSpecialOptions: List<FilterSpecialOption> = emptyList(),

    val isLoading: Boolean = false,
    val error: String = ""
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