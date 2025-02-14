package com.example.freeupcopy.ui.presentation.product_listing

import androidx.lifecycle.ViewModel
import com.example.freeupcopy.domain.enums.Filter
import com.example.freeupcopy.domain.enums.FilterCategoryUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProductListingViewModel @Inject constructor(

) : ViewModel() {
    private val _state = MutableStateFlow(ProductListingUiState())
    val state = _state.asStateFlow()

    fun onEvent(event: ProductListingUiEvent) {
        when(event) {
            is ProductListingUiEvent.ChangeSelectedFilter -> {
                _state.update {
                    it.copy(selectedFilter = event.filter)
                }
            }

//            is ProductListingUiEvent.ToggleShowSort -> {
//                _state.update {
//                    it.copy(
//                        isBottomSheetOpen = true,
//                        isSortBottomSheet = !_state.value.isSortBottomSheet,
//                        isFilterBottomSheet = false
//                    )
//                }
//            }
//
//            is ProductListingUiEvent.ToggleShowFilter -> {
//                _state.update {
//                    it.copy(
//                        isBottomSheetOpen = true,
//                        isFilterBottomSheet = !_state.value.isFilterBottomSheet,
//                        isSortBottomSheet = false
//                    )
//                }
//            }

//            is ProductListingUiEvent.ToggleBottomSheet -> {
//                _state.update {
//                    it.copy(
//                        isBottomSheetOpen = !it.isBottomSheetOpen
//                    )
//                }
//            }
            is ProductListingUiEvent.BottomSheetDismiss -> {
                _state.update {
                    it.copy(
                        isBottomSheetOpen = false,
                        isFilterBottomSheet = false,
                        isSortBottomSheet = false
                    )
                }
            }

            is ProductListingUiEvent.ToggleBottomSheet -> {
                if(event.type == "filter") {
                    _state.update {
                        it.copy(
                            isBottomSheetOpen = !_state.value.isBottomSheetOpen,
                            isFilterBottomSheet = true,
                            isSortBottomSheet = false
                        )
                    }
                }
                else {
                    _state.update {
                        it.copy(
                            isBottomSheetOpen = !_state.value.isBottomSheetOpen,
                            isFilterBottomSheet = false,
                            isSortBottomSheet = true
                        )
                    }
                }
            }

            is ProductListingUiEvent.ClearFilters -> {
                _state.update {
                    it.copy(
                        availabilityOptions = emptyList(),
                        conditionOptions = emptyList(),
                        sellerRatingOptions = emptyList(),
                        sellerBadgeOptions = emptyList(),
                        pricingModelOptions = emptyList(),
                        selectedCashRange = null,
                        selectedCoinRange = null,
                        selectedTertiaryCategory = emptyList(),
                        availableFilters = listOf(Filter.AVAILABILITY, Filter.CONDITION, Filter.SELLER_RATING, Filter.PRICE, Filter.CATEGORY, Filter.BRAND)
                    )
                }
            }

            is ProductListingUiEvent.ChangeSelectedAvailability -> {
                _state.update { currentState ->
                    val currentOptions = currentState.availabilityOptions.toMutableList()
                    if (currentOptions.contains(event.availabilityOption)) {
                        currentOptions.remove(event.availabilityOption)
                    } else {
                        currentOptions.add(event.availabilityOption)
                    }
                    currentState.copy(availabilityOptions = currentOptions)
                }
            }

            is ProductListingUiEvent.ChangeSelectedCondition -> {
                _state.update { currentState ->
                    val currentOptions = currentState.conditionOptions.toMutableList()
                    if (currentOptions.contains(event.conditionOption)) {
                        currentOptions.remove(event.conditionOption)
                    } else {
                        currentOptions.add(event.conditionOption)
                    }
                    currentState.copy(conditionOptions = currentOptions)
                }
            }

            is ProductListingUiEvent.ChangeSelectedSellerRating -> {
                _state.update { currentState ->
                    val currentOptions = currentState.sellerRatingOptions.toMutableList()
                    if (currentOptions.contains(event.sellerRatingOption)) {
                        currentOptions.remove(event.sellerRatingOption)
                    } else {
                        currentOptions.add(event.sellerRatingOption)
                    }
                    currentState.copy(sellerRatingOptions = currentOptions)
                }
            }

            is ProductListingUiEvent.ChangeSelectedSellerBadge -> {
                _state.update { currentState ->
                    val currentOptions = currentState.sellerBadgeOptions.toMutableList()
                    if (currentOptions.contains(event.sellerBadge)) {
                        currentOptions.remove(event.sellerBadge)
                    } else {
                        currentOptions.add(event.sellerBadge)
                    }
                    currentState.copy(sellerBadgeOptions = currentOptions)
                }
            }

            is ProductListingUiEvent.ChangeSelectedPricingModel -> {
                _state.update { currentState ->
                    val currentOptions = currentState.pricingModelOptions.toMutableList()
                    if (currentOptions.contains(event.pricingModel)) {
                        currentOptions.remove(event.pricingModel)
                    } else {
                        currentOptions.add(event.pricingModel)
                    }
                    currentState.copy(pricingModelOptions = currentOptions)
                }
            }

            is ProductListingUiEvent.ChangeCashRange -> {
                _state.update {
                    it.copy(selectedCashRange = event.cash)
                }
            }

            is ProductListingUiEvent.ChangeCoinRange -> {
                _state.update {
                    it.copy(selectedCoinRange = event.coins)
                }
            }

            is ProductListingUiEvent.ChangeSelectedTertiaryCategory -> {
                _state.update { currentState ->
                    val currentSelectedCategories = currentState.selectedTertiaryCategory.toMutableList()
                    val currentAvailableFilters = currentState.availableFilters.toMutableList()

                    if (currentSelectedCategories.contains(event.tertiaryCategory)) {
                        // Remove category and its special filters
                        currentSelectedCategories.remove(event.tertiaryCategory)

                        // Remove special filters only if no other selected category uses them
                        event.tertiaryCategory.specialFilters.forEach { specialFilter ->
                            val isFilterUsedByOtherCategories = currentSelectedCategories.any {
                                it.specialFilters.contains(specialFilter)
                            }
                            if (!isFilterUsedByOtherCategories &&
                                specialFilter !in listOf(Filter.AVAILABILITY, Filter.CONDITION, Filter.SELLER_RATING, Filter.PRICE, Filter.CATEGORY, Filter.BRAND)
                            ) {
                                currentAvailableFilters.remove(specialFilter)
                            }
                        }
                    } else {
                        // Add category and its special filters
                        currentSelectedCategories.add(event.tertiaryCategory)
                        currentAvailableFilters.addAll(event.tertiaryCategory.specialFilters)
                        // Remove duplicates while preserving order
                        val uniqueFilters = currentAvailableFilters.distinct()
                        currentAvailableFilters.clear()
                        currentAvailableFilters.addAll(uniqueFilters)
                    }

                    currentState.copy(
                        selectedTertiaryCategory = currentSelectedCategories,
                        availableFilters = currentAvailableFilters
                    )
                }
            }

            is ProductListingUiEvent.RemoveSpecialOptions -> {
                _state.update { currentState ->
                    val currentAvailableFilters = currentState.availableFilters.toMutableList()

                    // Get all special filters from the tertiary category being removed
                    event.tertiaryCategory.specialFilters.forEach { specialFilter ->
                        // Check if any remaining selected category uses this filter
                        val isFilterUsedByOtherCategories = currentState.selectedTertiaryCategory
                            .filter { it != event.tertiaryCategory }
                            .any { it.specialFilters.contains(specialFilter) }

                        // Remove the filter if it's not used by other categories and not a default filter
                        if (!isFilterUsedByOtherCategories &&
                            specialFilter !in listOf(Filter.AVAILABILITY, Filter.CONDITION, Filter.SELLER_RATING, Filter.PRICE, Filter.CATEGORY, Filter.BRAND)
                        ) {
                            currentAvailableFilters.remove(specialFilter)
                        }
                    }

                    currentState.copy(availableFilters = currentAvailableFilters)
                }
            }

            is ProductListingUiEvent.ToggleSelectAll -> {
                _state.update { currentState ->
                    val category = event.selectedFilter
                    val currentSelectedCategories = currentState.selectedTertiaryCategory.toMutableList()
                    val currentAvailableFilters = currentState.availableFilters.toMutableList()

                    // If any tertiary category from this category is not selected, select all
                    // Otherwise, deselect all
                    val allTertiaryCategories = category.subcategories.flatMap { it.tertiaryCategories }
                    val areAllSelected = allTertiaryCategories.all { it in currentSelectedCategories }

                    if (!areAllSelected) {
                        // Add all tertiary categories from this category
                        category.subcategories.forEach { subCategory ->
                            subCategory.tertiaryCategories.forEach { tertiaryCategory ->
                                if (!currentSelectedCategories.contains(tertiaryCategory)) {
                                    currentSelectedCategories.add(tertiaryCategory)
                                    // Add special filters
                                    currentAvailableFilters.addAll(tertiaryCategory.specialFilters)
                                }
                            }
                        }
                    } else {
                        // Remove all tertiary categories from this category
                        category.subcategories.forEach { subCategory ->
                            subCategory.tertiaryCategories.forEach { tertiaryCategory ->
                                currentSelectedCategories.remove(tertiaryCategory)
                                // Remove special filters if not used by remaining categories
                                tertiaryCategory.specialFilters.forEach { specialFilter ->
                                    val isFilterUsedByOtherCategories = currentSelectedCategories.any {
                                        it.specialFilters.contains(specialFilter)
                                    }
                                    if (!isFilterUsedByOtherCategories &&
                                        specialFilter !in listOf(Filter.AVAILABILITY, Filter.CONDITION, Filter.SELLER_RATING, Filter.PRICE, Filter.CATEGORY, Filter.BRAND)
                                    ) {
                                        currentAvailableFilters.remove(specialFilter)
                                    }
                                }
                            }
                        }
                    }

                    // Remove duplicates while preserving order
                    val uniqueFilters = currentAvailableFilters.distinct()
                    currentAvailableFilters.clear()
                    currentAvailableFilters.addAll(uniqueFilters)

                    currentState.copy(
                        selectedTertiaryCategory = currentSelectedCategories,
                        availableFilters = currentAvailableFilters
                    )
                }
            }

            is ProductListingUiEvent.SpecialOptionSelectedChange -> {
                _state.update { currentState ->
                    val currentSelectedSpecialOptions = currentState.selectedSpecialOptions.toMutableList()
                    if (currentSelectedSpecialOptions.contains(event.filterSpecialOption)) {
                        currentSelectedSpecialOptions.remove(event.filterSpecialOption)
                    } else {
                        currentSelectedSpecialOptions.add(event.filterSpecialOption)
                    }
                    currentState.copy(selectedSpecialOptions = currentSelectedSpecialOptions)
                }
            }
        }
    }

    fun onClickFilter() {
        _state.value = _state.value.copy(
            isFilterSelected = true,
            isSortSelected = false
        )
    }

    fun onSortClick() {
        _state.value = _state.value.copy(
            isFilterSelected = false,
            isSortSelected = true,
            isSortApplied = true
        )
    }


    fun changeSortToRec(){
        _state.update {
            it.copy(
                sortingOption = SortOption.Recommended
            )
        }
    }

    fun changeSortToPriceLoToHi(){
        _state.update {
            it.copy(
                sortingOption = SortOption.PriceLowToHigh
            )
        }
    }

    fun changeSortToPriceHiToLo(){
        _state.update {
            it.copy(
                sortingOption = SortOption.PriceHighToLow
            )
        }
    }

    fun onClickAvailablity() {
        clrFilterSection()
        _state.value = _state.value.copy(
            isAvailablitySelected = !_state.value.isAvailablitySelected,
            filterSectionOpen = FilterClassOptions.Availiblity
        )
    }

    fun onClickAvailable() {
        _state.value = _state.value.copy(
            isAvailableSelected = !_state.value.isAvailableSelected
        )
    }

    fun onClickSoldOut() {
        _state.value = _state.value.copy(
            isSoldOutSelected = !_state.value.isSoldOutSelected
        )
    }

    fun onClickCondition() {
        clrFilterSection()
        _state.value = _state.value.copy(
            isConditionSelected = !_state.value.isConditionSelected,
            filterSectionOpen = FilterClassOptions.Condition
        )
    }

    fun onClickNewWithTags() {
        _state.value = _state.value.copy(
            isNewWithTagsSelected = !_state.value.isNewWithTagsSelected,

        )
    }

    fun onClickLikeNew() {
        _state.value = _state.value.copy(
            isLikeNewSelected = !_state.value.isLikeNewSelected
        )
    }

    fun onClickGood() {
        _state.value = _state.value.copy(
            isGoodSelected = !_state.value.isGoodSelected
        )
    }

    fun onClickUsed() {
        _state.value = _state.value.copy(
            isUsedSelected = !_state.value.isUsedSelected
        )
    }

    fun onClickSellerRating() {
        clrFilterSection()
        _state.value = _state.value.copy(
            isSellerRatingSelected = !_state.value.isSellerRatingSelected,
            filterSectionOpen = FilterClassOptions.SellerRating
        )
    }

    fun onClickRating4_0() {
        _state.value = _state.value.copy(
            isRating4_0Selected = !_state.value.isRating4_0Selected
        )
    }

    fun onClickRating4_5() {
        _state.value = _state.value.copy(
            isRating4_5Selected = !_state.value.isRating4_5Selected
        )
    }

    fun onClickRating4_7() {
        _state.value = _state.value.copy(
            isRating4_7Selected = !_state.value.isRating4_7Selected
        )
    }

    fun onClickPrice() {
        clrFilterSection()
        _state.value = _state.value.copy(
            isPriceSelected = !_state.value.isPriceSelected,
            filterSectionOpen = FilterClassOptions.Price
        )
    }

    fun onClickCash(){
        _state.update {
            it.copy(
                isCashSelected = !_state.value.isCashSelected
            )
        }
    }

    fun onClickCoin(){
        _state.update {
            it.copy(
                isCoinSelected = !_state.value.isCoinSelected
            )
        }
    }

    fun onCashChange(value: Float) {
        _state.value = _state.value.copy(
            cashSelected = value
        )
    }

    fun onCoinsChange(value: Float) {
        _state.value = _state.value.copy(
            coinsSelected = value
        )
    }

    fun onOfferSelect(value : Boolean){
        _state.value = _state.value.copy(
            isOfferSelected = value
        )
    }


    fun onClickSellerActive() {
        clrFilterSection()
        _state.value = _state.value.copy(
            isSellerActiveSelected = !_state.value.isSellerActiveSelected,
            filterSectionOpen = FilterClassOptions.SellerActive
        )
    }

    fun onClickSellerActiveThisWeek() {
        _state.value = _state.value.copy(
            isSellerActiveThisWeekSelected = !_state.value.isSellerActiveThisWeekSelected
        )
    }

    fun onClickSellerActiveThisMonth() {
        _state.value = _state.value.copy(
            isSellerActiveThisMonthSelected = !_state.value.isSellerActiveThisMonthSelected
        )
    }

    fun onClickCategory() {
        clrFilterSection()
        _state.value = _state.value.copy(
            isCategorySelected = !_state.value.isCategorySelected,
            filterSectionOpen = FilterClassOptions.Category
        )
    }

    fun onClickSize() {
        clrFilterSection()
        _state.value = _state.value.copy(
            isSizeSelected = !_state.value.isSizeSelected,
            filterSectionOpen = FilterClassOptions.Size
        )
    }

    fun openBottomSheet(){
        _state.update{
            it.copy(
                isBottomSheetOpen = true
            )
        }
    }

    fun closeBottomSheet(){
        _state.update {
            it.copy(
                isBottomSheetOpen = false
            )
        }
    }

    fun clrFilterSection(){
        _state.update {
            it.copy(
                isAvailablitySelected = false,
                isConditionSelected = false,
                isSellerRatingSelected = false,
                isPriceSelected = false,
                isSellerActiveSelected = false,
                isCategorySelected = false,
                isSizeSelected = false
            )
        }
    }

    fun toggleOption(option: String) {
        _state.value = when (option) {
            "New Arrival" -> _state.value.copy(isNewArrivalSelected = !_state.value.isNewArrivalSelected)
            "Free in Coin" -> _state.value.copy(isFreeInCoinSelected = !_state.value.isFreeInCoinSelected)
            "New with Tags" -> _state.value.copy(isNewWithTagsSelected = !_state.value.isNewWithTagsSelected)
            "SwapGo Assured" -> _state.value.copy(isSwapGoAssuredSelected = !_state.value.isSwapGoAssuredSelected)
            else -> _state.value
        }
    }

    fun checkOptionRowState(option: String) : Boolean{
        return when(option){
            "New Arrival" -> _state.value.isNewArrivalSelected
            "Free in Coin" -> _state.value.isFreeInCoinSelected
            "New with Tags" -> _state.value.isNewWithTagsSelected
            "SwapGo Assured" -> _state.value.isSwapGoAssuredSelected
            else -> false
        }
    }
}
