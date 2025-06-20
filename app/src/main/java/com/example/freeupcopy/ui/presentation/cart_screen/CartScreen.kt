@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.freeupcopy.ui.presentation.cart_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import coil.compose.AsyncImage
import com.example.freeupcopy.R
import com.example.freeupcopy.common.Constants
import com.example.freeupcopy.data.remote.dto.cart.CartProduct
import com.example.freeupcopy.data.remote.dto.cart.SellerCart
import com.example.freeupcopy.domain.enums.Currency
import com.example.freeupcopy.ui.presentation.profile_screen.posted_products_screen.components.ConfirmDialog
import com.example.freeupcopy.ui.presentation.sell_screen.location_screen.location_screen.PleaseWaitLoading
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.CardShape
import kotlinx.coroutines.launch

@Composable
fun CartScreen(
    onNavigateBack: () -> Unit,
    onNavigateToCheckout: () -> Unit,
    onNavigateToSellerCartDetail: (String, String) -> Unit,
    viewModel: CartViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifeCycleOwner = LocalLifecycleOwner.current

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Handle error with Snackbar
    LaunchedEffect(state.error) {
        if (state.error.isNotBlank() && !state.isLoading) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = state.error,
                    duration = SnackbarDuration.Short
                )
                // Clear error after showing
                viewModel.onEvent(CartUiEvent.ClearError)
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
                viewModel.onEvent(CartUiEvent.ClearMessage)
            }
        }
    }

    // Refresh cart on initial load
    LaunchedEffect(Unit) {
        viewModel.onEvent(CartUiEvent.RefreshCart)
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Cart (${state.totalCombos})",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            val currentState = lifeCycleOwner.lifecycle.currentState
                            if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                onNavigateBack()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
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

                        state.error.contains("empty", ignoreCase = true) -> {
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

                        state.error.contains("empty", ignoreCase = true) -> {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        }

                        else -> {
                            MaterialTheme.colorScheme.onErrorContainer
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (state.cartItems.isEmpty() && !state.isLoading) {
                    EmptyCartContent(
                        onContinueShopping = onNavigateBack
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.cartItems) { sellerCart ->
                            SellerCartCard(
                                sellerCart = sellerCart,
                                onRemoveSeller = { sellerId ->
                                    viewModel.onEvent(CartUiEvent.RemoveSeller(sellerId))
                                },
                                onEditSellerCart = { sellerId, sellerName ->
                                    onNavigateToSellerCartDetail(sellerId, sellerName)
                                }
                            )
                        }
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
fun EmptyCartContent(
    onContinueShopping: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = null,
            modifier = Modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Your cart is empty",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Add some products to get started",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onContinueShopping,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continue Shopping")
        }
    }
}

@Composable
fun SellerCartCard(
    sellerCart: SellerCart,
    onRemoveSeller: (String) -> Unit,
    onEditSellerCart: (String, String) -> Unit
) {
    var showConfirmDelete by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        shape = CardShape.medium,
        onClick = {
            onEditSellerCart(
                sellerCart.seller._id,
                sellerCart.seller.username
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // First row: Seller info and actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = Constants.BASE_URL_AVATAR + sellerCart.seller.avatar,
                        contentDescription = null,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = sellerCart.seller.username,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                TextButton(
                    onClick = {
                        showConfirmDelete = true
                    }
                ) {
                    Text(
                        text = "Delete All",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp, top = 4.dp),
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.15f),
                thickness = 1.dp
            )
            // Second row: Product images
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                sellerCart.products.take(5).forEach { product ->
                    CartProductImage(
                        imageUrl = product.image,
                        contentDescription = product.title
                    )
                    if( product != sellerCart.products.last()) {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }
    }

    if (showConfirmDelete) {
        ConfirmDialog(
            dialogText = "Are you sure you want to delete all products from ${sellerCart.seller.username}'s cart? This action cannot be undone.",
            onConfirm = {
                onRemoveSeller(sellerCart.seller._id)
                showConfirmDelete = false
            },
            onCancel = { showConfirmDelete = false },
            confirmButtonText = "Delete All",
            cancelButtonText = "Cancel",
        )
    }
}

@Composable
fun CartProductImage(
    imageUrl: String,
    contentDescription: String
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        modifier = Modifier
            .size(56.dp)
            .clip(CardShape.small),
        contentScale = ContentScale.Crop
    )
}