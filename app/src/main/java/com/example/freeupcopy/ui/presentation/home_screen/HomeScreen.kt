package com.example.freeupcopy.ui.presentation.home_screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.freeupcopy.R
import com.example.freeupcopy.common.Constants.MAX_CASH_RANGE
import com.example.freeupcopy.common.Constants.MAX_COINS_RANGE
import com.example.freeupcopy.common.Constants.MIN_CASH_RANGE
import com.example.freeupcopy.common.Constants.MIN_COINS_RANGE
import com.example.freeupcopy.data.remote.dto.sell.ProductCard
import com.example.freeupcopy.domain.enums.ConditionOption
import com.example.freeupcopy.domain.enums.Filter
import com.example.freeupcopy.domain.enums.NewPricingModel
import com.example.freeupcopy.domain.enums.SellerBadge
import com.example.freeupcopy.domain.enums.SellerRatingOption
import com.example.freeupcopy.ui.presentation.common.rememberProductClickHandler
import com.example.freeupcopy.ui.presentation.home_screen.componants.AppNameTopSection
import com.example.freeupcopy.ui.presentation.home_screen.componants.BestInMen
import com.example.freeupcopy.ui.presentation.home_screen.componants.BestInWomenWear
import com.example.freeupcopy.ui.presentation.home_screen.componants.CategoriesHomeRow
import com.example.freeupcopy.ui.presentation.home_screen.componants.EthnicWomenWear
import com.example.freeupcopy.ui.presentation.home_screen.componants.FlexibleTopBar
import com.example.freeupcopy.ui.presentation.home_screen.componants.FlexibleTopBarDefaults
import com.example.freeupcopy.ui.presentation.home_screen.componants.HomeCarousel
import com.example.freeupcopy.ui.presentation.home_screen.componants.HomeCarouselItem
import com.example.freeupcopy.ui.presentation.home_screen.componants.HomeDashboard
import com.example.freeupcopy.ui.presentation.home_screen.componants.SearchTopSection
import com.example.freeupcopy.ui.presentation.main_screen.MainUiEvent
import com.example.freeupcopy.ui.presentation.product_card.ProductCard
import com.example.freeupcopy.ui.presentation.product_listing.ProductListingViewModel
import com.example.freeupcopy.ui.presentation.product_listing.componants.FilterOptionMultiSelectItem
import com.example.freeupcopy.ui.presentation.sell_screen.location_screen.location_screen.PleaseWaitLoading
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.CashColor1
import com.example.freeupcopy.ui.theme.CashColor2
import com.example.freeupcopy.ui.theme.CoinColor1
import com.example.freeupcopy.ui.theme.CoinColor2
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    lazyColumnState: LazyListState,
    onSearchBarClick: () -> Unit,
    onInboxClick: () -> Unit,
    onCartClick: () -> Unit,
    onCoinClick: () -> Unit,
    onCashClick: () -> Unit,
    onProductClick: (String) -> Unit,
    onAllCategoryClick: () -> Unit,
    onCategoryClick: (String?, String?, String?) -> Unit,
    onBannerCashOrCoinClick: (String) -> Unit,
    on99StoreClick: () -> Unit,
    on299CoinStoreClick: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    productListingViewModel: ProductListingViewModel = hiltViewModel(),
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val lifeCycleOwner = LocalLifecycleOwner.current

    val scope = rememberCoroutineScope()
    val exploreProducts = homeViewModel.exploreProducts.collectAsLazyPagingItems()

    // In your HomeScreen composable, you already have:
    val wishlistStates by homeViewModel.wishlistStates.collectAsState()

    val state by homeViewModel.state.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    // Handle error with Snackbar
    LaunchedEffect(state.error) {
        if (state.error.isNotBlank() && !state.isLoading) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = state.error,
                    duration = SnackbarDuration.Short
                )
                // Clear error after showing
                homeViewModel.onEvent(HomeUiEvent.ClearError)
            }
            Log.e("ProfileScreen", "Error: ${state.error}")
        }
    }


    // Create the product click handler
    val productClickHandler = rememberProductClickHandler(
        productListingViewModel = productListingViewModel,
        onProductClick = onProductClick,
        onLoadingStateChange = { isLoading ->
            homeViewModel.onEvent(HomeUiEvent.IsLoading(isLoading))
        }
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        topBar = {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
            ) {
                Spacer(modifier = Modifier.statusBarsPadding())
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primaryContainer)
                ) {

                    FlexibleTopBar(
                        scrollBehavior = scrollBehavior,
                        colors = FlexibleTopBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        AppNameTopSection(
                            onCoinClick = {
                                val currentState = lifeCycleOwner.lifecycle.currentState
                                if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                    onCoinClick()
                                }
                            },
                            onCashClick = {
                                val currentState = lifeCycleOwner.lifecycle.currentState
                                if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                    onCashClick()
                                }
                            }
                        )
                    }
                }
                Box {
                    FlexibleTopBar(
                        colors = FlexibleTopBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            scrolledContainerColor = Color.Transparent
                        )
                    ) {
                        SearchTopSection(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 12.dp, vertical = 8.dp
                                ),
                            onSearchBarClick = {
                                val currentState = lifeCycleOwner.lifecycle.currentState
                                if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                    onSearchBarClick()
                                }
                            },
                            onInboxClick = {
                                val currentState = lifeCycleOwner.lifecycle.currentState
                                if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                    onInboxClick()
                                }
                            },
                            onCartClick = {
                                val currentState = lifeCycleOwner.lifecycle.currentState
                                if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                    onCartClick()
                                }
                            }
                        )
                    }
                }
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                )
            }
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState) { snackbarData ->
                Snackbar(
                    snackbarData = snackbarData,
                    containerColor = when {
                        state.error.contains("already", ignoreCase = true) ||
                                state.error.contains("duplicate", ignoreCase = true) -> {
                            MaterialTheme.colorScheme.primaryContainer
                        }

                        state.error.contains("network", ignoreCase = true) ||
                                state.error.contains("internet", ignoreCase = true) ||
                                state.error.contains("connection", ignoreCase = true) -> {
                            Color(0xFFFF9800).copy(alpha = 0.9f) // Orange for network issues
                        }

                        else -> {
                            MaterialTheme.colorScheme.errorContainer // Red for general errors
                        }
                    },
                    contentColor = when {
                        state.error.contains("already", ignoreCase = true) ||
                                state.error.contains("duplicate", ignoreCase = true) -> {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        }

                        state.error.contains("network", ignoreCase = true) ||
                                state.error.contains("internet", ignoreCase = true) ||
                                state.error.contains("connection", ignoreCase = true) -> {
                            Color.White
                        }

                        else -> {
                            MaterialTheme.colorScheme.onErrorContainer
                        }
                    }
                )
            }
        }
    ) { innerPadding ->

        LazyColumn(
            state = lazyColumnState,
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize()
        ) {
            item {
                CategoriesHomeRow(
                    onAllCategoryClick = {
                        onAllCategoryClick()
                    },
                    onCategoryClick = { primaryCategory, secondaryCategory, tertiaryCategory ->
                        onCategoryClick(
                            primaryCategory,
                            secondaryCategory,
                            tertiaryCategory
                        )
                    },
                )
            }

            item {
                val items = listOf(
                    HomeCarouselItem(R.drawable.banner_coins, onClick = {
                        onBannerCashOrCoinClick("coin") // Special navigation parameter
                    }),
                    HomeCarouselItem(R.drawable.coins_wallet, onClick = {

                    }),
                    HomeCarouselItem(R.drawable.mix_banner, onClick = {
                        onBannerCashOrCoinClick("mix") // Special navigation parameter
                    })
                )

                HomeCarousel(
                    modifier = Modifier.zIndex(1f),
                    items = items,
                )
            }
            item {
                HomeDashboard(
                    modifier = Modifier.zIndex(1f),
                    on99StoreClick = {
                        on99StoreClick()
                    },
                    on299CoinStoreClick = {
                        on299CoinStoreClick()
                    }
                )
            }
            item {
                BestInWomenWear(
                    bestInWomenWear = state.bestInWomenWear,
                    isLoading = state.isBestInWomenLoading,
                    error = state.bestInWomenError,
                    onProductClick = {
                        productClickHandler.handleProductClick(it)
                    },
                    onRetry = {
                        homeViewModel.onEvent(HomeUiEvent.RefreshWomenWear)
                    },
                    onViewAll = {

                    },
                    onLikeClick = { productId ->
                        // Get current wishlist state
                        val product = state.bestInWomenWear.find { it._id == productId }
                        val isWishlisted = if (wishlistStates.containsKey(productId)) {
                            wishlistStates[productId]!!
                        } else {
                            product?.isWishlisted ?: false
                        }

                        if (isWishlisted) {
                            homeViewModel.onEvent(HomeUiEvent.RemoveFromWishlist(productId))
                        } else {
                            homeViewModel.onEvent(HomeUiEvent.AddToWishlist(productId))
                        }
                    }
                )
            }
            item {
                EthnicWomenWear(
                    ethnicWomenProducts = state.ethnicWomenProducts,
                    isLoading = state.isEthnicWomenLoading,
                    error = state.ethnicWomenError,
                    onRetry = {
                        homeViewModel.onEvent(HomeUiEvent.RefreshEthnicWear)
                    },
                    onProductClick = { product ->
                        productClickHandler.handleProductClick(product)
                    },
                    onLikeClick = { productId ->
                        // Get current wishlist state
                        val product = state.ethnicWomenProducts.find { it._id == productId }
                        val isWishlisted = if (wishlistStates.containsKey(productId)) {
                            wishlistStates[productId]!!
                        } else {
                            product?.isWishlisted ?: false
                        }

                        if (isWishlisted) {
                            homeViewModel.onEvent(HomeUiEvent.RemoveFromWishlist(productId))
                        } else {
                            homeViewModel.onEvent(HomeUiEvent.AddToWishlist(productId))
                        }
                    }
                )
            }
            item {
                BestInMen(
                    modifier = Modifier.zIndex(0f),
                    bestInMenProducts = state.bestInMenProducts,
                    isLoading = state.isBestInMenLoading,
                    error = state.bestInMenError,
                    onProductClick = { product ->
                        productClickHandler.handleProductClick(product)
                    },
                    onRetry = {
                        homeViewModel.onEvent(HomeUiEvent.RefreshMenWear)
                    }
                )
            }

            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Explore Products",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 4.dp)
                    )
                }

            }

            // Sticky header with background
            stickyHeader {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            FilterButton(
                                text = "Sort",
                                isActive = state.activeFilters.contains(Filter.SORT),
                                onClick = {
                                    homeViewModel.onEvent(HomeUiEvent.ToggleFilterBottomSheet("sort"))
                                }
                            )
                        }
                        item {
                            FilterButton(
                                text = "Price",
                                isActive = state.activeFilters.contains(Filter.PRICE),
                                onClick = {
                                    homeViewModel.onEvent(HomeUiEvent.ChangeSelectedFilter(Filter.PRICE))
                                    homeViewModel.onEvent(HomeUiEvent.ToggleFilterBottomSheet("filter"))
                                }
                            )
                        }
                        item {
                            FilterButton(
                                text = "Seller Rating",
                                isActive = state.activeFilters.contains(Filter.SELLER_RATING),
                                onClick = {
                                    homeViewModel.onEvent(HomeUiEvent.ChangeSelectedFilter(Filter.SELLER_RATING))
                                    homeViewModel.onEvent(HomeUiEvent.ToggleFilterBottomSheet("filter"))
                                }
                            )
                        }
                        item {
                            FilterButton(
                                text = "Condition",
                                isActive = state.activeFilters.contains(Filter.CONDITION),
                                onClick = {
                                    homeViewModel.onEvent(HomeUiEvent.ChangeSelectedFilter(Filter.CONDITION))
                                    homeViewModel.onEvent(HomeUiEvent.ToggleFilterBottomSheet("filter"))
                                }
                            )
                        }
                    }
                    // Add a divider to visually separate the header
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                    )
                }
            }

            // Explore products section after the sticky header
            item {
//                val wishlistStates by homeViewModel.wishlistStates.collectAsState()

                ExploreProducts(
                    modifier = Modifier,
                    wishlistStates = wishlistStates,
                    exploreProducts = exploreProducts,
                    isLoading = state.isExploreProductsLoading,
                    error = state.exploreProductsError,
                    onProductClick = { product ->
                        productClickHandler.handleProductClick(product)
                    },
                    onRetry = {
                        exploreProducts.refresh()
                    },
                    onLikeClick = { productId ->
                        // Get current wishlist state from the passed wishlistStates
                        val currentProduct = exploreProducts.itemSnapshotList.items.find { it._id == productId }
                        val isWishlisted = if (wishlistStates.containsKey(productId)) {
                            wishlistStates[productId]!!
                        } else {
                            currentProduct?.isWishlisted ?: false
                        }

                        if (isWishlisted) {
                            homeViewModel.onEvent(HomeUiEvent.RemoveFromWishlist(productId))
                        } else {
                            homeViewModel.onEvent(HomeUiEvent.AddToWishlist(productId))
                        }
                    }
                )
            }
        }

    }

    // Add filter bottom sheet
    if (state.isFilterBottomSheetOpen) {
        ModalBottomSheet(
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            onDismissRequest = {
                homeViewModel.onEvent(HomeUiEvent.ToggleFilterBottomSheet(null))
            },
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ) {
            if (state.filterBottomSheetType == "filter") {
                // Show only the selected filter section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = state.selectedFilter.displayValue,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                        IconButton(
                            onClick = { homeViewModel.onEvent(HomeUiEvent.ToggleFilterBottomSheet(null)) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close"
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    when (state.selectedFilter) {
                        Filter.SELLER_RATING -> {
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Rating",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(Modifier.size(8.dp))

                                SellerRatingOption.entries.forEach { sellerRatingOption ->
                                    FilterOptionMultiSelectItem(
                                        filterOption = "${sellerRatingOption.displayValue} +",
                                        isChecked = state.tempSellerRatingOptions.contains(sellerRatingOption),
                                        onCheckedChange = {
                                            homeViewModel.onEvent(HomeUiEvent.ChangeSelectedSellerRating(sellerRatingOption))
                                        }
                                    )
                                    Spacer(Modifier.size(8.dp))
                                }

                                Spacer(Modifier.size(8.dp))

                                Text(
                                    text = "Badge",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(Modifier.size(8.dp))

                                SellerBadge.entries.forEach { sellerBadge ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Checkbox(
                                            modifier = Modifier.size(24.dp),
                                            checked = state.tempSellerBadgeOptions.contains(sellerBadge),
                                            onCheckedChange = {
                                                homeViewModel.onEvent(HomeUiEvent.ChangeSelectedSellerBadge(sellerBadge))
                                            }
                                        )
                                        Spacer(Modifier.size(12.dp))
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                        Spacer(Modifier.size(8.dp))
                                        Text(
                                            text = sellerBadge.displayValue
                                        )
                                    }
                                    Spacer(Modifier.size(8.dp))
                                }
                            }
                        }

                        Filter.PRICE -> {
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Select pricing model and Range",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    lineHeight = 20.sp
                                )
                                Spacer(Modifier.size(12.dp))

                                NewPricingModel.entries.forEach { pricingModel ->
                                    val isChecked = state.tempPricingModelOptions.contains(pricingModel) // Use temp values
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        Checkbox(
                                            modifier = Modifier.size(24.dp),
                                            checked = isChecked,
                                            onCheckedChange = {
                                                homeViewModel.onEvent(HomeUiEvent.ChangeSelectedPricingModel(pricingModel))
                                            }
                                        )
                                        Text(
                                            text = pricingModel.displayValue
                                        )
                                        if (isChecked) {
                                            if (pricingModel == NewPricingModel.CASH) {
                                                Box(
                                                    modifier = Modifier
                                                        .clip(CircleShape)
                                                        .border(1.dp, CashColor2, CircleShape)
                                                        .background(CashColor1.copy(alpha = 0.3f))
                                                        .padding(
                                                            horizontal = 16.dp,
                                                            vertical = 4.dp
                                                        )
                                                ) {
                                                    Text(
                                                        text = if (state.tempSelectedCashRange == MAX_CASH_RANGE)
                                                            "All products" else
                                                            "<₹${state.tempSelectedCashRange?.toInt() ?: 0}",
                                                        fontSize = 15.sp,
                                                        color = MaterialTheme.colorScheme.primary,
                                                        fontWeight = FontWeight.SemiBold
                                                    )
                                                }
                                            } else if (pricingModel == NewPricingModel.COINS) {
                                                Box(
                                                    modifier = Modifier
                                                        .clip(CircleShape)
                                                        .border(1.dp, CoinColor2, CircleShape)
                                                        .background(CoinColor1.copy(alpha = 0.3f))
                                                        .padding(
                                                            horizontal = 10.dp,
                                                            vertical = 4.dp
                                                        )
                                                ) {
                                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                                        Icon(
                                                            modifier = Modifier
                                                                .padding(end = 2.dp)
                                                                .size(20.dp),
                                                            painter = painterResource(id = R.drawable.coin),
                                                            contentDescription = null,
                                                            tint = Color.Unspecified
                                                        )
                                                        Text(
                                                            text = if (state.tempSelectedCoinRange == MAX_COINS_RANGE)
                                                                "All"
                                                            else "<${state.tempSelectedCoinRange?.toInt() ?: 0}",
                                                            fontSize = 15.sp,
                                                            color = MaterialTheme.colorScheme.primary,
                                                            fontWeight = FontWeight.SemiBold
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (isChecked) {
                                        if (pricingModel != NewPricingModel.CASH_AND_COINS) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Slider(
                                                    modifier = Modifier.weight(0.75f),
                                                    value = if (pricingModel == NewPricingModel.CASH)
                                                        state.tempSelectedCashRange ?: MIN_CASH_RANGE
                                                    else
                                                        state.tempSelectedCoinRange ?: MIN_COINS_RANGE,
                                                    onValueChange = {
                                                        if (pricingModel == NewPricingModel.CASH) {
                                                            homeViewModel.onEvent(HomeUiEvent.ChangeCashRange(it))
                                                        } else {
                                                            homeViewModel.onEvent(HomeUiEvent.ChangeCoinRange(it))
                                                        }
                                                    },
                                                    valueRange = if (pricingModel == NewPricingModel.CASH)
                                                        MIN_CASH_RANGE..MAX_CASH_RANGE
                                                    else
                                                        MIN_COINS_RANGE..MAX_COINS_RANGE,
                                                )
                                            }
                                        } else {
                                            Text(
                                                modifier = Modifier.padding(start = 36.dp),
                                                text = "This option doesn't come with range",
                                                fontSize = 14.sp,
                                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f),
                                                lineHeight = 18.sp
                                            )
                                        }
                                    }
                                    Spacer(Modifier.size(8.dp))
                                }
                            }
                        }

                        Filter.CONDITION -> {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                ConditionOption.entries.forEach { conditionOption ->
                                    FilterOptionMultiSelectItem(
                                        filterOption = conditionOption.displayValue,
                                        isChecked = state.tempConditionOptions.contains(conditionOption),
                                        onCheckedChange = {
                                            homeViewModel.onEvent(HomeUiEvent.ChangeSelectedCondition(conditionOption))
                                        }
                                    )
                                }
                            }
                        }

                        else -> {
                            // Other filters
                            Text("This filter is not yet implemented")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedButton(
                            onClick = {
                                homeViewModel.onEvent(HomeUiEvent.ClearFilters)
                            },
                            modifier = Modifier.weight(1f),
                            shape = ButtonShape
                        ) {
                            Text("Clear")
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = {
                                homeViewModel.onEvent(HomeUiEvent.ApplyFilters)
                            },
                            modifier = Modifier.weight(1f),
                            shape = ButtonShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.tertiary,
                                contentColor = MaterialTheme.colorScheme.onTertiary
                            )
                        ) {
                            Text("Apply")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            } else if (state.filterBottomSheetType == "sort") {
                // Sort bottom sheet content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Sort By",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Sort options
                    val sortOptions = listOf(
                        "relevance" to "Relevance",
                        "price_low_to_high" to "Price: Low to High",
                        "price_high_to_low" to "Price: High to Low",
                        "newest_first" to "Newest First"
                    )

                    sortOptions.forEach { (value, display) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    homeViewModel.onEvent(HomeUiEvent.ChangeSortOption(value))
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = state.tempSortOption == value,
                                onClick = {
                                    homeViewModel.onEvent(HomeUiEvent.ChangeSortOption(value))
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = display)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            homeViewModel.onEvent(HomeUiEvent.ApplySortOption)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = ButtonShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.onTertiary
                        )
                    ) {
                        Text("Apply")
                    }
                }
            }
        }
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(0.3f)),
            contentAlignment = Alignment.Center
        ) {
            PleaseWaitLoading()
        }
    }
}

@Composable
fun FilterButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    isActive: Boolean = false,
) {
    val backgroundColor = if (isActive) {
        MaterialTheme.colorScheme.secondaryContainer
    } else {
        Color.Transparent
    }

    val borderColor = if (isActive) {
        MaterialTheme.colorScheme.primary
    } else {
        Color.Black.copy(alpha = 0.3f)
    }

    val textColor = if (isActive) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .border(1.dp, borderColor, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.SemiBold,
                color = textColor
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = textColor
            )
        }
    }
}


@Composable
fun ExploreProducts(
    modifier: Modifier = Modifier,
    exploreProducts: LazyPagingItems<ProductCard>,
    isLoading: Boolean,
    error: String,
    wishlistStates: Map<String, Boolean>,
    onProductClick: (ProductCard) -> Unit,
    onRetry: () -> Unit,
    onLikeClick: (String) -> Unit,
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            error.isNotEmpty() -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Error loading products",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = onRetry) {
                        Text("Retry")
                    }
                }
            }

            exploreProducts.itemCount == 0 -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No products found",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            else -> {
                LazyVerticalStaggeredGrid (
                    columns = StaggeredGridCells.Fixed(2),
                    contentPadding = PaddingValues(
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 16.dp,
                        top = 8.dp
                    ),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalItemSpacing = 8.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = Short.MAX_VALUE.toInt().dp)
                ) {

                    items(exploreProducts.itemCount) { index ->
                        exploreProducts[index]?.let { product ->

                            val isWishlisted = wishlistStates[product._id] ?: product.isWishlisted
                            ProductCard(
                                brand = product.brand,
                                title = product.title,
                                size = "null",
                                productThumbnail = if (product.images.isNotEmpty()) product.images[0] else null,
                                cashPrice = if (product.price.cashPrice != null) product.price.cashPrice.toInt()
                                    .toString() else null,
                                coinsPrice = if (product.price.coinPrice != null) product.price.coinPrice.toInt()
                                    .toString() else null,
                                combinedPrice = if (product.price.mixPrice != null)
                                    Pair(
                                        product.price.mixPrice.enteredCash.toInt().toString(),
                                        product.price.mixPrice.enteredCoin.toInt().toString()
                                    )
                                else null,
                                mrp = product.price.mrp?.toInt().toString(),
                                badge = "null",
                                isLiked = isWishlisted,
                                onLikeClick = {
                                    onLikeClick(product._id)
                                },
                                user = product.seller,
                                onClick = { onProductClick(product) }
                            )
                        }
                    }
                }
            }
        }
    }
}
