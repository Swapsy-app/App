package com.example.freeupcopy.ui.presentation.wish_list

import com.example.freeupcopy.domain.enums.AvailabilityOption
import com.example.freeupcopy.domain.enums.ConditionOption
import com.example.freeupcopy.domain.enums.Filter
import com.example.freeupcopy.domain.enums.FilterCategoryUiModel
import com.example.freeupcopy.domain.enums.FilterTertiaryCategory
import com.example.freeupcopy.domain.enums.NewPricingModel

sealed class WishlistUiEvent {
    data class ToggleFilterBottomSheet(val type: String?) : WishlistUiEvent()
    data class ChangeSelectedFilter(val filter: Filter) : WishlistUiEvent()
    data class ChangeSortOption(val sortOption: String) : WishlistUiEvent()
    data object ApplySortOption : WishlistUiEvent()
    data class ChangeSelectedCondition(val conditionOption: ConditionOption) : WishlistUiEvent()
    data class ChangeSelectedAvailability(val availabilityOption: AvailabilityOption) : WishlistUiEvent()
    data class ChangeSelectedPricingModel(val pricingModel: NewPricingModel) : WishlistUiEvent()
    data class ChangeCashRange(val cash: Float) : WishlistUiEvent()
    data class ChangeCoinRange(val coins: Float) : WishlistUiEvent()
    data class ClearSpecificFilter(val filter: Filter) : WishlistUiEvent()
    data object ClearFilters : WishlistUiEvent()
    data object ApplyFilters : WishlistUiEvent()
    data class RemoveFromWishlist(val productId: String) : WishlistUiEvent()

    data class IsLoading(val isLoading: Boolean) : WishlistUiEvent()

    // Category-related events
    data class ChangeSelectedTertiaryCategory(val category: FilterTertiaryCategory) : WishlistUiEvent()
    data class RemoveSelectedTertiaryCategory(val category: FilterTertiaryCategory) : WishlistUiEvent()
    data class SelectAllCategories(val category: FilterCategoryUiModel) : WishlistUiEvent()

    data object ClearError : WishlistUiEvent()
    data object ClearSuccessMessage : WishlistUiEvent()
}
