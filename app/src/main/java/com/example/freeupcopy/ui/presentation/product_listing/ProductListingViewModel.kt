package com.example.freeupcopy.ui.presentation.product_listing

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.local.RecentSearch
import com.example.freeupcopy.data.local.RecentlyViewed
import com.example.freeupcopy.data.local.RecentlyViewedDao
import com.example.freeupcopy.data.pref.SwapGoPref
import com.example.freeupcopy.di.AppModule
import com.example.freeupcopy.domain.enums.AvailabilityOption
import com.example.freeupcopy.domain.enums.ConditionOption
import com.example.freeupcopy.domain.enums.Filter
import com.example.freeupcopy.domain.enums.FilterCategoryUiModel
import com.example.freeupcopy.domain.enums.FilterTertiaryCategory
import com.example.freeupcopy.domain.enums.NewPricingModel
import com.example.freeupcopy.domain.model.TertiaryCategory
import com.example.freeupcopy.domain.repository.SellRepository
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListingViewModel @Inject constructor(
    private val getProductCardsUseCase: GetProductCardsUseCase,
    private val recentlyViewedDao: RecentlyViewedDao,
    private val swapGoPref: SwapGoPref,
    private val repository: SellRepository, // Add this
    private val wishlistStateManager: AppModule.WishlistStateManager, // Add this
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(ProductListingUiState())
    val state = _state.asStateFlow()

    // Add wishlist states
    private val _wishlistStates = MutableStateFlow<Map<String, Boolean>>(emptyMap())
    val wishlistStates = _wishlistStates.asStateFlow()

    private val priceType: String? = savedStateHandle["priceType"]
    private val maxPriceCash: String? = savedStateHandle["maxPriceCash"]
    private val maxPriceCoin: String? = savedStateHandle["maxPriceCoin"]
    private val primaryCategory: String? = savedStateHandle["primaryCategory"]
    private val secondaryCategory: String? = savedStateHandle["secondaryCategory"]
    private val tertiaryCategory: String? = savedStateHandle["tertiaryCategory"]

    init {
        updateFilterState()

        // Set up user data collection
        viewModelScope.launch {
            swapGoPref.getUser().collect { user ->
                _state.update { it.copy(user = user) }
            }
        }

        viewModelScope.launch {
            wishlistStateManager.wishlistUpdates.collect { (productId, isWishlisted) ->
                updateProductWishlistState(productId, isWishlisted)
            }
        }
    }

    // Add wishlist update method
    private fun updateProductWishlistState(productId: String, isWishlisted: Boolean) {
        _wishlistStates.update { current ->
            current + (productId to isWishlisted)
        }
    }

    private fun updateFilterState() {
        val pricingModels: List<NewPricingModel> =
            if (priceType.isNullOrEmpty()) {
                emptyList()
            } else {
                priceType.split(",").mapNotNull { part ->
                    NewPricingModel.entries.find { it.apiValue == part.trim() }
                }
            }

        // Handle category selection
        val selectedTertiaryCategories = mutableListOf<FilterTertiaryCategory>()
        val availableFilters = mutableListOf(
            Filter.AVAILABILITY,
            Filter.CONDITION,
            Filter.SELLER_RATING,
            Filter.PRICE,
            Filter.CATEGORY,
            Filter.BRAND
        )

        // If tertiaryCategory is provided, only select that specific tertiary category
        if (!tertiaryCategory.isNullOrEmpty()) {
            // Find the specific tertiary category across all categories
            var foundTertiaryCategory: FilterTertiaryCategory? = null

            // Search through all categories and subcategories
            for (category in FilterCategoryUiModel.predefinedCategories) {
                for (subCategory in category.subcategories) {
                    val tertiary = subCategory.tertiaryCategories.find { it.name == tertiaryCategory }
                    if (tertiary != null) {
                        foundTertiaryCategory = tertiary
                        break
                    }
                }
                if (foundTertiaryCategory != null) break
            }

            // Add the found tertiary category to the selected list
            foundTertiaryCategory?.let {
                selectedTertiaryCategories.add(it)
                availableFilters.addAll(it.specialFilters)
            }
        }
        // If no tertiaryCategory but primaryCategory is provided
        else if (!primaryCategory.isNullOrEmpty()) {
            val category = FilterCategoryUiModel.predefinedCategories.find { it.name == primaryCategory }
            category?.let { cat ->
                // If secondaryCategory is also provided, only select tertiary categories from that subcategory
                if (!secondaryCategory.isNullOrEmpty()) {
                    val subCategory = cat.subcategories.find { it.name == secondaryCategory }
                    subCategory?.let { sub ->
                        selectedTertiaryCategories.addAll(sub.tertiaryCategories)
                        // Add special filters from these tertiary categories
                        sub.tertiaryCategories.forEach { tertiary ->
                            availableFilters.addAll(tertiary.specialFilters)
                        }
                    }
                } else {
                    // If only primaryCategory is provided, select all tertiary categories from all subcategories
                    cat.subcategories.forEach { subCat ->
                        selectedTertiaryCategories.addAll(subCat.tertiaryCategories)
                        // Add special filters from these tertiary categories
                        subCat.tertiaryCategories.forEach { tertiary ->
                            availableFilters.addAll(tertiary.specialFilters)
                        }
                    }
                }
            }
        }

        // Remove duplicates from availableFilters while preserving order
        val uniqueFilters = availableFilters.distinct()

        _state.update {
            it.copy(
                pricingModelOptions = pricingModels,
                selectedCashRange = maxPriceCash?.toFloat(),
                selectedCoinRange = maxPriceCoin?.toFloat(),
//                primaryCategory = primaryCategory,
//                secondaryCategory = secondaryCategory,
//                tertiaryCategory = tertiaryCategory,
                selectedTertiaryCategory = selectedTertiaryCategories,
                availableFilters = uniqueFilters,
                // Set the selected filter to CATEGORY if tertiary categories are selected
                selectedFilter = if (selectedTertiaryCategories.isNotEmpty()) Filter.CATEGORY else it.selectedFilter
            )
        }
    }




    @OptIn(ExperimentalCoroutinesApi::class)
    val productCards = _state
        .map { state ->
            // Create a data object with all the parameters we need to track for changes
            ProductQueryState(
                userId = state.user?._id,
                searchQuery = state.searchQuery,
                availabilityOptions = state.availabilityOptions,
                conditionOptions = state.conditionOptions,
                selectedTertiaryCategory = state.selectedTertiaryCategory,
                selectedFilter = state.selectedFilter,
                appliedSortOption = state.appliedSortOption ?: "",
                pricingModelOptions = state.pricingModelOptions,
                selectedCashRange = Pair(null, state.selectedCashRange),
                selectedCoinRange = Pair(null, state.selectedCoinRange),
//                primaryCategory = state.primaryCategory,
//                secondaryCategory = state.secondaryCategory,
//                tertiaryCategory = state.tertiaryCategory
            )
        }
        .distinctUntilChanged()
        .flatMapLatest { queryState ->
            // Format the query: Trim and replace spaces with plus signs.
            val formattedQuery = queryState.searchQuery.trim().replace("\\s+".toRegex(), "+")

            // Build the status filter based on availability options.
            val statusFilter = if (queryState.availabilityOptions.isEmpty()) {
                "available,sold"
            } else {
                queryState.availabilityOptions.joinToString(",") { it.filterName }
            }

            // Build the condition filter if any condition options exist.
            val conditionFilter = if (queryState.conditionOptions.isEmpty()) {
                null
            } else {
                queryState.conditionOptions.joinToString(",") { it.displayValue }
            }

            // Map the selected tertiary categories to a combined string for the backend.
            val combinedCategories = queryState.selectedTertiaryCategory
                .map { "${it.parentCategory.lowercase()}_${it.name.lowercase()}" }
                .distinct()

            val filters = buildMap {
                put("status", statusFilter)
                conditionFilter?.let { put("condition", it) }
                if (combinedCategories.isNotEmpty()) {
                    // "combinedCategory" will be processed by your backend.
                    put("combinedCategory", combinedCategories.joinToString(","))
                }
//
//                // Add category filters if they exist
//                queryState.primaryCategory?.let { put("primaryCategory", it) }
//                queryState.secondaryCategory?.let { put("secondaryCategory", it) }
//                queryState.tertiaryCategory?.let { put("tertiaryCategory", it) }
            }


            // Pass all parameters to the query
            getProductCardsUseCase(
                ProductCardsQueryParameters(
                    userId = queryState.userId,
                    search = formattedQuery,
                    filters = filters,
                    sort = queryState.appliedSortOption,
                    priceType = queryState.pricingModelOptions.joinToString(",") { it.apiValue },
                    minPriceCash = queryState.selectedCashRange?.first,
                    maxPriceCash = queryState.selectedCashRange?.second,
                    minPriceCoin = queryState.selectedCoinRange?.first,
                    maxPriceCoin = queryState.selectedCoinRange?.second,
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
            is ProductListingUiEvent.IsLoading -> {
                _state.update {
                    it.copy(isLoading = event.isLoading)
                }
            }

            is ProductListingUiEvent.ChangeSearchQuery -> {
                _state.update {
                    it.copy(searchQuery = event.query)
                }
            }

            is ProductListingUiEvent.SetInitialQuery -> {
                _state.update { it.copy(initialQuerySet = true) }
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

            is ProductListingUiEvent.ProductClicked -> {
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true) }
                    val existingRecentlyViewed =
                        recentlyViewedDao.getRecentlyViewExists(event.productId)
                    if (existingRecentlyViewed == null) {
                        val recentlyViewed = RecentlyViewed(
                            productId = event.productId,
                            imageUrl = event.productImageUrl,
                            title = event.title
                        )
                        recentlyViewedDao.insertWithLimit(recentlyViewed)
//                        Log.d("ProductListingVM", "Inserted recently viewed: $recentlyViewed")
                    } else {
                        // Update the timestamp if the record already exists
                        val updatedRecentlyViewed =
                            existingRecentlyViewed.copy(timestamp = System.currentTimeMillis())
                        recentlyViewedDao.insertWithLimit(updatedRecentlyViewed)
                    }
                    _state.update { it.copy(isLoading = false) }
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


            is ProductListingUiEvent.AddToWishlist -> {
                viewModelScope.launch {
                    // Optimistically update UI
                    updateProductWishlistState(event.productId, true)

                    repository.addToWishlist(event.productId).collect { response ->
                        when (response) {
                            is Resource.Success -> {
                                wishlistStateManager.notifyWishlistChanged(event.productId, true)
                                _state.update {
                                    it.copy(isLoading = false, error = "")
                                }
                            }
                            is Resource.Error -> {
                                // Revert optimistic update
                                updateProductWishlistState(event.productId, false)
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        error = response.message ?: "Failed to add to wishlist"
                                    )
                                }
                            }
                            is Resource.Loading -> {
                                _state.update { it.copy(isLoading = true, error = "") }
                            }
                        }
                    }
                }
            }

            is ProductListingUiEvent.RemoveFromWishlist -> {
                viewModelScope.launch {
                    // Optimistically update UI
                    updateProductWishlistState(event.productId, false)

                    repository.removeFromWishlist(event.productId).collect { response ->
                        when (response) {
                            is Resource.Success -> {
                                wishlistStateManager.notifyWishlistChanged(event.productId, false)
                                _state.update {
                                    it.copy(isLoading = false, error = "")
                                }
                            }
                            is Resource.Error -> {
                                // Revert optimistic update
                                updateProductWishlistState(event.productId, true)
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        error = response.message ?: "Failed to remove from wishlist"
                                    )
                                }
                            }
                            is Resource.Loading -> {
                                _state.update { it.copy(isLoading = true, error = "") }
                            }
                        }
                    }
                }
            }
        }
    }
}

// Data class to hold all query parameters
data class ProductQueryState(
    val userId: String? = null,
    val searchQuery: String,
    val availabilityOptions: List<AvailabilityOption>,
    val conditionOptions: List<ConditionOption>,
    val selectedTertiaryCategory: List<FilterTertiaryCategory>,
    val selectedFilter: Filter?,
    val appliedSortOption: String,
    val pricingModelOptions: List<NewPricingModel>,
    val selectedCashRange: Pair<Float?, Float?>?,
    val selectedCoinRange: Pair<Float?, Float?>?,
//    val primaryCategory: String?,
//    val secondaryCategory: String?,
//    val tertiaryCategory: String?
)
