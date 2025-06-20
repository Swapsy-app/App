package com.example.freeupcopy.ui.presentation.cart_screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.freeupcopy.R
import com.example.freeupcopy.common.Constants
import com.example.freeupcopy.data.remote.dto.sell.ProductPrice
import com.example.freeupcopy.data.remote.dto.your_profile.UserProductCard
import com.example.freeupcopy.ui.presentation.common.rememberProductClickHandler
import com.example.freeupcopy.ui.presentation.product_listing.ProductListingViewModel
import com.example.freeupcopy.ui.presentation.sell_screen.location_screen.location_screen.PleaseWaitLoading
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.CardShape
import com.example.freeupcopy.ui.theme.NoteContainerLight
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerCartDetailScreen(
    sellerName: String,
    sellerId: String,
    onClose: () -> Unit,
    onNavigateToAddProducts: (String, String) -> Unit,
    onNavigateToCheckout: () -> Unit,
    onProductClick: (String) -> Unit,
    viewModel: SellerCartDetailViewModel = hiltViewModel(),
    productListingViewModel: ProductListingViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val availableProducts = viewModel.availableProducts.collectAsLazyPagingItems()
    val cartProducts = viewModel.cartProducts.collectAsLazyPagingItems()

    val lifeCycleOwner = LocalLifecycleOwner.current

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val productClickHandler = rememberProductClickHandler(
        productListingViewModel = productListingViewModel,
        onProductClick = onProductClick,
        onLoadingStateChange = { isLoading ->
            viewModel.onEvent(SellerCartDetailEvent.IsLoading(isLoading))
        }
    )

    LaunchedEffect(Unit) {
        viewModel.onEvent(SellerCartDetailEvent.LoadCartItems)
    }

    // Handle error with Snackbar
    LaunchedEffect(state.error) {
        if (state.error.isNotBlank() && !state.isLoading) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = state.error,
                    duration = SnackbarDuration.Short
                )
                // Clear error after showing
                viewModel.onEvent(SellerCartDetailEvent.ClearError)
            }
        }
    }

    // Handle success message with Snackbar
    LaunchedEffect(state.successMessage) {
        if (state.successMessage.isNotBlank() && !state.isLoading) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = state.successMessage,
                    duration = SnackbarDuration.Short
                )
                // Clear message after showing
                viewModel.onEvent(SellerCartDetailEvent.ClearMessage)
            }
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "$sellerName's",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            val currentState = lifeCycleOwner.lifecycle.currentState
                            if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                onClose()
                            }
                        }
                    ) {
                        Icon(Icons.Rounded.Close, "Close")
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState) { snackbarData ->
                Snackbar(
                    snackbarData = snackbarData,
                    containerColor = when {
                        // Check if it's a success message
                        state.successMessage.isNotBlank() -> {
                            Color(0xFF4CAF50).copy(alpha = 0.9f) // Green for success
                        }

                        // Error message styling
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
                        // Success message content color
                        state.successMessage.isNotBlank() -> {
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

                        else -> {
                            MaterialTheme.colorScheme.onErrorContainer
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (state.cartItems.isEmpty() || state.isLoading) return@Scaffold
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(NavigationBarDefaults.windowInsets)
                    .defaultMinSize(minHeight = 70.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = 50.dp)
                            .fillMaxWidth()
                            .clip(ButtonShape)
                            .clickable {
                                val currentState = lifeCycleOwner.lifecycle.currentState
                                if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                    onNavigateToCheckout()
                                }
                            }
                            .background(MaterialTheme.colorScheme.tertiary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Proceed to Checkout",
                            color = MaterialTheme.colorScheme.onTertiary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Current cart items section
                if (state.cartItems.isNotEmpty()) {
                    item {
                        Text(
                            "Items in Cart (${state.cartItems.size})",
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Display cart items
                    items(cartProducts.itemCount) { index ->
                        cartProducts[index]?.let { product ->
                            CartProductItem(
                                product = product,
                                selectedMode = state.cartItems[product._id] ?: "cash",
                                onRemoveFromCart = {
                                    viewModel.onEvent(
                                        SellerCartDetailEvent.RemoveProduct(product._id)
                                    )
                                },
                                onUpdatePaymentMode = { newMode ->
                                    viewModel.onEvent(
                                        SellerCartDetailEvent.UpdatePaymentMode(
                                            product._id,
                                            newMode
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

                    // Total price card
                    if (state.cartItems.isNotEmpty()) {
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                ) {
                                    Text(
                                        "Total for this seller:",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.Black.copy(0.85f)
                                    )

                                    // Calculate totals including mix values
                                    var totalCash = 0
                                    var totalCoins = 0

                                    cartProducts.itemSnapshotList.items.forEach { product ->
                                        val selectedMode = state.cartItems[product._id]

                                        when (selectedMode) {
                                            "cash" -> {
                                                // Add cash price + any mix cash component
                                                val cashPrice =
                                                    product.price.cashPrice?.toDouble()?.toInt()
                                                        ?: 0
                                                totalCash += cashPrice
                                            }

                                            "coin" -> {
                                                // Add coin price + any mix coin component
                                                val coinPrice =
                                                    product.price.coinPrice?.toDouble()?.toInt()
                                                        ?: 0
                                                totalCoins += coinPrice
                                            }

                                            "mix" -> {
                                                // Add both mix components to their respective totals
                                                val mixCash =
                                                    product.price.mixPrice?.enteredCash?.toDouble()
                                                        ?.toInt() ?: 0
                                                val mixCoin =
                                                    product.price.mixPrice?.enteredCoin?.toDouble()
                                                        ?.toInt() ?: 0
                                                totalCash += mixCash
                                                totalCoins += mixCoin
                                            }
                                        }
                                    }

                                    // Display consolidated totals
                                    if (totalCash > 0) {
                                        Text(
                                            "Cash: ₹$totalCash",
                                            fontWeight = FontWeight.W500,
                                            fontSize = 15.sp
                                        )
                                    }
                                    if (totalCoins > 0) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                "Coins: $totalCoins",
                                                fontWeight = FontWeight.W500,
                                                fontSize = 15.sp,
//                                                color = Color.Black.copy(0.75f)
                                            )

                                            Icon(
                                                modifier = Modifier
                                                    .padding(start = 2.dp)
                                                    .size(14.dp),
                                                painter = painterResource(id = R.drawable.coin),
                                                contentDescription = null,
                                                tint = Color.Unspecified
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

                // Available products section
                item {
                    Text(
                        "More Products by $sellerName",
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                if (availableProducts.itemCount == 0 && !state.isLoading) {
                    item {
                        Text(
                            "All available products are already in your cart",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                } else {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    // Navigate to AddSellerProductsScreen
                                    onNavigateToAddProducts(sellerId, sellerName)
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row {
                                    AsyncImage(
                                        model = Constants.BASE_URL_AVATAR + state.seller?.avatar,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(Modifier.size(8.dp))
                                    Column {
                                        Text(
                                            text = "Add Items From",
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Text(
                                            text = "@$sellerName",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }

                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = "Go to add products"
                                )
                            }
                        }
                    }
                }


//                // Handle paging loading states for available products
//                availableProducts.apply {
//                    when {
//                        loadState.refresh is LoadState.Loading -> {
//                            item {
//                                Box(
//                                    modifier = Modifier.fillMaxWidth(),
//                                    contentAlignment = Alignment.Center
//                                ) {
//                                    CircularProgressIndicator()
//                                }
//                            }
//                        }
//
//                        loadState.append is LoadState.Loading -> {
//                            item {
//                                Box(
//                                    contentAlignment = Alignment.Center,
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .padding(vertical = 16.dp)
//                                ) {
//                                    CircularProgressIndicator()
//                                }
//                            }
//                        }
//
//                        loadState.refresh is LoadState.Error -> {
//                            if (availableProducts.itemCount == 0) {
//                                item {
//                                    ErrorStateContent(
//                                        error = loadState.refresh as LoadState.Error,
//                                        onRetry = { availableProducts.retry() }
//                                    )
//                                }
//                            }
//                        }
//
//                        loadState.append is LoadState.Error -> {
//                            item {
//                                ErrorStateContent(
//                                    error = loadState.append as LoadState.Error,
//                                    onRetry = { availableProducts.retry() }
//                                )
//                            }
//                        }
//                    }
//
//                    // End of pagination message
//                    if (loadState.append == LoadState.NotLoading(endOfPaginationReached = true) &&
//                        availableProducts.itemCount != 0 && availableProducts.itemCount > 15
//                    ) {
//                        item {
//                            Text(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(bottom = 16.dp),
//                                text = "No more products to load",
//                                textAlign = TextAlign.Center,
//                                fontWeight = FontWeight.Bold
//                            )
//                        }
//                    }
//                }
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
fun ErrorStateContent(
    error: LoadState.Error,
    onRetry: () -> Unit
) {
    var message = ""
    val e = error.error
    if (e is UnknownHostException) {
        message = "No internet.\nCheck your connection"
    } else if (e is Exception) {
        message = e.message ?: "Unknown error occurred"
    }

    if (message.isNotEmpty()) {
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
                onClick = onRetry,
                shape = ButtonShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary
                )
            ) {
                Text("Retry")
            }
        }
    } else {
        // Fallback error UI
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.im_error),
                contentDescription = null
            )
            Text(
                text = "Something went wrong",
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.W500
            )
            Button(
                onClick = onRetry,
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

@Composable
fun CartProductItem(
    product: UserProductCard,
    selectedMode: String,
    onRemoveFromCart: () -> Unit,
    onUpdatePaymentMode: (String) -> Unit,
    onClick: () -> Unit
) {
    var showUpdateDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = CardShape.small,
        onClick = {
            onClick()
        }
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Product image
                AsyncImage(
                    model = product.images.firstOrNull(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CardShape.small),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = product.title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 18.sp
                    )

                    // Show price for selected mode
                    Text(
                        text = formatPrice(product.price, selectedMode),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                Row {
                    IconButton(onClick = { showUpdateDialog = true }) {
                        Icon(Icons.Outlined.Edit, "Change payment mode")
                    }
                    IconButton(onClick = onRemoveFromCart) {
                        Icon(Icons.Outlined.Delete, "Remove")
                    }
                }
            }
        }
    }

    if (showUpdateDialog) {
        PaymentModeSelectionDialog(
            product = product,
            currentMode = selectedMode,
            onDismiss = { showUpdateDialog = false },
            onConfirm = { newMode ->
                if (newMode != selectedMode) {
                    onUpdatePaymentMode(newMode)
                }
                showUpdateDialog = false
            }
        )
    }
}


@Composable
fun PaymentModeSelectionDialog(
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
                text = "Update Payment Method",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        },
        text = {
            Column {
                Text(
                    text = "Current: ${ if(currentMode == "mix") "Cash + Coins" else
                        currentMode?.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                        }
                    }",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

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
                                text = if (mode == "mix") "Cash + Coins" else
                                    mode.replaceFirstChar {
                                        if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                                    },
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = if (mode == selectedMode) FontWeight.Bold else FontWeight.Medium
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
                    onConfirm(selectedMode)
                },
                enabled = selectedMode != currentMode, // Only enable if mode actually changed
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary
                ),
                shape = ButtonShape
            ) {
                Text("Update")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}


fun formatPrice(price: ProductPrice, mode: String): String {
    return when (mode) {
        "cash" -> price.cashPrice?.let { "₹${it.toInt()}" } ?: "N/A"
        "coin" -> price.coinPrice?.let { "${it.toInt()} coins" } ?: "N/A"
        "mix" -> {
            val cash = price.mixPrice?.enteredCash
            val coin = price.mixPrice?.enteredCoin
            if (cash != null && coin != null) {
                "₹${cash.toInt()} + ${coin.toInt()} coins"
            } else "N/A"
        }

        else -> "N/A"
    }
}

private fun getAllAvailablePrices(price: ProductPrice): List<Pair<String, String>> {
    val prices = mutableListOf<Pair<String, String>>()

    price.cashPrice?.let {
        prices.add("cash" to "₹${it.toInt()}")
    }

    price.coinPrice?.let {
        prices.add("coin" to "${it.toInt()} coins")
    }

    price.mixPrice?.let { mixPrice ->
        prices.add("mix" to "₹${mixPrice.enteredCash.toInt()} + ${mixPrice.enteredCoin.toInt()} coins")
    }

    return prices
}

// Helper function to get available payment modes
fun getAvailablePaymentModes(price: ProductPrice): List<String> {
    val modes = mutableListOf<String>()
    if (price.cashPrice != null) modes.add("cash")
    if (price.coinPrice != null) modes.add("coin")
    if (price.mixPrice != null) modes.add("mix")
    return modes
}
