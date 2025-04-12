package com.example.freeupcopy.ui.presentation.product_listing

import com.example.freeupcopy.domain.enums.AvailabilityOption
import com.example.freeupcopy.domain.enums.ConditionOption
import com.example.freeupcopy.domain.enums.Filter
import com.example.freeupcopy.domain.enums.FilterCategoryUiModel
import com.example.freeupcopy.domain.enums.FilterSpecialOption
import com.example.freeupcopy.domain.enums.FilterTertiaryCategory
import com.example.freeupcopy.domain.enums.NewPricingModel
import com.example.freeupcopy.domain.enums.SellerBadge
import com.example.freeupcopy.domain.enums.SellerRatingOption

sealed class ProductListingUiEvent {
    data class ChangeSearchQuery(val query: String): ProductListingUiEvent()
    data class ToggleBottomSheet(val type: String): ProductListingUiEvent()
    data object BottomSheetDismiss: ProductListingUiEvent()
    data object ClearFilters: ProductListingUiEvent()
    data class ChangeSelectedFilter(val filter: Filter): ProductListingUiEvent()
    data class ChangeSelectedAvailability(val availabilityOption: AvailabilityOption): ProductListingUiEvent()
    data class ChangeSelectedCondition(val conditionOption: ConditionOption): ProductListingUiEvent()
    data class ChangeSelectedSellerRating(val sellerRatingOption: SellerRatingOption): ProductListingUiEvent()
    data class ChangeSelectedSellerBadge(val sellerBadge: SellerBadge): ProductListingUiEvent()
    data class ChangeSelectedPricingModel(val pricingModel: NewPricingModel): ProductListingUiEvent()
    data class ChangeCashRange(val cash: Float): ProductListingUiEvent()
    data class ChangeCoinRange(val coins: Float): ProductListingUiEvent()
    data class ChangeSelectedTertiaryCategory(val tertiaryCategory: FilterTertiaryCategory): ProductListingUiEvent()
    data class RemoveSpecialOptions(val tertiaryCategory: FilterTertiaryCategory) : ProductListingUiEvent()
    data class ToggleSelectAll(val selectedFilter: FilterCategoryUiModel) : ProductListingUiEvent()

    data class ChangeSortOption(val sortOption: String): ProductListingUiEvent()
    data object ApplySortOption: ProductListingUiEvent()

    data class SpecialOptionSelectedChange(val filterSpecialOption: FilterSpecialOption) : ProductListingUiEvent()
}