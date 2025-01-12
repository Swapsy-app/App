package com.example.freeupcopy.ui.presentation.product_listing

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.presentation.home_screen.componants.SearchBar
import com.example.freeupcopy.ui.presentation.product_card.ProductCard
import com.example.freeupcopy.ui.presentation.product_listing.componants.BottomPopup
import com.example.freeupcopy.ui.presentation.product_listing.componants.SelectedOptionsRow
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.SwapGoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListing(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    productListingViewModel: ProductListingViewModel = viewModel()
) {
    val state by productListingViewModel.state.collectAsState()
    val lifeCycleOwner = LocalLifecycleOwner.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        SearchBar(
                            value = "Searched P..",
                            isFocused = remember {
                                mutableStateOf(false)
                            },
                            onFocusChange = {},
                            onValueChange = {},
                            onSearch = { },
                            onCancel = { },
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .fillMaxWidth(0.65f)
                        )
                        Box(
                            modifier = Modifier
                                .wrapContentSize(Alignment.TopStart)
                        ) {
                            val expanded = remember { mutableStateOf(false) }
                            val selectedOption = remember { mutableStateOf("Products") } // Default text
                            Button(
                                onClick = { expanded.value = true },
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                            ) {
                                Text(text = selectedOption.value)
                            }

                            // Dropdown Menu
                            DropdownMenu(
                                expanded = expanded.value,
                                onDismissRequest = { expanded.value = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Products") },
                                    onClick = {
                                        selectedOption.value = "Products"
                                        expanded.value = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Sellers") },
                                    onClick = {
                                        selectedOption.value = "Sellers"
                                        expanded.value = false
                                    }
                                )
                            }
                        }

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
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Spacer(modifier = Modifier.size(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .weight(0.70f)
                        .heightIn(min = 50.dp)
                        .fillMaxWidth()
                        .clip(ButtonShape)
                        .clickable {
                            productListingViewModel.openBottomSheet()
                            productListingViewModel.onClickFilter()
                        }
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onPrimaryContainer.copy(0.3f),
                            ButtonShape
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Filter",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Icon(
                        imageVector = Icons.Rounded.List,
                        contentDescription = "filter icon",
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.size(8.dp))
                Row(
                    modifier = Modifier
                        .weight(0.70f)
                        .heightIn(min = 50.dp)
                        .fillMaxWidth()
                        .clip(ButtonShape)
                        .border(
                            if (state.isSortApplied)2.dp else 1.dp,
                            MaterialTheme.colorScheme.onPrimaryContainer.copy(0.3f),
                            ButtonShape
                        )
                        .clickable { /* No-op for now */ },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {


                    // Sort Button
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                productListingViewModel.openBottomSheet()
                                productListingViewModel.onSortClick()
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Sort",
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(8.dp)
                        )

                    }

                }

            }
            Spacer(modifier = Modifier.height(16.dp))
            SelectedOptionsRow(
                uiState = state,
                onOptionClicked = { option -> productListingViewModel.toggleOption(option) },
                isOptionSelected = {option -> productListingViewModel.checkOptionRowState(option)}
            )
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2), // 2 items per row
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                items(10) { index ->
                    ProductCard(
                        containerColor = MaterialTheme.colorScheme.background,
                        companyName = listOf(
                            "Adidas", "Nike", "Puma", null, "Under Armour",
                            "Levi's", "Calvin Klein", "Tommy Hilfiger", "Lacoste", null
                        )[index],
                        productName = listOf(
                            "Adidas Bomber Jacket", "Nike Air Max", "Puma Suede", "Reebok Classic", "Under Armour Hoodie",
                            "Levi's Jeans", "Calvin Klein T-shirt", "Tommy Hilfiger Polo", "Lacoste L.12.12", "Ralph Lauren Shirt"
                        )[index],
                        size = listOf(
                            "40 inches", "L", "M", "XL", "L",
                            "32 inches", "S", "M", "XL", "L"
                        )[index],
                        productThumbnail = painterResource(id = R.drawable.bomber_jacket), // Assuming R.drawable.bomber_jacket is a placeholder
                        priceOffered = listOf(
                            null, "499", "399", null, "799",
                            "1999", null, "1499", "1299", null
                        )[index],
                        coinsOffered = listOf(
                            null, "1000", null, "599", null,
                            null, "999", null, null, "1799"
                        )[index],
                        specialOffer = listOf(
                            listOf("4000", "2000"), listOf("300","800"), null, null, null,
                            null, null, listOf("1000", "500"), null, null
                        )[index],
                        priceOriginal = listOf(
                            "3500", "2999", "1999", "1499", "1999",
                            "3999", "1999", "2999", "2499", "3499"
                        )[index],
                        badge = listOf(
                            "Trusted", "Sale", "New", null, "Limited Edition",
                            "Classic", "Trendy", "Luxury", "Iconic", null
                        )[index],
                        isLiked = false,
                        onLikeClick = {},
                        priorityPriceType = null, // Set priorityPriceType to null for all products
                        isClothes = listOf(
                            true, false, true, false, true,
                            true, true, true, false, true
                        )[index]
                    )
                }
            }
        }
    }
    if(state.isBottomSheetOpen){
        BottomPopup(
            isAvailablitySelected = state.isAvailablitySelected,
            onAvailablitySelected = { productListingViewModel.onClickAvailablity() },
            isAvailableSelected = state.isAvailableSelected,
            onAvailableSelected = { productListingViewModel.onClickAvailable() },
            isSoldOutSelected = state.isSoldOutSelected,
            onSoldOutSelected = { productListingViewModel.onClickSoldOut() },
            isConditionSelected = state.isConditionSelected,
            onConditionSelected = { productListingViewModel.onClickCondition() },
            isNewWithTagsSelected = state.isNewWithTagsSelected,
            onNewWithTagsSelected = { productListingViewModel.onClickNewWithTags() },
            isLikeNewSelected = state.isLikeNewSelected,
            onLikeNewSelected = { productListingViewModel.onClickLikeNew() },
            isGoodSelected = state.isGoodSelected,
            onGoodSelected = { productListingViewModel.onClickGood() },
            isUsedSelected = state.isUsedSelected,
            onUsedSelected = { productListingViewModel.onClickUsed() },
            isSellerRatingSelected = state.isSellerRatingSelected,
            onSellerRatingSelected = { productListingViewModel.onClickSellerRating() },
            isRating4_0Selected = state.isRating4_0Selected,
            onRating4_0Selected = { productListingViewModel.onClickRating4_0() },
            isRating4_5Selected = state.isRating4_5Selected,
            onRating4_5Selected = { productListingViewModel.onClickRating4_5() },
            isRating4_7Selected = state.isRating4_7Selected,
            onRating4_7Selected = { productListingViewModel.onClickRating4_7() },
            isPriceSelected = state.isPriceSelected,
            onPriceSelected = { productListingViewModel.onClickPrice() },
            isSellerActiveSelected = state.isSellerActiveSelected,
            onSellerActiveSelected = { productListingViewModel.onClickSellerActive() },
            isSellerActiveThisWeekSelected = state.isSellerActiveThisWeekSelected,
            onSellerActiveThisWeekSelected = { productListingViewModel.onClickSellerActiveThisWeek() },
            isSellerActiveThisMonthSelected = state.isSellerActiveThisMonthSelected,
            onSellerActiveThisMonthSelected = { productListingViewModel.onClickSellerActiveThisMonth() },
            isCategorySelected = state.isCategorySelected,
            onCategorySelected = { productListingViewModel.onClickCategory() },
            isSizeSelected = state.isSizeSelected,
            onSizeSelected = { productListingViewModel.onClickSize() },
            cashSelected = state.cashSelected,
            onCashChange = { productListingViewModel.onCashChange(it) },
            coinsSelected = state.coinsSelected,
            onCoinsChange = { productListingViewModel.onCoinsChange(it) },
            isOfferSelected = state.isOfferSelected,
            onOfferSelect = { productListingViewModel.onOfferSelect(it) },
            onDismissListener = { productListingViewModel.closeBottomSheet() },
            filterSectionSelected = state.filterSectionOpen,
            onClickCash = {productListingViewModel.onClickCash()},
            onClickCoin = {productListingViewModel.onClickCoin()},
            isCashSelected = state.isCashSelected,
            isCoinSelected = state.isCoinSelected,
            isFilterSelected = state.isFilterSelected,
            isSortSelected = state.isSortSelected,
            sortOption = state.sortingOption,
            changeSortToRec = {productListingViewModel.changeSortToRec()},
            changeSortToPriceLoToHi = {productListingViewModel.changeSortToPriceLoToHi()},
            changeSortToPriceHiToLo = {productListingViewModel.changeSortToPriceHiToLo()}
        )
    }
}



@Preview(showBackground = true) @Composable
fun PreviewProductListing(){
    SwapGoTheme {
        ProductListing(
            onBack = { }
        )
    }
}