@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.freeupcopy.ui.presentation.cart_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.freeupcopy.R
import com.example.freeupcopy.data.remote.dto.sell.ProductCard
import com.example.freeupcopy.data.remote.dto.your_profile.UserProductCard
import com.example.freeupcopy.ui.presentation.common.rememberProductClickHandler
import com.example.freeupcopy.ui.presentation.product_listing.ProductListingViewModel
import com.example.freeupcopy.ui.presentation.sell_screen.location_screen.location_screen.PleaseWaitLoading
import com.example.freeupcopy.ui.theme.BottomSheetShape
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.CardShape
import com.example.freeupcopy.ui.theme.CashColor2
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSellerProductsScreen(
    sellerId: String,
    sellerName: String,
    onNavigateBack: () -> Unit,
    onProductClick: (String) -> Unit,
    viewModel: AddSellerProductsViewModel = hiltViewModel(),
    productListingViewModel: ProductListingViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val availableProducts = viewModel.availableProducts.collectAsLazyPagingItems()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Initialize with sellerId
    LaunchedEffect(sellerId) {
        viewModel.initializeSeller(sellerId)
    }

    val productClickHandler = rememberProductClickHandler(
        productListingViewModel = productListingViewModel,
        onProductClick = onProductClick,
        onLoadingStateChange = { isLoading ->
            viewModel.onEvent(AddSellerProductsUiEvent.IsLoading(isLoading))
        }
    )

    // Handle error with Snackbar
    LaunchedEffect(state.error) {
        if (state.error.isNotBlank() && !state.isLoading) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = state.error,
                    duration = SnackbarDuration.Short
                )
                // Clear error after showing
                viewModel.onEvent(AddSellerProductsUiEvent.ClearError)
            }
        }
    }

    // Handle success message with Snackbar
    LaunchedEffect(state.message) {
        if (state.message.isNotBlank() && !state.isLoading) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = state.message,
                    duration = SnackbarDuration.Short
                )
                // Clear message after showing
                viewModel.onEvent(AddSellerProductsUiEvent.ClearMessage)
            }
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Add Items From",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                        Text(
                            text = "@$sellerName",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState) { snackbarData ->
                Snackbar(
                    snackbarData = snackbarData,
                    containerColor = when {
                        // Success message
                        state.message.isNotBlank() -> {
                            Color(0xFF4CAF50).copy(alpha = 0.9f) // Green for success
                        }
                        // Error message types
                        state.error.contains("already", ignoreCase = true) ||
                                state.error.contains("duplicate", ignoreCase = true) -> {
                            MaterialTheme.colorScheme.primaryContainer
                        }

                        state.error.contains("network", ignoreCase = true) ||
                                state.error.contains("internet", ignoreCase = true) ||
                                state.error.contains("connection", ignoreCase = true) -> {
                            Color(0xFFFF9800).copy(alpha = 0.9f) // Orange for network issues
                        }

                        state.error.contains("maximum", ignoreCase = true) ||
                                state.error.contains("limit", ignoreCase = true) -> {
                            MaterialTheme.colorScheme.primaryContainer
                        }

                        else -> {
                            MaterialTheme.colorScheme.errorContainer // Red for general errors
                        }
                    },
                    contentColor = when {
                        // Success message content color
                        state.message.isNotBlank() -> {
                            Color.White
                        }
                        // Error message content colors
                        state.error.contains("already", ignoreCase = true) ||
                                state.error.contains("duplicate", ignoreCase = true) -> {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        }

                        state.error.contains("network", ignoreCase = true) ||
                                state.error.contains("internet", ignoreCase = true) ||
                                state.error.contains("connection", ignoreCase = true) -> {
                            Color.White
                        }

                        state.error.contains("maximum", ignoreCase = true) ||
                                state.error.contains("limit", ignoreCase = true) -> {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        }

                        else -> {
                            MaterialTheme.colorScheme.onErrorContainer
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (state.addedProducts.isNotEmpty()) {
                AddedItemsBottomBar(
                    addedItemsCount = state.addedProducts.size,
                    onShowAddedItems = {
                        viewModel.onEvent(AddSellerProductsUiEvent.ShowAddedItemsSheet)
                    },
                    onConfirm = {
                        viewModel.onEvent(AddSellerProductsUiEvent.ConfirmAddedItems)
                        onNavigateBack()
                    }
                )
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                // Info banner
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE3F2FD)
                    ),
                    shape = CardShape.medium
                ) {
                    Text(
                        text = if (state.addedProducts.isNotEmpty() && state.addedProducts.size < 5) {
                            "${state.addedProducts.size} item${if (state.addedProducts.size > 1) "s" else ""} added • Add upto ${5 - state.addedProducts.size} more and save on delivery fee"
                        } else if (state.addedProducts.size == 5) {
                            "You have added 5 items. You can now confirm your cart."
                        } else {
                            "Add products to your cart from this seller. You can add up to 5 items."
                        },
                        modifier = Modifier.padding(12.dp),
                        color = Color(0xFF1976D2),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }

                // Products Grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(availableProducts.itemCount) { index ->
                        availableProducts[index]?.let { product ->
                            ProductGridItem(
                                product = product,
                                onAddToCart = { selectedMode ->
                                    viewModel.onEvent(
                                        AddSellerProductsUiEvent.AddToCartUi(
                                            productId = product._id,
                                            selectedMode = selectedMode,
                                            productTitle = product.title,
                                            productImageUrl = product.images.firstOrNull() ?: "",
                                            price = formatProductPriceFromUserProduct(product, selectedMode),
                                        )
                                    )
                                },
                                onClick = {
                                    productClickHandler.handleProductValueClick(
                                        productId = product._id,
                                        productImageUrl = product.images.firstOrNull() ?: "",
                                        title = product.title
                                    )
                                }
                            )
                        }
                    }

                    // Handle paging loading states
                    availableProducts.apply {
                        when {
//                            loadState.refresh is LoadState.Loading -> {
//                                item {
//                                    Box(
//                                        modifier = Modifier.fillMaxWidth(),
//                                        contentAlignment = Alignment.Center
//                                    ) {
//                                        CircularProgressIndicator()
//                                    }
//                                }
//                            }

                            loadState.append is LoadState.Loading -> {
                                item {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 16.dp)
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }
                            }

                            loadState.refresh is LoadState.Error -> {
                                if (availableProducts.itemCount == 0) {
                                    item {
                                        ErrorStateContent(
                                            error = loadState.refresh as LoadState.Error,
                                            onRetry = { availableProducts.retry() }
                                        )
                                    }
                                }
                            }

                            loadState.append is LoadState.Error -> {
                                item {
                                    ErrorStateContent(
                                        error = loadState.append as LoadState.Error,
                                        onRetry = { availableProducts.retry() }
                                    )
                                }
                            }
                        }

                        // End of pagination message
                        if (loadState.append == LoadState.NotLoading(endOfPaginationReached = true) &&
                            availableProducts.itemCount != 0 && availableProducts.itemCount > 15
                        ) {
                            item {
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
                    }
                }
            }
        }
    }

    // Added Items Bottom Sheet
    if (state.showAddedItemsSheet) {
        AddedItemsBottomSheet(
            addedProducts = state.addedProducts,
            onDismiss = {
                viewModel.onEvent(AddSellerProductsUiEvent.HideAddedItemsSheet)
            },
            onRemoveItem = { productId ->
                viewModel.onEvent(AddSellerProductsUiEvent.RemoveFromCart(productId))
            },
            onConfirm = {
                viewModel.onEvent(AddSellerProductsUiEvent.ConfirmAddedItems)
                onNavigateBack()
            }
        )
    }

    // Loading overlay for cart operations
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
fun AddedItemsBottomBar(
    addedItemsCount: Int,
    onShowAddedItems: () -> Unit,
    onConfirm: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(start = 12.dp, end = 12.dp, top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Added Items Button
        OutlinedButton(
            onClick = onShowAddedItems,
            modifier = Modifier.weight(1f),
            shape = ButtonShape,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("$addedItemsCount Item${if (addedItemsCount > 1) "s" else ""} Added")
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        // Confirm Button
        Button(
            onClick = onConfirm,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black, // Yellow color like in image
                contentColor = Color.White
            ),
            shape = ButtonShape
        ) {
            Text("Confirm")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddedItemsBottomSheet(
    addedProducts: List<AddedProduct>,
    onDismiss: () -> Unit,
    onRemoveItem: (String) -> Unit,
    onConfirm: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = BottomSheetShape,
        sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        ),
        dragHandle = null,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${addedProducts.size} Item${if (addedProducts.size > 1) "s" else ""} Added",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Added Items List
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                addedProducts.forEach { product ->
                    AddedProductItem(
                        product = product,
                        onRemove = { onRemoveItem(product.productId) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Confirm Button
            Button(
                onClick = onConfirm,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary
                ),
                shape = ButtonShape
            ) {
                Text(
                    "Confirm"
                )
            }
        }
    }
}

@Composable
fun AddedProductItem(
    product: AddedProduct,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image
            AsyncImage(
                model = product.image,
                contentDescription = product.title,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Product Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 18.sp
                )

                Text(
                    text = product.price,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // Remove Button
            IconButton(onClick = onRemove) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun ProductGridItem(
    product: UserProductCard,
    onAddToCart: (String) -> Unit,
    onClick: () -> Unit = {}
) {
    var showPaymentDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = CardShape.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        onClick = {
            onClick()
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Product Image
            AsyncImage(
                model = product.images.firstOrNull(),
                contentDescription = product.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Product Title
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Price Section
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    // Current Price
                    if (product.price.cashPrice != null) {
                        Text(
                            text = "₹${product.price.cashPrice.toInt()}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "₹${product.price.mrp?.toInt()}", // Replace with discounted price
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.4f),
                            textDecoration = TextDecoration.LineThrough,
                            fontSize = 13.sp
                        )
                    } else if (product.price.coinPrice != null) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${product.price.coinPrice.toInt()}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(Modifier.size(2.dp))
                            Icon(
                                modifier = Modifier.size(16.dp),
                                painter = painterResource(id = R.drawable.coin),
                                contentDescription = "coin",
                                tint = Color.Unspecified
                            )
                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = "₹${product.price.mrp?.toInt()}", // Replace with discounted price
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.4f),
                                textDecoration = TextDecoration.LineThrough,
                                fontSize = 13.sp
                            )
                        }
                    } else if (product.price.mixPrice != null) {
                        val mixPrice = product.price.mixPrice
                        val cash = mixPrice.enteredCash.toInt()
                        val coin = mixPrice.enteredCoin.toInt()

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "₹$cash", // Replace with discounted price
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Text(
                                text = " + ", // Replace with discounted price
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W500
                            )

                            Text(
                                text = "$coin", // Replace with discounted price
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.size(2.dp))
                            Icon(
                                modifier = Modifier.size(16.dp),
                                painter = painterResource(id = R.drawable.coin),
                                contentDescription = "coin",
                                tint = Color.Unspecified
                            )
                        }
                    }
                }
                OutlinedButton(
                    onClick = {
                        val availableModes = getAvailablePaymentModes(product.price)
                        if (availableModes.size == 1) {
                            onAddToCart(availableModes.first())
                        } else {
                            showPaymentDialog = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = ButtonShape
                ) {
                    Text(
                        text = "+ Add",
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }

    // Payment Mode Selection Dialog
    if (showPaymentDialog) {
        PaymentModeAddDialog(
            product = product,
            onDismiss = { showPaymentDialog = false },
            onConfirm = { mode ->
                onAddToCart(mode)
                showPaymentDialog = false
            }
        )
    }
}

@Composable
fun PaymentModeAddDialog(
    product: UserProductCard,
    currentMode: String? = null,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    val availableModes = getAvailablePaymentModes(product.price)
    var selectedMode by remember { mutableStateOf(currentMode ?: availableModes.first()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        shape = CardShape.medium,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        title = {
            Text(
                text = "Select Payment Method",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        },
        text = {
            Column {
                availableModes.forEach { mode ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedMode = mode }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = mode == selectedMode,
                            onClick = { selectedMode = mode }
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        Column {
                            Text(
                                text = if(mode == "mix") {
                                    "Cash + Coins"
                                } else
                                    mode.replaceFirstChar {
                                    if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                                },
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = formatPrice(product.price, mode),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    Log.e("PaymentDialog", "Selected mode: $selectedMode")
                    onConfirm(selectedMode)
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

private fun formatProductPriceFromUserProduct(product: UserProductCard?, selectedMode: String): String {
    if (product == null) return "Price not available"

    return when (selectedMode) {
        "cash" -> product.price.cashPrice?.let { "₹${it.toInt()}" } ?: "N/A"
        "coin" -> product.price.coinPrice?.let { "${it.toInt()} coins" } ?: "N/A"
        "mix" -> {
            val cash = product.price.mixPrice?.enteredCash
            val coin = product.price.mixPrice?.enteredCoin
            if (cash != null && coin != null) {
                "₹${cash.toInt()} + ${coin.toInt()} coins"
            } else "N/A"
        }
        else -> "N/A"
    }
}
