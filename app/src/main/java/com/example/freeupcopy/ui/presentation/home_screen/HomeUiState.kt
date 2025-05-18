package com.example.freeupcopy.ui.presentation.home_screen

import com.example.freeupcopy.data.remote.dto.product.User
import com.example.freeupcopy.data.remote.dto.sell.ProductCard
import com.example.freeupcopy.domain.enums.AvailabilityOption
import com.example.freeupcopy.domain.enums.ConditionOption
import com.example.freeupcopy.domain.enums.Filter
import com.example.freeupcopy.domain.enums.NewPricingModel
import com.example.freeupcopy.domain.enums.SellerBadge
import com.example.freeupcopy.domain.enums.SellerRatingOption

data class HomeUiState(
    val bestInMenProducts: List<ProductCard> = emptyList(),
    val isBestInMenLoading: Boolean = false,
    val bestInMenError: String = "",

    val bestInWomenWear: List<ProductCard> = emptyList(),
    val isBestInWomenLoading: Boolean = false,
    val bestInWomenError: String = "",

    val ethnicWomenProducts: List<ProductCard> = emptyList(),
    val isEthnicWomenLoading: Boolean = false,
    val ethnicWomenError: String = "",

    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String = "",

    // New fields for Explore Products
    // Add a set to track which filters have active selections
    val activeFilters: Set<Filter> = emptySet(),

    val exploreProducts: List<ProductCard> = emptyList(),
    val isExploreProductsLoading: Boolean = false,
    val exploreProductsError: String = "",

    // Filter state
    val isFilterBottomSheetOpen: Boolean = false,
    val filterBottomSheetType: String? = null,
    val selectedFilter: Filter = Filter.CATEGORY,

    val availabilityOptions: List<AvailabilityOption> = emptyList(),
    val conditionOptions: List<ConditionOption> = emptyList(),
    val sellerRatingOptions: List<SellerRatingOption> = emptyList(),
    val sellerBadgeOptions: List<SellerBadge> = emptyList(),
    val pricingModelOptions: List<NewPricingModel> = emptyList(),
    val selectedCashRange: Float? = null,
    val selectedCoinRange: Float? = null,

    // Temporary filter options (for pending changes)
    val tempAvailabilityOptions: List<AvailabilityOption> = emptyList(),
    val tempConditionOptions: List<ConditionOption> = emptyList(),
    val tempSellerRatingOptions: List<SellerRatingOption> = emptyList(),
    val tempSellerBadgeOptions: List<SellerBadge> = emptyList(),
    val tempPricingModelOptions: List<NewPricingModel> = emptyList(),
    val tempSelectedCashRange: Float? = null,
    val tempSelectedCoinRange: Float? = null,

    // Sort state
    val tempSortOption: String? = null,
    val appliedSortOption: String? = null,
)