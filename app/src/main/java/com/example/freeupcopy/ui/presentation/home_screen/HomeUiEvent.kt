package com.example.freeupcopy.ui.presentation.home_screen

import com.example.freeupcopy.domain.enums.AvailabilityOption
import com.example.freeupcopy.domain.enums.ConditionOption
import com.example.freeupcopy.domain.enums.Filter
import com.example.freeupcopy.domain.enums.NewPricingModel
import com.example.freeupcopy.domain.enums.SellerBadge
import com.example.freeupcopy.domain.enums.SellerRatingOption

sealed class HomeUiEvent {
    data object RefreshWomenWear : HomeUiEvent()
    data object RefreshMenWear : HomeUiEvent()
    data object RefreshEthnicWear : HomeUiEvent()
    data object RefreshAllProducts : HomeUiEvent()

    data class IsLoading(val isLoading: Boolean) : HomeUiEvent()
    data class OnLikeClick(val productId: String) : HomeUiEvent()

    // Filter events
    data class ToggleFilterBottomSheet(val type: String?) : HomeUiEvent()
    data class ChangeSelectedFilter(val filter: Filter) : HomeUiEvent()
    data class ChangeSelectedAvailability(val availabilityOption: AvailabilityOption) : HomeUiEvent()
    data class ChangeSelectedCondition(val conditionOption: ConditionOption) : HomeUiEvent()
    data class ChangeSelectedSellerRating(val sellerRatingOption: SellerRatingOption) : HomeUiEvent()
    data class ChangeSelectedSellerBadge(val sellerBadge: SellerBadge) : HomeUiEvent()
    data class ChangeSelectedPricingModel(val pricingModel: NewPricingModel) : HomeUiEvent()
    data class ChangeCashRange(val cash: Float) : HomeUiEvent()
    data class ChangeCoinRange(val coins: Float) : HomeUiEvent()
    data object ClearFilters : HomeUiEvent()
    data object ApplyFilters : HomeUiEvent()

    // Sort events
    data class ChangeSortOption(val sortOption: String) : HomeUiEvent()
    data object ApplySortOption : HomeUiEvent()
}