package com.example.freeupcopy.ui.presentation.wish_list

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.freeupcopy.R
import com.example.freeupcopy.data.remote.dto.sell.getWishlist
import com.example.freeupcopy.domain.enums.AvailabilityOption
import com.example.freeupcopy.domain.enums.ConditionOption
import com.example.freeupcopy.domain.enums.Filter
import com.example.freeupcopy.domain.enums.FilterCategoryUiModel
import com.example.freeupcopy.domain.enums.FilterTertiaryCategory
import com.example.freeupcopy.ui.presentation.common.rememberProductClickHandler
import com.example.freeupcopy.ui.presentation.home_screen.FilterButton
import com.example.freeupcopy.ui.presentation.product_card.ProductCard
import com.example.freeupcopy.ui.presentation.product_listing.ProductListingViewModel
import com.example.freeupcopy.ui.presentation.product_listing.componants.AppliedFilterIndicator
import com.example.freeupcopy.ui.presentation.product_listing.componants.FilterOptionMultiSelectItem
import com.example.freeupcopy.ui.presentation.sell_screen.location_screen.location_screen.PleaseWaitLoading
import com.example.freeupcopy.ui.theme.BottomSheetShape
import com.example.freeupcopy.ui.theme.ButtonShape
import java.net.UnknownHostException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishListScreen(
    modifier: Modifier = Modifier,
    viewModel: WishListViewModel = hiltViewModel(),
    productListingViewModel: ProductListingViewModel = hiltViewModel(),
    onProductClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsState().value
    val wishlistItems = viewModel.wishlistItems.collectAsLazyPagingItems()

    LaunchedEffect(state.error) {
        Log.e("WishListScreen", "Error: ${state.error}")
        state.error.takeIf { it.isNotBlank() }?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(state.onSuccessfulDelete){
        if (state.onSuccessfulDelete) {
            Toast.makeText(context, "Item removed from wishlist", Toast.LENGTH_SHORT).show()
        }
    }

    val productClickHandler = rememberProductClickHandler(
        productListingViewModel = productListingViewModel,
        onProductClick = onProductClick,
        onLoadingStateChange = { isLoading ->
            viewModel.onEvent(WishlistUiEvent.IsLoading(isLoading))
        }
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                title = { Text("My Wishlist", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Sticky header with filters
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column {
                    // Filter options row
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            FilterButton(
                                text = "Availability",
                                isActive = state.activeFilters.contains(Filter.AVAILABILITY),
                                onClick = {
                                    viewModel.onEvent(WishlistUiEvent.ChangeSelectedFilter(Filter.AVAILABILITY))
                                    viewModel.onEvent(WishlistUiEvent.ToggleFilterBottomSheet("filter"))
                                }
                            )
                        }
                        item {
                            FilterButton(
                                text = "Sort",
                                isActive = state.activeFilters.contains(Filter.SORT),
                                onClick = {
                                    viewModel.onEvent(WishlistUiEvent.ToggleFilterBottomSheet("sort"))
                                }
                            )
                        }
                        item {
                            FilterButton(
                                text = "Condition",
                                isActive = state.activeFilters.contains(Filter.CONDITION),
                                onClick = {
                                    viewModel.onEvent(WishlistUiEvent.ChangeSelectedFilter(Filter.CONDITION))
                                    viewModel.onEvent(WishlistUiEvent.ToggleFilterBottomSheet("filter"))
                                }
                            )
                        }
                        item {
                            FilterButton(
                                text = "Category",
                                isActive = state.activeFilters.contains(Filter.CATEGORY),
                                onClick = {
                                    viewModel.onEvent(WishlistUiEvent.ChangeSelectedFilter(Filter.CATEGORY))
                                    viewModel.onEvent(WishlistUiEvent.ToggleFilterBottomSheet("filter"))
                                }
                            )
                        }
                    }

                    // Selected filters display
                    if (state.activeFilters.isNotEmpty()) {
                        SelectedOptionsRowWishList(
                            activeFilters = state.activeFilters,
                            onClearFilter = { filter ->
                                viewModel.onEvent(WishlistUiEvent.ClearSpecificFilter(filter))
                            }
                        )
                    }
                }
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.12f),
                    thickness = 1.dp
                )
            }

            // Content area with handling for different states
            Box(modifier = Modifier.weight(1f)) {
                if (wishlistItems.itemCount == 0 && wishlistItems.loadState.refresh is LoadState.NotLoading) {
                    EmptyWishlistMessage()
                } else {
                    // Display wishlist items when available
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Fixed(2),
                        contentPadding = PaddingValues(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalItemSpacing = 8.dp,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(wishlistItems.itemCount) { index ->
                            wishlistItems[index]?.let { product ->
                                ProductCard(
                                    brand = product.brand,
                                    title = product.title,
                                    size = "null",
                                    productThumbnail = product.image,
                                    cashPrice = if (product.price.cash != null) product.price.cash.enteredAmount?.toInt()
                                        .toString() else null,
                                    coinsPrice = if (product.price.coin != null) product.price.coin.enteredAmount?.toInt()
                                        .toString() else null,
                                    combinedPrice = if (product.price.mix != null)
                                        Pair(
                                            product.price.mix.enteredCash?.toInt().toString(),
                                            product.price.mix.enteredCoin?.toInt().toString()
                                        )
                                    else null,
                                    mrp = product.price.mrp.toInt().toString(),
                                    badge = "null",
                                    isLiked = true,
                                    onLikeClick = {
                                        viewModel.onEvent(WishlistUiEvent.RemoveFromWishlist(product.productId))
                                    },
                                    user = product.seller!!,
                                    onClick = {
                                        productClickHandler.handleProductClick(
                                            getWishlist(product)
                                        )
                                    }
                                )
                            }
                        }

                        // Handle append loading state (loading more items)
                        wishlistItems.apply {
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

                            // End of pagination message
                            if (loadState.append == LoadState.NotLoading(endOfPaginationReached = true) &&
                                wishlistItems.itemCount != 0 && wishlistItems.itemCount > 15
                            ) {
                                item(span = StaggeredGridItemSpan.FullLine) {
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 16.dp),
                                        text = "No more items to load",
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            // Error while loading more items
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
                                            softWrap = true,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Button(
                                            modifier = Modifier.padding(start = 16.dp),
                                            onClick = { wishlistItems.retry() },
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

        // Handle refresh loading and error states
        wishlistItems.apply {
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
                    if (wishlistItems.itemCount == 0) {
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
                                    onClick = { wishlistItems.retry() },
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

                is LoadState.Loading -> {
                    Box(Modifier.fillMaxSize()) {
                        PleaseWaitLoading(Modifier.align(Alignment.Center))
                    }
                }

                else -> {}
            }
        }
    }
    // Filter bottom sheet
    if (state.isFilterBottomSheetOpen) {
        ModalBottomSheet(
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            onDismissRequest = {
                viewModel.onEvent(WishlistUiEvent.ToggleFilterBottomSheet(null))
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
                FilterBottomSheetContent(
                    state = state,
                    viewModel = viewModel
                )
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
private fun EmptyWishlistMessage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.im_no_result_found),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Your wishlist is empty",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Items you like will appear here",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}


@Composable
private fun FilterBottomSheetContent(
    state: WishListUiState,
    viewModel: WishListViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Filter content based on selected filter type
        when (state.filterBottomSheetType) {
            "sort" -> {
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
                                viewModel.onEvent(WishlistUiEvent.ChangeSortOption(value))
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = state.tempSortOption == value,
                            onClick = {
                                viewModel.onEvent(WishlistUiEvent.ChangeSortOption(value))
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = display)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.onEvent(WishlistUiEvent.ApplySortOption)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = ButtonShape
                ) {
                    Text("Apply")
                }
            }

            "filter" -> {
                // Show filter options based on selected filter
                when (state.selectedFilter) {

                    Filter.AVAILABILITY -> {

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            AvailabilityOption.entries.forEach { option ->
                                FilterOptionMultiSelectItem(
                                    filterOption = option.displayValue,
                                    isChecked = state.tempAvailabilityOptions.contains(option),
                                    onCheckedChange = {
                                        viewModel.onEvent(
                                            WishlistUiEvent.ChangeSelectedAvailability(option)
                                        )
                                    }
                                )
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
                                        viewModel.onEvent(
                                            WishlistUiEvent.ChangeSelectedCondition(conditionOption)
                                        )
                                    }
                                )
                            }
                        }
                    }

                    Filter.CATEGORY -> {
                        CategoryFilterContent(
                            selectedTertiaryCategories = state.tempSelectedTertiaryCategories,
                            onTertiaryCategoryClick = { category ->
                                viewModel.onEvent(
                                    WishlistUiEvent.ChangeSelectedTertiaryCategory(category)
                                )
                            },
                            onRemoveSpecialOption = { category ->
                                viewModel.onEvent(
                                    WishlistUiEvent.RemoveSelectedTertiaryCategory(category)
                                )
                            },
                            onSelectAll = { category ->
                                viewModel.onEvent(WishlistUiEvent.SelectAllCategories(category))
                            }
                        )
                    }

                    else -> {
                        Text("Filter not implemented")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            viewModel.onEvent(WishlistUiEvent.ClearFilters)
                        },
                        modifier = Modifier.weight(1f),
                        shape = ButtonShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Clear")
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Button(
                        onClick = {
                            viewModel.onEvent(WishlistUiEvent.ApplyFilters)
                        },
                        modifier = Modifier.weight(1f),
                        shape = ButtonShape
                    ) {
                        Text("Apply")
                    }
                }

            }
        }
    }
}

@Composable
private fun CategoryFilterContent(
    selectedTertiaryCategories: List<FilterTertiaryCategory>,
    onTertiaryCategoryClick: (FilterTertiaryCategory) -> Unit,
    onRemoveSpecialOption: (FilterTertiaryCategory) -> Unit,
    onSelectAll: (FilterCategoryUiModel) -> Unit
) {
    var selectedCategory by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (selectedCategory.isNotEmpty()) {
            val currentCategory = FilterCategoryUiModel.predefinedCategories.find { it.name == selectedCategory }
            val categoryTertiaryCategories = currentCategory?.subcategories
                ?.flatMap { it.tertiaryCategories }
                ?.filter { it.parentCategory == currentCategory.name } ?: emptyList()

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        modifier = Modifier
                            .size(30.dp)
                            .offset(x = (-4).dp),
                        onClick = { selectedCategory = "" }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                            contentDescription = "select category",
                        )
                    }
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = selectedCategory,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                FilterOptionMultiSelectItem(
                    modifier = Modifier.padding(top = 4.dp),
                    filterOption = "Select All",
                    isChecked = selectedTertiaryCategories.count { it.parentCategory == currentCategory?.name } ==
                            categoryTertiaryCategories.size,
                    onCheckedChange = {
                        // Handle select all logic here using the filtered list for currentCategory
                        currentCategory?.let { onSelectAll(it) }
                    }
                )

                HorizontalDivider(
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 12.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.12f),
                )
            }
        } else {
            Text(
                text = "Category",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
//        Spacer(modifier = Modifier.size(12.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 300.dp),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            if (selectedCategory.isEmpty()) {
                items(FilterCategoryUiModel.predefinedCategories) { category ->
                    Row(
                        modifier = Modifier
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = { selectedCategory = category.name }
                            )
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Get tertiary categories for this category (filtered by parent)
                        val categoryTertiaryCategories = category.subcategories
                            .flatMap { it.tertiaryCategories }
                            .filter { it.parentCategory == category.name }

                        if (selectedTertiaryCategories.any { selected ->
                                categoryTertiaryCategories.any { it.uniqueKey == selected.uniqueKey }
                            }) {
                            AppliedFilterIndicator()
                            Spacer(modifier = Modifier.size(8.dp))
                        }

                        Text(
                            text = category.name
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                            contentDescription = "select category",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            } else {
                val subCategories = FilterCategoryUiModel.predefinedCategories
                    .find { it.name == selectedCategory }
                    ?.subcategories.orEmpty()

                for (subCategory in subCategories) {
                    if (subCategory.name != "Primary") {
                        item {
                            Text(
                                text = subCategory.name,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }

                    items(subCategory.tertiaryCategories) { tertiaryCategory ->
                        FilterOptionMultiSelectItem(
                            filterOption = tertiaryCategory.name,
                            isChecked = selectedTertiaryCategories.any { it.uniqueKey == tertiaryCategory.uniqueKey },
                            onCheckedChange = {
                                onTertiaryCategoryClick(tertiaryCategory)
                                if (selectedTertiaryCategories.any { it.uniqueKey == tertiaryCategory.uniqueKey }) {
                                    onRemoveSpecialOption(tertiaryCategory)
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}




@Composable
fun SelectedOptionsRowWishList(
    activeFilters: Set<Filter>,
    onClearFilter: (Filter) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(activeFilters.size) { index ->
            val filter = activeFilters.elementAt(index)
            SelectedFilterChip(
                filter = filter,
                onClear = { onClearFilter(filter) }
            )
        }
    }
}

@Composable
fun SelectedFilterChip(
    filter: Filter,
    onClear: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = filter.displayValue,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            IconButton(
                onClick = onClear,
                modifier = Modifier.size(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Clear filter",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(12.dp)
                )
            }
        }
    }
}