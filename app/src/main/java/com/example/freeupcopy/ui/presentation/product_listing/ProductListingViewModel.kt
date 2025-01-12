package com.example.freeupcopy.ui.presentation.product_listing

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProductListingViewModel : ViewModel() {
    private val _state = MutableStateFlow(ProductListingUiState())
    val state = _state.asStateFlow()

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
