package com.example.freeupcopy.ui.presentation.product_listing

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.freeupcopy.R
import com.example.freeupcopy.common.Constants.MAX_CASH_RANGE
import com.example.freeupcopy.common.Constants.MAX_COINS_RANGE
import com.example.freeupcopy.ui.presentation.home_screen.componants.SearchBar
import com.example.freeupcopy.ui.presentation.product_card.ProductCard
import com.example.freeupcopy.ui.presentation.product_listing.componants.FiltersBottomSheet
import com.example.freeupcopy.ui.presentation.product_listing.componants.SelectedOptionsRow
import com.example.freeupcopy.ui.theme.BottomSheetShape
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.SwapGoTheme
import com.example.freeupcopy.ui.theme.TextFieldContainerColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListing(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    productListingViewModel: ProductListingViewModel = hiltViewModel()
) {
    val state by productListingViewModel.state.collectAsState()
    val lifeCycleOwner = LocalLifecycleOwner.current

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
                            value = "",
                            isFocused = remember {
                                mutableStateOf(false)
                            },
                            onFocusChange = {},
                            onValueChange = {},
                            onSearch = { },
                            onCancel = { },
                            modifier = Modifier
                                .fillMaxWidth(),
                            containerColor = TextFieldContainerColor,
                        )
//                        Box(
//                            modifier = Modifier
//                                .wrapContentSize(Alignment.TopStart)
//                        ) {
//                            val expanded = remember { mutableStateOf(false) }
//                            val selectedOption = remember { mutableStateOf("Products") } // Default text
//                            Button(
//                                onClick = { expanded.value = true },
//                                shape = RoundedCornerShape(8.dp),
//                                modifier = Modifier
//                            ) {
//                                Text(text = selectedOption.value)
//                            }
//
//                            // Dropdown Menu
//                            DropdownMenu(
//                                expanded = expanded.value,
//                                onDismissRequest = { expanded.value = false }
//                            ) {
//                                DropdownMenuItem(
//                                    text = { Text("Products") },
//                                    onClick = {
//                                        selectedOption.value = "Products"
//                                        expanded.value = false
//                                    }
//                                )
//                                DropdownMenuItem(
//                                    text = { Text("Sellers") },
//                                    onClick = {
//                                        selectedOption.value = "Sellers"
//                                        expanded.value = false
//                                    }
//                                )
//                            }
//                        }

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
            PrimaryTabRow (
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
                            contentDescription = "filter icon",
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

//            Row(
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Row(
//                    modifier = Modifier
//                        .weight(0.70f)
//                        .heightIn(min = 50.dp)
//                        .fillMaxWidth()
//                        .clip(ButtonShape)
//                        .clickable {
//                            productListingViewModel.openBottomSheet()
//                            productListingViewModel.onClickFilter()
//                        }
//                        .border(
//                            1.dp,
//                            MaterialTheme.colorScheme.onPrimaryContainer.copy(0.3f),
//                            ButtonShape
//                        ),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Center
//                ) {
//                    Text(
//                        text = "Filter",
//                        color = MaterialTheme.colorScheme.onPrimaryContainer,
//                        fontSize = 20.sp
//                    )
//                    Spacer(modifier = Modifier.size(16.dp))
//                    Icon(
//                        imageVector = Icons.Rounded.List,
//                        contentDescription = "filter icon",
//                        modifier = Modifier.size(22.dp)
//                    )
//                }
//                Spacer(modifier = Modifier.size(8.dp))
//                Row(
//                    modifier = Modifier
//                        .weight(0.70f)
//                        .heightIn(min = 50.dp)
//                        .fillMaxWidth()
//                        .clip(ButtonShape)
//                        .border(
//                            if (state.isSortApplied) 2.dp else 1.dp,
//                            MaterialTheme.colorScheme.onPrimaryContainer.copy(0.3f),
//                            ButtonShape
//                        )
//                        .clickable { /* No-op for now */ },
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Center
//                ) {
//
//
//                    // Sort Button
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .clickable {
//                                productListingViewModel.openBottomSheet()
//                                productListingViewModel.onSortClick()
//                            },
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.Center
//                    ) {
//                        Text(
//                            text = "Sort",
//                            color = MaterialTheme.colorScheme.onPrimaryContainer,
//                            fontSize = 20.sp,
//                            modifier = Modifier.padding(8.dp)
//                        )
//
//                    }
//
//                }
//
//            }
            SelectedOptionsRow(
                scrollBehavior = scrollBehavior,
                onOptionClicked = { option ->
                    productListingViewModel.onEvent(ProductListingUiEvent.SpecialOptionSelectedChange(option))
                },
                selectedFilterSpecialOptions = state.selectedSpecialOptions
            )
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.12f)
            )
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2), // 2 items per row
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalItemSpacing = 12.dp,
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
            ) {
                items(10) { index ->
                    ProductCard(
                        brand = listOf(
                            "Adidas", "Nike", "Puma", null, "Under Armour",
                            "Levi's", "Calvin Klein", "Tommy Hilfiger", "Lacoste", null
                        )[index],
                        title = listOf(
                            "Just Herbs Mini Kit limited edition",
                            "Nike Air Max",
                            "Puma Suede",
                            "Reebok Classic",
                            "Under Armour Hoodie",
                            "Levi's Jeans",
                            "Calvin Klein T-shirt",
                            "Tommy Hilfiger Polo",
                            "Lacoste L.12.12",
                            "Ralph Lauren Shirt"
                        )[index],
                        size = listOf(
                            "40 inches", "L", "M", "XL", "L",
                            "32 inches", "S", "M", "XL", "L"
                        )[index],
                        productThumbnail = painterResource(id = R.drawable.bomber_jacket), // Assuming R.drawable.bomber_jacket is a placeholder
                        cashPrice = listOf(
                            null, "499", "399", null, "799",
                            "1999", null, "1499", "1299", null
                        )[index],
                        coinsPrice = listOf(
                            null, "1000", null, "599", null,
                            null, "999", null, null, "1799"
                        )[index],
                        combinedPrice = listOf(
                            Pair("4000", "2000"), Pair("300", "800"), null, null, null,
                            null, null, Pair("1000", "500"), null, null
                        )[index],
                        mrp = listOf(
                            "3500", "2999", "1999", "1499", "1999",
                            "3999", "1999", "2999", "2499", "3499"
                        )[index],
                        badge = listOf(
                            "Trusted", "Sale", "New", null, "Limited Edition",
                            "Classic", "Trendy", "Luxury", "Iconic", null
                        )[index],
                        isLiked = false,
                        onLikeClick = {}
                    )
                }
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
            windowInsets = WindowInsets(0.dp),
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
                                productListingViewModel.onEvent(ProductListingUiEvent.ChangeCashRange(it))
                            },
                            selectedCoinRange = state.selectedCoinRange ?: MAX_COINS_RANGE,
                            onCoinsRangeChange = {
                                productListingViewModel.onEvent(ProductListingUiEvent.ChangeCoinRange(it))
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
                                productListingViewModel.onEvent(ProductListingUiEvent.ToggleSelectAll(it))
                            },
                            availableFilters = state.availableFilters
                        )
                    }

                    state.isSortBottomSheet -> {
                        Box(
                            Modifier.size(16.dp)
                        )
                    }
                }
            }

        }
//        BottomPopup(
//            isAvailablitySelected = state.isAvailablitySelected,
//            onAvailablitySelected = { productListingViewModel.onClickAvailablity() },
//            isAvailableSelected = state.isAvailableSelected,
//            onAvailableSelected = { productListingViewModel.onClickAvailable() },
//            isSoldOutSelected = state.isSoldOutSelected,
//            onSoldOutSelected = { productListingViewModel.onClickSoldOut() },
//            isConditionSelected = state.isConditionSelected,
//            onConditionSelected = { productListingViewModel.onClickCondition() },
//            isNewWithTagsSelected = state.isNewWithTagsSelected,
//            onNewWithTagsSelected = { productListingViewModel.onClickNewWithTags() },
//            isLikeNewSelected = state.isLikeNewSelected,
//            onLikeNewSelected = { productListingViewModel.onClickLikeNew() },
//            isGoodSelected = state.isGoodSelected,
//            onGoodSelected = { productListingViewModel.onClickGood() },
//            isUsedSelected = state.isUsedSelected,
//            onUsedSelected = { productListingViewModel.onClickUsed() },
//            isSellerRatingSelected = state.isSellerRatingSelected,
//            onSellerRatingSelected = { productListingViewModel.onClickSellerRating() },
//            isRating4_0Selected = state.isRating4_0Selected,
//            onRating4_0Selected = { productListingViewModel.onClickRating4_0() },
//            isRating4_5Selected = state.isRating4_5Selected,
//            onRating4_5Selected = { productListingViewModel.onClickRating4_5() },
//            isRating4_7Selected = state.isRating4_7Selected,
//            onRating4_7Selected = { productListingViewModel.onClickRating4_7() },
//            isPriceSelected = state.isPriceSelected,
//            onPriceSelected = { productListingViewModel.onClickPrice() },
//            isSellerActiveSelected = state.isSellerActiveSelected,
//            onSellerActiveSelected = { productListingViewModel.onClickSellerActive() },
//            isSellerActiveThisWeekSelected = state.isSellerActiveThisWeekSelected,
//            onSellerActiveThisWeekSelected = { productListingViewModel.onClickSellerActiveThisWeek() },
//            isSellerActiveThisMonthSelected = state.isSellerActiveThisMonthSelected,
//            onSellerActiveThisMonthSelected = { productListingViewModel.onClickSellerActiveThisMonth() },
//            isCategorySelected = state.isCategorySelected,
//            onCategorySelected = { productListingViewModel.onClickCategory() },
//            isSizeSelected = state.isSizeSelected,
//            onSizeSelected = { productListingViewModel.onClickSize() },
//            cashSelected = state.cashSelected,
//            onCashChange = { productListingViewModel.onCashChange(it) },
//            coinsSelected = state.coinsSelected,
//            onCoinsChange = { productListingViewModel.onCoinsChange(it) },
//            isOfferSelected = state.isOfferSelected,
//            onOfferSelect = { productListingViewModel.onOfferSelect(it) },
//            onDismissListener = { productListingViewModel.closeBottomSheet() },
//            filterSectionSelected = state.filterSectionOpen,
//            onClickCash = {productListingViewModel.onClickCash()},
//            onClickCoin = {productListingViewModel.onClickCoin()},
//            isCashSelected = state.isCashSelected,
//            isCoinSelected = state.isCoinSelected,
//            isFilterSelected = state.isFilterSelected,
//            isSortSelected = state.isSortSelected,
//            sortOption = state.sortingOption,
//            changeSortToRec = {productListingViewModel.changeSortToRec()},
//            changeSortToPriceLoToHi = {productListingViewModel.changeSortToPriceLoToHi()},
//            changeSortToPriceHiToLo = {productListingViewModel.changeSortToPriceHiToLo()},
//            selectedFilter = null
//        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewProductListing() {
    SwapGoTheme {
        ProductListing(
            onBack = { }
        )
    }
}