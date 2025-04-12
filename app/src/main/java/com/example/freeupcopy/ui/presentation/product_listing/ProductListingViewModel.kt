package com.example.freeupcopy.ui.presentation.product_listing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.freeupcopy.domain.enums.Filter
import com.example.freeupcopy.domain.use_case.GetProductCardsUseCase
import com.example.freeupcopy.domain.use_case.ProductCardsQueryParameters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProductListingViewModel @Inject constructor(
    private val getProductCardsUseCase: GetProductCardsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ProductListingUiState())
    val state = _state.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val productCards = _state
        .map {
            Triple(
                it.searchQuery,
                it.availabilityOptions,
                Pair(
                    Triple(it.conditionOptions, it.selectedTertiaryCategory, it.selectedFilter),
                    it.appliedSortOption
                )
            )
        }
        .distinctUntilChanged()
        .flatMapLatest { (query, availabilityOptions, pairTripleAndSort) ->
            val (innerTriple, appliedSortOption) = pairTripleAndSort
            val (conditionOptions, selectedCategories, _) = innerTriple

            // Format the query: Trim and replace spaces with plus signs.
            val formattedQuery = query.trim().replace("\\s+".toRegex(), "+")

            // Build the status filter based on availability options.
            val statusFilter = if (availabilityOptions.isEmpty()) {
                "available,sold"
            } else {
                availabilityOptions.joinToString(",") { it.filterName }
            }

            // Build the condition filter if any condition options exist.
            val conditionFilter = if (conditionOptions.isEmpty()) {
                null
            } else {
                conditionOptions.joinToString(",") { it.displayValue }
            }

            // Map the selected tertiary categories to a combined string for the backend.
            val combinedCategories = selectedCategories
                .map { "${it.parentCategory.lowercase()}_${it.name.lowercase()}" }
                .distinct()

            val filters = buildMap {
                put("status", statusFilter)
                conditionFilter?.let { put("condition", it) }
                if (combinedCategories.isNotEmpty()) {
                    // "combinedCategory" will be processed by your backend.
                    put("combinedCategory", combinedCategories.joinToString(","))
                }
            }

            // Pass the appliedSortOption (set when the user hits "Apply")
            getProductCardsUseCase(
                ProductCardsQueryParameters(
                    search = formattedQuery,
                    filters = filters,
                    sort = appliedSortOption
                )
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            PagingData.empty()
        )

    fun onEvent(event: ProductListingUiEvent) {
        when (event) {

            is ProductListingUiEvent.ChangeSearchQuery -> {
                _state.update {
                    it.copy(searchQuery = event.query)
                }
            }

            is ProductListingUiEvent.ChangeSelectedFilter -> {
                _state.update {
                    it.copy(selectedFilter = event.filter)
                }
            }

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
                if (event.type == "filter") {
                    _state.update {
                        it.copy(
                            isBottomSheetOpen = !_state.value.isBottomSheetOpen,
                            isFilterBottomSheet = true,
                            isSortBottomSheet = false
                        )
                    }
                } else {
                    // When showing the sort bottom sheet, prefill tempSortOption with appliedSortOption.
                    _state.update { currentState ->
                        currentState.copy(
                            isBottomSheetOpen = !_state.value.isBottomSheetOpen,
                            isFilterBottomSheet = false,
                            isSortBottomSheet = true,
                            tempSortOption = currentState.appliedSortOption
                        )
                    }
                }
            }

            is ProductListingUiEvent.ChangeSortOption -> {
                // Instead of directly applying sort, update the temporary sort option.
                _state.update {
                    it.copy(tempSortOption = event.sortOption)
                }
            }

            is ProductListingUiEvent.ApplySortOption -> {
                // Apply the temporary sort option so the query uses it.
                _state.update { currentState ->
                    currentState.copy(
                        appliedSortOption = currentState.tempSortOption,
                        isBottomSheetOpen = false,
                        isSortBottomSheet = false
                    )
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
                        availableFilters = listOf(
                            Filter.AVAILABILITY,
                            Filter.CONDITION,
                            Filter.SELLER_RATING,
                            Filter.PRICE,
                            Filter.CATEGORY,
                            Filter.BRAND
                        )
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
                    val currentSelectedCategories =
                        currentState.selectedTertiaryCategory.toMutableList()
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
                                specialFilter !in listOf(
                                    Filter.AVAILABILITY,
                                    Filter.CONDITION,
                                    Filter.SELLER_RATING,
                                    Filter.PRICE,
                                    Filter.CATEGORY,
                                    Filter.BRAND
                                )
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
                            specialFilter !in listOf(
                                Filter.AVAILABILITY,
                                Filter.CONDITION,
                                Filter.SELLER_RATING,
                                Filter.PRICE,
                                Filter.CATEGORY,
                                Filter.BRAND
                            )
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
                    val currentSelectedCategories =
                        currentState.selectedTertiaryCategory.toMutableList()
                    val currentAvailableFilters = currentState.availableFilters.toMutableList()

                    // If any tertiary category from this category is not selected, select all
                    // Otherwise, deselect all
                    val allTertiaryCategories =
                        category.subcategories.flatMap { it.tertiaryCategories }
                    val areAllSelected =
                        allTertiaryCategories.all { it in currentSelectedCategories }

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
                                    val isFilterUsedByOtherCategories =
                                        currentSelectedCategories.any {
                                            it.specialFilters.contains(specialFilter)
                                        }
                                    if (!isFilterUsedByOtherCategories &&
                                        specialFilter !in listOf(
                                            Filter.AVAILABILITY,
                                            Filter.CONDITION,
                                            Filter.SELLER_RATING,
                                            Filter.PRICE,
                                            Filter.CATEGORY,
                                            Filter.BRAND
                                        )
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
                    val currentSelectedSpecialOptions =
                        currentState.selectedSpecialOptions.toMutableList()
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
}
