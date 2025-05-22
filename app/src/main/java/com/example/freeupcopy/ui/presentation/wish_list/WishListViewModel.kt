package com.example.freeupcopy.ui.presentation.wish_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.filter
import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.di.AppModule
import com.example.freeupcopy.domain.enums.AvailabilityOption
import com.example.freeupcopy.domain.enums.ConditionOption
import com.example.freeupcopy.domain.enums.Filter
import com.example.freeupcopy.domain.enums.FilterTertiaryCategory
import com.example.freeupcopy.domain.enums.NewPricingModel
import com.example.freeupcopy.domain.repository.SellRepository
import com.example.freeupcopy.domain.use_case.GetWishlistUseCase
import com.example.freeupcopy.domain.use_case.WishlistQueryParameters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishListViewModel @Inject constructor(
    private val repository: SellRepository,
    private val getWishlistUseCase: GetWishlistUseCase,
    private val wishlistStateManager: AppModule.WishlistStateManager
) : ViewModel() {

    private val _state = MutableStateFlow(WishListUiState())
    val state = _state.asStateFlow()

    // Add these properties to track removed items
    private val _removedItems = MutableStateFlow<Set<String>>(emptySet())
    private val removedItems = _removedItems.asStateFlow()

    // Modify your wishlist flow to filter out removed items
    @OptIn(ExperimentalCoroutinesApi::class)
    val wishlistItems = combine(
        _state.map { state ->
            WishlistQueryParameters(
                sort = state.appliedSortOption,
                status = if (state.availabilityOptions.isEmpty()) {
                    "available,sold"
                } else {
                    state.availabilityOptions.joinToString(",") { it.filterName }
                },
                condition = if (state.conditionOptions.isEmpty()) {
                    null
                } else {
                    state.conditionOptions.joinToString(",") { it.displayValue }
                },
                primaryCategory = state.selectedTertiaryCategories
                    .map { it.parentCategory }
                    .distinct()
                    .joinToString(",")
                    .takeIf { it.isNotEmpty() },
//                secondaryCategory = state.selectedTertiaryCategories
//                    .map { it }
//                    .distinct()
//                    .joinToString(",")
//                    .takeIf { it.isNotEmpty() },
                tertiaryCategory = state.selectedTertiaryCategories
                    .map { it.name }
                    .distinct()
                    .joinToString(",")
                    .takeIf { it.isNotEmpty() },
                priceType = state.pricingModelOptions
                    .joinToString(",") { it.apiValue }
                    .takeIf { it.isNotEmpty() },
                minPrice = null,
                maxPrice = state.selectedCashRange
            )
        }.distinctUntilChanged()
            .flatMapLatest { queryParams ->
                getWishlistUseCase(queryParams)
            },
        removedItems
    ) { pagingData, removedIds ->
        pagingData.filter { wishlistItem ->
            wishlistItem.productId.toString() !in removedIds
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        PagingData.empty()
    )


    fun onEvent(event: WishlistUiEvent) {
        when (event) {
            is WishlistUiEvent.ToggleFilterBottomSheet -> {
                if (event.type != null) {
                    _state.update {
                        it.copy(
                            isFilterBottomSheetOpen = true,
                            filterBottomSheetType = event.type,
                            // Initialize temp values with current values
                            tempConditionOptions = it.conditionOptions,
                            tempPricingModelOptions = it.pricingModelOptions,
                            tempAvailabilityOptions = it.availabilityOptions,
                            tempSelectedTertiaryCategories = it.selectedTertiaryCategories,
                            tempSelectedCashRange = it.selectedCashRange,
                            tempSelectedCoinRange = it.selectedCoinRange,
                            tempSortOption = it.appliedSortOption
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            isFilterBottomSheetOpen = false,
                            filterBottomSheetType = null
                        )
                    }
                }
            }

            is WishlistUiEvent.ChangeSelectedFilter -> {
                _state.update { it.copy(selectedFilter = event.filter) }
            }

            is WishlistUiEvent.IsLoading -> {
                _state.update { it.copy(isLoading = event.isLoading) }
            }

            is WishlistUiEvent.ChangeSortOption -> {
                _state.update { it.copy(tempSortOption = event.sortOption) }
            }

            is WishlistUiEvent.ApplySortOption -> {
                val newActiveFilters = _state.value.activeFilters.toMutableSet()
                if (_state.value.tempSortOption != null && _state.value.tempSortOption != "relevance") {
                    newActiveFilters.add(Filter.SORT)
                } else {
                    newActiveFilters.remove(Filter.SORT)
                }

                _state.update {
                    it.copy(
                        appliedSortOption = it.tempSortOption,
                        activeFilters = newActiveFilters,
                        isFilterBottomSheetOpen = false
                    )
                }
            }

            is WishlistUiEvent.ChangeSelectedCondition -> {
                _state.update { currentState ->
                    val currentOptions = currentState.tempConditionOptions.toMutableList()
                    if (currentOptions.contains(event.conditionOption)) {
                        currentOptions.remove(event.conditionOption)
                    } else {
                        currentOptions.add(event.conditionOption)
                    }
                    currentState.copy(tempConditionOptions = currentOptions)
                }
            }

            is WishlistUiEvent.ChangeSelectedPricingModel -> {
                _state.update { currentState ->
                    val currentOptions = currentState.tempPricingModelOptions.toMutableList()
                    if (currentOptions.contains(event.pricingModel)) {
                        currentOptions.remove(event.pricingModel)
                    } else {
                        currentOptions.add(event.pricingModel)
                    }
                    currentState.copy(tempPricingModelOptions = currentOptions)
                }
            }

            is WishlistUiEvent.ChangeSelectedAvailability -> {
                _state.update { currentState ->
                    val currentOptions = currentState.tempAvailabilityOptions.toMutableList()
                    if (currentOptions.contains(event.availabilityOption)) {
                        currentOptions.remove(event.availabilityOption)
                    } else {
                        currentOptions.add(event.availabilityOption)
                    }
                    currentState.copy(tempAvailabilityOptions = currentOptions)
                }
            }

            is WishlistUiEvent.ChangeCashRange -> {
                _state.update { it.copy(tempSelectedCashRange = event.cash) }
            }

            is WishlistUiEvent.ChangeCoinRange -> {
                _state.update { it.copy(tempSelectedCoinRange = event.coins) }
            }

            is WishlistUiEvent.ClearFilters -> {
                _state.update {
                    it.copy(
                        tempConditionOptions = emptyList(),
                        tempPricingModelOptions = emptyList(),
                        tempAvailabilityOptions = emptyList(),
                        tempSelectedTertiaryCategories = emptyList(),
                        tempSelectedCashRange = null,
                        tempSelectedCoinRange = null
                    )
                }
            }

            is WishlistUiEvent.RemoveFromWishlist -> {
                removeFromWishlist(event.productId)
            }

            is WishlistUiEvent.ChangeSelectedTertiaryCategory -> {
                _state.update { currentState ->
                    val currentCategories =
                        currentState.tempSelectedTertiaryCategories.toMutableList()
                    if (currentCategories.any { it.uniqueKey == event.category.uniqueKey }) {
                        currentCategories.removeAll { it.uniqueKey == event.category.uniqueKey }
                    } else {
                        currentCategories.add(event.category)
                    }
                    currentState.copy(tempSelectedTertiaryCategories = currentCategories)
                }
            }

            is WishlistUiEvent.RemoveSelectedTertiaryCategory -> {
                _state.update { currentState ->
                    val currentCategories =
                        currentState.tempSelectedTertiaryCategories.toMutableList()
                    currentCategories.removeAll { it.uniqueKey == event.category.uniqueKey }
                    currentState.copy(tempSelectedTertiaryCategories = currentCategories)
                }
            }

            is WishlistUiEvent.SelectAllCategories -> {
                _state.update { currentState ->
                    val allCategories = event.category.subcategories
                        .flatMap { it.tertiaryCategories }
                        .filter { it.parentCategory == event.category.name }

                    // If all categories are already selected, clear them
                    val currentSelectedInCategory = currentState.tempSelectedTertiaryCategories
                        .filter { it.parentCategory == event.category.name }

                    val newCategories = if (currentSelectedInCategory.size == allCategories.size) {
                        currentState.tempSelectedTertiaryCategories.filter { it.parentCategory != event.category.name }
                    } else {
                        val filtered =
                            currentState.tempSelectedTertiaryCategories.filter { it.parentCategory != event.category.name }
                        filtered + allCategories
                    }

                    currentState.copy(tempSelectedTertiaryCategories = newCategories)
                }
            }

            is WishlistUiEvent.ApplyFilters -> {
                val newActiveFilters = mutableSetOf<Filter>()

                // Check which filters are active
                if (_state.value.tempConditionOptions.isNotEmpty()) {
                    newActiveFilters.add(Filter.CONDITION)
                }

                if (_state.value.tempAvailabilityOptions.isNotEmpty()) {
                    newActiveFilters.add(Filter.AVAILABILITY)
                }

                if (_state.value.tempPricingModelOptions.isNotEmpty()) {
                    newActiveFilters.add(Filter.PRICE)
                }

                if (_state.value.tempSelectedTertiaryCategories.isNotEmpty()) {
                    newActiveFilters.add(Filter.CATEGORY)
                }

                _state.update { currentState ->
                    currentState.copy(
                        conditionOptions = currentState.tempConditionOptions,
                        availabilityOptions = currentState.tempAvailabilityOptions,
                        pricingModelOptions = currentState.tempPricingModelOptions,
                        selectedCashRange = currentState.tempSelectedCashRange,
                        selectedCoinRange = currentState.tempSelectedCoinRange,
                        selectedTertiaryCategories = currentState.tempSelectedTertiaryCategories,
                        activeFilters = newActiveFilters,
                        isFilterBottomSheetOpen = false
                    )
                }
            }

            is WishlistUiEvent.ClearSpecificFilter -> {
                val newActiveFilters = _state.value.activeFilters.toMutableSet()
                newActiveFilters.remove(event.filter)

                _state.update {
                    when (event.filter) {
                        Filter.SORT -> it.copy(
                            appliedSortOption = "relevance",
                            activeFilters = newActiveFilters
                        )

                        Filter.PRICE -> it.copy(
                            pricingModelOptions = emptyList(),
                            selectedCashRange = null,
                            selectedCoinRange = null,
                            activeFilters = newActiveFilters
                        )

                        Filter.CONDITION -> it.copy(
                            conditionOptions = emptyList(),
                            activeFilters = newActiveFilters
                        )

                        Filter.AVAILABILITY -> it.copy(
                            availabilityOptions = emptyList(),
                            activeFilters = newActiveFilters
                        )

                        Filter.CATEGORY -> it.copy(
                            selectedTertiaryCategories = emptyList(),
                            activeFilters = newActiveFilters
                        )

                        else -> it
                    }
                }
            }
        }
    }

    private fun removeFromWishlist(productId: String) {
        viewModelScope.launch {
            // First update local state to immediately remove the item visually
            _removedItems.update { it + productId }

            // Then perform the actual API call
            repository.removeFromWishlist(productId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        // Notify other ViewModels about the change
                        wishlistStateManager.notifyWishlistChanged(productId, false)

                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = "",
                                onSuccessfulDelete = true
                            )
                        }
                    }
                    is Resource.Error -> {
                        // If the API call fails, restore the item
                        _removedItems.update { it - productId }
                        _state.update {
                            it.copy(
                                error = result.message ?: "An unknown error occurred",
                                onSuccessfulDelete = false
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = true,
                                onSuccessfulDelete = false
                            )
                        }
                    }
                }
            }
        }
    }
}