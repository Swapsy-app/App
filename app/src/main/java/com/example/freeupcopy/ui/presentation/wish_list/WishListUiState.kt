package com.example.freeupcopy.ui.presentation.wish_list

import com.example.freeupcopy.domain.enums.AvailabilityOption
import com.example.freeupcopy.domain.enums.ConditionOption
import com.example.freeupcopy.domain.enums.Filter
import com.example.freeupcopy.domain.enums.FilterTertiaryCategory
import com.example.freeupcopy.domain.enums.NewPricingModel

data class WishListUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val successMessage: String = "",

    // Filter state
    val activeFilters: Set<Filter> = emptySet(),
    val selectedFilter: Filter = Filter.SORT,
    val appliedSortOption: String? = "relevance",
    val conditionOptions: List<ConditionOption> = emptyList(),
    val availabilityOptions: List<AvailabilityOption> = emptyList(),
    val pricingModelOptions: List<NewPricingModel> = emptyList(),
    val selectedCashRange: Float? = null,
    val selectedCoinRange: Float? = null,
    val selectedTertiaryCategories: List<FilterTertiaryCategory> = emptyList(),

    // Bottom sheet state
    val isFilterBottomSheetOpen: Boolean = false,
    val filterBottomSheetType: String? = null,

    // Temporary values for bottom sheet
    val tempConditionOptions: List<ConditionOption> = emptyList(),
    val tempAvailabilityOptions: List<AvailabilityOption> = emptyList(),
    val tempPricingModelOptions: List<NewPricingModel> = emptyList(),
    val tempSelectedCashRange: Float? = null,
    val tempSelectedCoinRange: Float? = null,
    val tempSortOption: String? = "relevance",
    val tempSelectedTertiaryCategories: List<FilterTertiaryCategory> = emptyList()
)
