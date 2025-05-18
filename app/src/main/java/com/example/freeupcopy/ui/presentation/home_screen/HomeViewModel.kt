package com.example.freeupcopy.ui.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.freeupcopy.domain.enums.Filter
import com.example.freeupcopy.domain.repository.SellRepository
import com.example.freeupcopy.domain.use_case.GetProductCardsUseCase
import com.example.freeupcopy.domain.use_case.ProductCardsQueryParameters
import com.example.freeupcopy.ui.presentation.product_listing.ProductQueryState
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
class HomeViewModel @Inject constructor(
    private val repository: SellRepository,
    private val getProductCardsUseCase: GetProductCardsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()


    //    // Use stateIn pattern similar to ProductViewModel's similarProducts implementation
//    @OptIn(ExperimentalCoroutinesApi::class)
//    val bestInMenProducts = MutableStateFlow(
//        ProductCardsQueryParameters(
//            filters = mapOf("primaryCategory" to "Men", "status" to "available"),
//            limit = 6,
//            sort = "relevance"
//        )
//    )
//        .flatMapLatest { params ->
//            getProductCardsUseCase(params)
//        }
//        .stateIn(
//            viewModelScope,
//            SharingStarted.Lazily,
//            PagingData.empty()
//        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val exploreProducts = _state
        .map { state ->
            // Create a data object with all the parameters we need to track for changes
            ProductQueryState(
                searchQuery = "",
                availabilityOptions = state.availabilityOptions, // Use applied values
                conditionOptions = state.conditionOptions,       // Use applied values
                selectedTertiaryCategory = emptyList(),
                selectedFilter = null,
                appliedSortOption = state.appliedSortOption ?: "",
                pricingModelOptions = state.pricingModelOptions, // Use applied values
                selectedCashRange = Pair(null, state.selectedCashRange),
                selectedCoinRange = Pair(null, state.selectedCoinRange)
            )
        }
        .distinctUntilChanged()
        .flatMapLatest { queryState ->
            // Format the query: Trim and replace spaces with plus signs

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

            val filters = buildMap {
                put("status", statusFilter)
                conditionFilter?.let { put("condition", it) }
            }

            // Pass all parameters to the query
            getProductCardsUseCase(
                ProductCardsQueryParameters(
                    search = "",
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



    init {
        // Fetch all product categories on initialization
        fetchBestInMenProducts()
        fetchBestInWomenWear()
        fetchEthnicWomenProducts()
    }

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.RefreshWomenWear -> fetchBestInWomenWear()
            is HomeUiEvent.RefreshMenWear -> fetchBestInMenProducts()
            is HomeUiEvent.RefreshEthnicWear -> fetchEthnicWomenProducts()
            is HomeUiEvent.RefreshAllProducts -> {
                fetchBestInMenProducts()
                fetchBestInWomenWear()
                fetchEthnicWomenProducts()
            }

            is HomeUiEvent.IsLoading -> {
                _state.update { it.copy(isLoading = event.isLoading) }
            }


            is HomeUiEvent.ChangeSelectedFilter -> {
                _state.update { it.copy(selectedFilter = event.filter) }
            }


            // Sort event handling
            is HomeUiEvent.ApplySortOption -> {
                val newActiveFilters = _state.value.activeFilters.toMutableSet()
                // Add or remove sort from active filters based on selection
                if (_state.value.tempSortOption != null && _state.value.tempSortOption != "relevance") {
                    newActiveFilters.add(Filter.SORT)
                } else {
                    newActiveFilters.remove(Filter.SORT)
                }

                _state.update { currentState ->
                    currentState.copy(
                        appliedSortOption = currentState.tempSortOption,
                        isFilterBottomSheetOpen = false,
                        activeFilters = newActiveFilters
                    )
                }
                fetchExploreProducts()
            }

            // Sort events
            is HomeUiEvent.ChangeSortOption -> {
                _state.update {
                    it.copy(tempSortOption = event.sortOption)
                }
            }

            is HomeUiEvent.ToggleFilterBottomSheet -> {
                if (event.type != null) {
                    // When opening the filter sheet, initialize temp values with current values
                    _state.update {
                        it.copy(
                            isFilterBottomSheetOpen = true,
                            filterBottomSheetType = event.type,
                            tempAvailabilityOptions = it.availabilityOptions,
                            tempConditionOptions = it.conditionOptions,
                            tempSellerRatingOptions = it.sellerRatingOptions,
                            tempSellerBadgeOptions = it.sellerBadgeOptions,
                            tempPricingModelOptions = it.pricingModelOptions,
                            tempSelectedCashRange = it.selectedCashRange,
                            tempSelectedCoinRange = it.selectedCoinRange,
                            tempSortOption = it.appliedSortOption
                        )
                    }
                } else {
                    // When closing without applying, just close the sheet
                    _state.update {
                        it.copy(
                            isFilterBottomSheetOpen = false,
                            filterBottomSheetType = null
                        )
                    }
                }
            }

            is HomeUiEvent.ChangeSelectedAvailability -> {
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

            is HomeUiEvent.ChangeSelectedCondition -> {
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

            is HomeUiEvent.ChangeSelectedSellerRating -> {
                _state.update { currentState ->
                    val currentOptions = currentState.tempSellerRatingOptions.toMutableList()
                    if (currentOptions.contains(event.sellerRatingOption)) {
                        currentOptions.remove(event.sellerRatingOption)
                    } else {
                        currentOptions.add(event.sellerRatingOption)
                    }
                    currentState.copy(tempSellerRatingOptions = currentOptions)
                }
            }

            is HomeUiEvent.ChangeSelectedSellerBadge -> {
                _state.update { currentState ->
                    val currentOptions = currentState.tempSellerBadgeOptions.toMutableList()
                    if (currentOptions.contains(event.sellerBadge)) {
                        currentOptions.remove(event.sellerBadge)
                    } else {
                        currentOptions.add(event.sellerBadge)
                    }
                    currentState.copy(tempSellerBadgeOptions = currentOptions)
                }
            }

            is HomeUiEvent.ChangeSelectedPricingModel -> {
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

            is HomeUiEvent.ChangeCashRange -> {
                _state.update { it.copy(tempSelectedCashRange = event.cash) }
            }

            is HomeUiEvent.ChangeCoinRange -> {
                _state.update { it.copy(tempSelectedCoinRange = event.coins) }
            }

            is HomeUiEvent.ClearFilters -> {
                _state.update {
                    it.copy(
                        tempAvailabilityOptions = emptyList(),
                        tempConditionOptions = emptyList(),
                        tempSellerRatingOptions = emptyList(),
                        tempSellerBadgeOptions = emptyList(),
                        tempPricingModelOptions = emptyList(),
                        tempSelectedCashRange = null,
                        tempSelectedCoinRange = null
                    )
                }
            }

            is HomeUiEvent.ApplyFilters -> {
                // Apply temp values to actual values
                _state.update { currentState ->
                    val newActiveFilters = mutableSetOf<Filter>()

                    // Check which filters are active
                    if (currentState.tempConditionOptions.isNotEmpty()) {
                        newActiveFilters.add(Filter.CONDITION)
                    }

                    if (currentState.tempPricingModelOptions.isNotEmpty()) {
                        newActiveFilters.add(Filter.PRICE)
                    }

                    if (currentState.tempSellerRatingOptions.isNotEmpty() ||
                        currentState.tempSellerBadgeOptions.isNotEmpty()
                    ) {
                        newActiveFilters.add(Filter.SELLER_RATING)
                    }

                    currentState.copy(
                        availabilityOptions = currentState.tempAvailabilityOptions,
                        conditionOptions = currentState.tempConditionOptions,
                        sellerRatingOptions = currentState.tempSellerRatingOptions,
                        sellerBadgeOptions = currentState.tempSellerBadgeOptions,
                        pricingModelOptions = currentState.tempPricingModelOptions,
                        selectedCashRange = currentState.tempSelectedCashRange,
                        selectedCoinRange = currentState.tempSelectedCoinRange,
                        activeFilters = newActiveFilters,
                        isFilterBottomSheetOpen = false
                    )
                }
            }
        }
    }



    // Existing fetch functions...

    private fun fetchExploreProducts() {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isExploreProductsLoading = true, exploreProductsError = "") }

                // Build filters based on selected options
                val filters = buildMap {
                    // Status filter
                    val statusFilter = if (_state.value.availabilityOptions.isEmpty()) {
                        "available,sold"
                    } else {
                        _state.value.availabilityOptions.joinToString(",") { it.filterName }
                    }
                    put("status", statusFilter)

                    // Condition filter
                    if (_state.value.conditionOptions.isNotEmpty()) {
                        put("condition", _state.value.conditionOptions.joinToString(",") { it.displayValue })
                    }
                }

                // Get pricing model
                val priceType = _state.value.pricingModelOptions.joinToString(",") { it.apiValue }

                val response = repository.fetchProductCards(
                    limit = 12,
                    page = 1,
                    search = "",
                    sort = _state.value.appliedSortOption,
                    filters = filters,
                    priceType = if (priceType.isNotEmpty()) priceType else null,
                    minPriceCash = null,
                    maxPriceCash = _state.value.selectedCashRange,
                    minPriceCoin = null,
                    maxPriceCoin = _state.value.selectedCoinRange
                )

                _state.update {
                    it.copy(
                        exploreProducts = response.products,
                        isExploreProductsLoading = false,
                        exploreProductsError = ""
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isExploreProductsLoading = false,
                        exploreProductsError = e.message ?: "Unknown error",
                        exploreProducts = emptyList()
                    )
                }
            }
        }
    }





    private fun fetchBestInMenProducts() {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isBestInMenLoading = true, bestInMenError = "") }

                val response = repository.fetchProductCards(
                    limit = 6,
                    page = 1,
                    search = null,
                    sort = "relevance",
                    filters = mapOf(
                        "primaryCategory" to "Men",
                        "status" to "available"
                    )
                )

                _state.update {
                    it.copy(
                        bestInMenProducts = response.products,
                        isBestInMenLoading = false,
                        bestInMenError = ""
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        bestInMenProducts = emptyList(),
                        bestInMenError = e.message ?: "Unknown error",
                        isBestInMenLoading = false
                    )
                }
            }
        }
    }

    private fun fetchBestInWomenWear() {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isBestInWomenLoading = true, bestInWomenError = "") }

                val response = repository.fetchProductCards(
                    limit = 12,
                    page = 1,
                    search = null,
                    sort = "relevance",
                    filters = mapOf(
                        "primaryCategory" to "Women",
                        "secondaryCategory" to "Ethnic,Western",
                        "status" to "available"
                    )
                )

                _state.update {
                    it.copy(
                        bestInWomenWear = response.products,
                        isBestInWomenLoading = false,
                        bestInWomenError = ""
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isBestInWomenLoading = false,
                        bestInWomenError = e.message ?: "Unknown error",
                        bestInWomenWear = emptyList()
                    )
                }
            }
        }
    }

    private fun fetchEthnicWomenProducts() {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isEthnicWomenLoading = true, ethnicWomenError = "") }

                val response = repository.fetchProductCards(
                    limit = 12,
                    page = 1,
                    search = null,
                    sort = "relevance",
                    filters = mapOf(
                        "primaryCategory" to "Women",
                        "secondaryCategory" to "Ethnic",
                        "status" to "available"
                    )
                )

                _state.update {
                    it.copy(
                        ethnicWomenProducts = response.products,
                        isEthnicWomenLoading = false,
                        ethnicWomenError = ""
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isEthnicWomenLoading = false,
                        ethnicWomenError = e.message ?: "Unknown error",
                        ethnicWomenProducts = emptyList()
                    )
                }
            }
        }
    }

}