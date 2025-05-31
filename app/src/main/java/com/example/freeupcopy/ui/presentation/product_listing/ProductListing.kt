package com.example.freeupcopy.ui.presentation.product_listing

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.freeupcopy.R
import com.example.freeupcopy.common.Constants.MAX_CASH_RANGE
import com.example.freeupcopy.common.Constants.MAX_COINS_RANGE
import com.example.freeupcopy.ui.presentation.common.rememberProductClickHandler
import com.example.freeupcopy.ui.presentation.home_screen.componants.SearchBar
import com.example.freeupcopy.ui.presentation.product_card.ProductCard
import com.example.freeupcopy.ui.presentation.product_listing.componants.FiltersBottomSheet
import com.example.freeupcopy.ui.presentation.product_listing.componants.SelectedOptionsRow
import com.example.freeupcopy.ui.presentation.product_listing.componants.SortBottomSheet
import com.example.freeupcopy.ui.presentation.sell_screen.location_screen.location_screen.PleaseWaitLoading
import com.example.freeupcopy.ui.theme.BottomSheetShape
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.SwapGoTheme
import com.example.freeupcopy.ui.theme.TextFieldContainerColor
import java.net.UnknownHostException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListing(
    modifier: Modifier = Modifier,
    query: String,
    onBack: () -> Unit,
    onProductClick: (String) -> Unit,
    productListingViewModel: ProductListingViewModel = hiltViewModel()
) {
    val state by productListingViewModel.state.collectAsState()
    val wishlistStates by productListingViewModel.wishlistStates.collectAsState()
    val lifeCycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val products = productListingViewModel.productCards.collectAsLazyPagingItems()

    // Create the product click handler
    val productClickHandler = rememberProductClickHandler(
        productListingViewModel = productListingViewModel,
        onProductClick = onProductClick,
        onLoadingStateChange = { isLoading ->
            productListingViewModel.onEvent(ProductListingUiEvent.IsLoading(isLoading))
        }
    )

    LaunchedEffect(Unit) {
        if (state.initialQuerySet.not() && query.isNotEmpty()) {
            productListingViewModel.onEvent(ProductListingUiEvent.ChangeSearchQuery(query))
            // Mark that we've set the initial query
            productListingViewModel.onEvent(ProductListingUiEvent.SetInitialQuery)
        }
    }
    LaunchedEffect(state.error) {
        Log.e("ProductListingScreen", "Error: ${state.error}")
        state.error.takeIf { it.isNotBlank() }?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        SearchBar(
                            value = state.searchQuery,
                            isFocused = remember {
                                mutableStateOf(false)
                            },
                            onFocusChange = {},
                            onValueChange = {
                                productListingViewModel.onEvent(ProductListingUiEvent.ChangeSearchQuery(it))
                            },
                            onSearch = { },
                            onCancel = {
                                productListingViewModel.onEvent(ProductListingUiEvent.ChangeSearchQuery(""))
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            containerColor = TextFieldContainerColor,
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            onBack()
                        }
                    }) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                            contentDescription = "back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            PrimaryTabRow(
                modifier = Modifier.fillMaxWidth(),
                selectedTabIndex = selectedTabIndex,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = {
                        selectedTabIndex = 0
                    },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f),
                    text = {
                        Text(
                            text = "Products",
                            fontSize = 16.sp
                        )
                    }
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = {
                        selectedTabIndex = 1
                    },
                    text = {
                        Text(
                            text = "Sellers",
                            fontSize = 16.sp
                        )
                    },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(start = 8.dp, end = 8.dp, bottom = 6.dp, top = 12.dp),
            ) {
                OutlinedButton(
                    modifier = Modifier
                        .weight(1f),
                    onClick = {
                        productListingViewModel.onEvent(ProductListingUiEvent.ToggleBottomSheet("filter"))
                    },
                    shape = ButtonShape,
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.onPrimaryContainer.copy(0.3f)
                    )
                ) {
                    Row(
                        modifier = Modifier.height(32.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_filter),
                            contentDescription = "filter icon",
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = "Filter",
                            fontSize = 16.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.size(8.dp))

                OutlinedButton(
                    modifier = Modifier
                        .weight(1f),
                    onClick = {
                        productListingViewModel.onEvent(ProductListingUiEvent.ToggleBottomSheet("sort"))
                    },
                    shape = ButtonShape,
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.onPrimaryContainer.copy(0.3f)
                    )
                ) {
                    Row(
                        modifier = Modifier.height(32.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_sort),
                            contentDescription = "sort icon",
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = "Sort",
                            fontSize = 16.sp
                        )
                    }
                }
            }

            SelectedOptionsRow(
                scrollBehavior = scrollBehavior,
                onOptionClicked = { option ->
                    productListingViewModel.onEvent(
                        ProductListingUiEvent.SpecialOptionSelectedChange(
                            option
                        )
                    )
                },
                selectedFilterSpecialOptions = state.selectedSpecialOptions
            )
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.12f)
            )
            Box(modifier = Modifier.weight(1f)) {
                if (products.itemCount == 0 && products.loadState.refresh is LoadState.NotLoading) {
                    // Instead of fillMaxSize(), use fillMaxWidth()
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                            .background(MaterialTheme.colorScheme.surface),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.im_no_result_found),
                                contentDescription = null
                            )
                            Text(
                                text = "No products found",
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(Modifier.size(6.dp))
                            Text(
                                text = "Try changing filters or your search query.",
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                } else {
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surface),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalItemSpacing = 12.dp,
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
                    ) {
                        items(products.itemCount) {
                            products[it]?.let { product ->

                                val isWishlisted = if (wishlistStates.containsKey(product._id)) {
                                    wishlistStates[product._id]!!
                                } else {
                                    product.isWishlisted
                                }

                                ProductCard(
                                    brand = product.brand,
                                    title = product.title,
                                    size = "null",
//                            size = product.size,
                                    productThumbnail = if (product.images.size == 1) product.images[0] else null,
                                    cashPrice = if (product.price.cashPrice != null) product.price.cashPrice.toInt()
                                        .toString() else null,
                                    coinsPrice = if (product.price.coinPrice != null) product.price.coinPrice.toInt()
                                        .toString() else null,
                                    combinedPrice =
                                        if (product.price.mixPrice != null)
                                            Pair(
                                                product.price.mixPrice.enteredCash.toInt()
                                                    .toString(),
                                                product.price.mixPrice.enteredCoin.toInt()
                                                    .toString()
                                            )
                                        else
                                            null,
                                    mrp = product.price.mrp?.toInt().toString(),
                                    badge = "null",
                                    user = product.seller,
                                    isLiked = isWishlisted,
                                    onLikeClick = {
                                        // Call the appropriate event based on current state
                                        if (isWishlisted) {
                                            productListingViewModel.onEvent(
                                                ProductListingUiEvent.RemoveFromWishlist(product._id)
                                            )
                                        } else {
                                            productListingViewModel.onEvent(
                                                ProductListingUiEvent.AddToWishlist(product._id)
                                            )
                                        }
                                    },
                                    onClick = {
                                        productClickHandler.handleProductClick(product)
                                    }
                                )
                            }
                        }
                        products.apply {
                            if (loadState.append is LoadState.Loading) {
                                item(span = StaggeredGridItemSpan.FullLine) {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier.padding(vertical = 16.dp)
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }
                            }
                            if (loadState.append == LoadState.NotLoading(endOfPaginationReached = true) &&
                                products.itemCount != 0 && products.itemCount > 15
                            ) {
                                item(span = StaggeredGridItemSpan.FullLine) {
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 16.dp),
                                        text = "No more products to load",
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            if (loadState.append is LoadState.Error) {
                                var message = ""
                                val e = (loadState.append as LoadState.Error).error
                                if (e is UnknownHostException) {
                                    message = "No internet.\nCheck your connection"
                                } else if (e is Exception) {
                                    message = e.message ?: "Unknown error occurred"
                                }
                                item(span = StaggeredGridItemSpan.FullLine) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 8.dp, vertical = 8.dp),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(24.dp),
                                            painter = painterResource(R.drawable.ic_error),
                                            contentDescription = null,
                                            tint = Color.Unspecified
                                        )
                                        Spacer(Modifier.size(16.dp))
                                        Text(
                                            text = "Error: $message",
                                            modifier = Modifier.weight(1f),
                                            softWrap = true,  // Ensures text wraps to next line when needed
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Button(
                                            modifier = Modifier.padding(start = 16.dp),
                                            onClick = { products.retry() },
                                            shape = ButtonShape,
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = MaterialTheme.colorScheme.tertiary,
                                                contentColor = MaterialTheme.colorScheme.onTertiary
                                            )
                                        ) {
                                            Text("Retry")
                                        }
                                    }
                                }

                            }

                        }


                    }

                }
            }
        }
        products.apply {

            var message = ""
            if (loadState.refresh is LoadState.Error) {
                val e = (loadState.refresh as LoadState.Error).error
                if (e is UnknownHostException) {
                    message = "No internet.\nCheck your connection"
                } else if (e is Exception) {
                    message = e.message ?: "Unknown error occurred"
                }
            }

            when (loadState.refresh) {

                is LoadState.Error -> {
                    if (products.itemCount == 0) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.surface)
                        ) {
                            Column(
                                modifier = Modifier.align(Alignment.Center),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.im_error),
                                    contentDescription = null
                                )
                                Text(
                                    text = message.ifEmpty { "Unknown error occurred" },
                                    fontSize = 15.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.W500
                                )
                                Button(
                                    onClick = { products.retry() },
                                    shape = ButtonShape,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.tertiary,
                                        contentColor = MaterialTheme.colorScheme.onTertiary
                                    )
                                ) {
                                    Text("Retry")
                                }
                            }
                        }
                    }
                }

                LoadState.Loading -> {
                    Box(Modifier.fillMaxSize()) {
                       PleaseWaitLoading(Modifier.align(Alignment.Center))
                    }
                }

                else -> {}
            }
        }
    }
    if (state.isBottomSheetOpen) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                productListingViewModel.onEvent(ProductListingUiEvent.BottomSheetDismiss)
            },
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            shape = BottomSheetShape,
            dragHandle = null
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(top = 16.dp)
            ) {
                when {
                    state.isFilterBottomSheet -> {

                        FiltersBottomSheet(
                            selectedFilter = state.selectedFilter,
                            onClearFilters = {
                                productListingViewModel.onEvent(ProductListingUiEvent.ClearFilters)
                            },
                            onSelectedFilterClick = {
                                productListingViewModel.onEvent(
                                    ProductListingUiEvent.ChangeSelectedFilter(
                                        it
                                    )
                                )
                            },
                            onAvailabilityOptionClick = {
                                productListingViewModel.onEvent(
                                    ProductListingUiEvent.ChangeSelectedAvailability(
                                        it
                                    )
                                )
                            },
                            availabilityOptions = state.availabilityOptions,
                            onConditionOptionClick = {
                                productListingViewModel.onEvent(
                                    ProductListingUiEvent.ChangeSelectedCondition(
                                        it
                                    )
                                )
                            },
                            conditionOptions = state.conditionOptions,
                            onSellerRatingOptionClick = {
                                productListingViewModel.onEvent(
                                    ProductListingUiEvent.ChangeSelectedSellerRating(
                                        it
                                    )
                                )
                            },
                            sellerRatingOptions = state.sellerRatingOptions,
                            onSellerBadgeClick = {
                                productListingViewModel.onEvent(
                                    ProductListingUiEvent.ChangeSelectedSellerBadge(
                                        it
                                    )
                                )
                            },
                            sellerBadgeOptions = state.sellerBadgeOptions,
                            onPricingModelClick = {
                                productListingViewModel.onEvent(
                                    ProductListingUiEvent.ChangeSelectedPricingModel(
                                        it
                                    )
                                )
                            },
                            pricingModelOptions = state.pricingModelOptions,
                            selectedCashRange = state.selectedCashRange ?: MAX_CASH_RANGE,
                            onCashRangeChange = {
                                productListingViewModel.onEvent(
                                    ProductListingUiEvent.ChangeCashRange(
                                        it
                                    )
                                )
                            },
                            selectedCoinRange = state.selectedCoinRange ?: MAX_COINS_RANGE,
                            onCoinsRangeChange = {
                                productListingViewModel.onEvent(
                                    ProductListingUiEvent.ChangeCoinRange(
                                        it
                                    )
                                )
                            },
                            onTertiaryCategoryClick = {
                                productListingViewModel.onEvent(
                                    ProductListingUiEvent.ChangeSelectedTertiaryCategory(
                                        it
                                    )
                                )
                            },
                            selectedTertiaryCategories = state.selectedTertiaryCategory,
                            onRemoveSpecialOption = {
                                productListingViewModel.onEvent(
                                    ProductListingUiEvent.RemoveSpecialOptions(
                                        it
                                    )
                                )
                            },
                            onSelectAll = {
                                productListingViewModel.onEvent(
                                    ProductListingUiEvent.ToggleSelectAll(
                                        it
                                    )
                                )
                            },
                            availableFilters = state.availableFilters,
                            onApplyClick = {
                                productListingViewModel.onEvent(ProductListingUiEvent.ToggleBottomSheet("filter"))
                            }
                        )
                    }

                    state.isSortBottomSheet -> {
                        SortBottomSheet(
                            tempSortOption = state.tempSortOption ?: "default",
                            onSortOptionSelected = { selectedOption ->
                                productListingViewModel.onEvent(ProductListingUiEvent.ChangeSortOption(selectedOption))
                            },
                            onApply = {
                                productListingViewModel.onEvent(ProductListingUiEvent.ApplySortOption)
                            },
                            onDismiss = {
                                productListingViewModel.onEvent(ProductListingUiEvent.BottomSheetDismiss)
                            }
                        )
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


@Preview(showBackground = true)
@Composable
fun PreviewProductListing() {
    SwapGoTheme {
        ProductListing(
            query = "",
            onBack = { },
            onProductClick = {}
        )
    }
}