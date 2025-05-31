package com.example.freeupcopy.ui.presentation.offer_screen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.freeupcopy.R
import com.example.freeupcopy.common.Constants
import com.example.freeupcopy.data.remote.dto.product.BuyerBargain
import com.example.freeupcopy.data.remote.dto.product.SellerBargain
import com.example.freeupcopy.domain.enums.Currency
import com.example.freeupcopy.domain.enums.OfferStatusOption
import com.example.freeupcopy.domain.enums.OfferTabOption
import com.example.freeupcopy.ui.presentation.common.rememberProductClickHandler
import com.example.freeupcopy.ui.presentation.home_screen.componants.settleAppBar
import com.example.freeupcopy.ui.presentation.product_listing.ProductListingViewModel
import com.example.freeupcopy.ui.presentation.profile_screen.posted_products_screen.components.ConfirmDialog
import com.example.freeupcopy.ui.presentation.profile_screen.seller_profile_screen.SellerProfileUiEvent
import com.example.freeupcopy.ui.presentation.sell_screen.location_screen.location_screen.PleaseWaitLoading
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.CardShape
import com.example.freeupcopy.ui.theme.LinkColor
import com.example.freeupcopy.utils.getTimeAgo
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import java.util.Locale
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OffersScreen(
    modifier: Modifier = Modifier,
    onProductClick: (String) -> Unit,
    onBackClick: () -> Unit,
    productListingViewModel: ProductListingViewModel = hiltViewModel(),
    viewModel: OfferViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val buyerOffers = viewModel.buyerOffers.collectAsLazyPagingItems()
    val sellerOffers = viewModel.sellerOffers.collectAsLazyPagingItems()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val productClickHandler = rememberProductClickHandler(
        productListingViewModel = productListingViewModel,
        onProductClick = onProductClick,
        onLoadingStateChange = { isLoading ->
            viewModel.updateLoadingState(isLoading)
        }
    )

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    // Handle error with Snackbar
    LaunchedEffect(state.error) {
        if (state.error.isNotBlank() && !state.isLoading) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = state.error,
                    duration = SnackbarDuration.Short
                )
                // Clear error after showing
                viewModel.onEvent(OffersUiEvent.ClearError)
            }
            Log.e("SellerProfileScreen", "Error: ${state.error}")
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                title = { Text("My Offers", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState) { snackbarData ->
                Snackbar(
                    snackbarData = snackbarData,
                    containerColor = when {
                        state.error.contains("accepted", ignoreCase = true) ||
                                state.error.contains("success", ignoreCase = true) -> {
                            Color(0xFF4CAF50).copy(alpha = 0.9f) // Green for success
                        }
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
                        state.error.contains("accepted", ignoreCase = true) ||
                                state.error.contains("success", ignoreCase = true) -> {
                            Color.White
                        }
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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Primary Tab Row for Sent/Received
            PrimaryTabRow(
                modifier = Modifier.fillMaxWidth(),
                selectedTabIndex = state.selectedTab.ordinal,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ) {
                Tab(
                    selected = state.selectedTab == OfferTabOption.SENT,
                    onClick = {
                        viewModel.onEvent(OffersUiEvent.ChangeTab(OfferTabOption.SENT))
                    },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f),
                    text = {
                        Text(
                            text = "Sent",
                            fontSize = 16.sp
                        )
                    }
                )
                Tab(
                    selected = state.selectedTab == OfferTabOption.RECEIVED,
                    onClick = {
                        viewModel.onEvent(OffersUiEvent.ChangeTab(OfferTabOption.RECEIVED))
                    },
                    text = {
                        Text(
                            text = "Received",
                            fontSize = 16.sp
                        )
                    },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
                )
            }

            // Status filter row
            OfferStatusRow(
                onStatusClicked = { status ->
                    viewModel.onEvent(OffersUiEvent.ChangeStatus(status))
                },
                selectedStatus = state.selectedStatus,
                availableStatuses = viewModel.getAvailableStatusOptions()
            )

            // Content based on selected tab
            Box(modifier = Modifier.weight(1f)) {
                when (state.selectedTab) {
                    OfferTabOption.SENT -> {
                        BuyerOffersContent(
                            offers = buyerOffers,
                            onClick = { productId ->
                                productClickHandler.handleProductIdClick(productId)
                            }
                        )
                    }
                    OfferTabOption.RECEIVED -> {
                        SellerOffersContent(
                            offers = sellerOffers,
                            onClick = { productId ->
                                productClickHandler.handleProductIdClick(productId)
                            },
                            onAcceptOffer = { offerId ->
                                viewModel.onEvent(OffersUiEvent.AcceptOffer(offerId))
                            }
                        )
                    }
                }
            }
        }

        // Handle refresh loading and error states for both tabs
        val currentOffers = when (state.selectedTab) {
            OfferTabOption.SENT -> buyerOffers
            OfferTabOption.RECEIVED -> sellerOffers
        }

        currentOffers.apply {
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
                    if (currentOffers.itemCount == 0) {
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
                                    onClick = { currentOffers.retry() },
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

    // Loading overlay for product navigation
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
private fun BuyerOffersContent(
    offers: LazyPagingItems<BuyerBargain>,
    onClick: (String) -> Unit
) {
    if (offers.itemCount == 0 && offers.loadState.refresh is LoadState.NotLoading) {
        EmptyOffersMessage("No sent offers yet", "Your sent offers will appear here")
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 4.dp)
        ) {
            items(offers.itemCount) { index ->
                offers[index]?.let { offer ->
                    OfferItem(
                        offer = offer,
                        onClick = {
                            onClick(offer.product._id)
                        }
                    )
                }
            }

            // Handle append loading state (loading more items)
            offers.apply {
                if (loadState.append is LoadState.Loading) {
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

                // End of pagination message
                if (loadState.append == LoadState.NotLoading(endOfPaginationReached = true) &&
                    offers.itemCount != 0 && offers.itemCount > 15
                ) {
                    item {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            text = "No more offers to load",
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

                    item {
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
                                onClick = { offers.retry() },
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun SellerOffersContent(
    offers: LazyPagingItems<SellerBargain>,
    onClick: (String) -> Unit,
    onAcceptOffer: (String) -> Unit
) {
    if (offers.itemCount == 0 && offers.loadState.refresh is LoadState.NotLoading) {
        EmptyOffersMessage("No received offers yet", "Offers from buyers will appear here")
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 4.dp)
        ) {
            items(offers.itemCount) { index ->
                offers[index]?.let { offer ->
                    SellerOfferItem(
                        offer = offer,
                        onClick = {
                            onClick(offer.product._id)
                        },
                        onAcceptOffer = {
                            onAcceptOffer(offer._id)
                        }
                    )
                }
            }

            // Handle append loading state (loading more items)
            offers.apply {
                if (loadState.append is LoadState.Loading) {
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

                // End of pagination message
                if (loadState.append == LoadState.NotLoading(endOfPaginationReached = true) &&
                    offers.itemCount != 0 && offers.itemCount > 15
                ) {
                    item {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            text = "No more offers to load",
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

                    item {
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
                                onClick = { offers.retry() },
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

@Composable
private fun EmptyOffersMessage(
    title: String,
    subtitle: String
) {
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
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
fun OfferItem(
    offer: BuyerBargain,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = CardShape.small,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Seller info header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = Constants.BASE_URL_AVATAR + offer.seller.avatar,
                    contentDescription = "Seller profile",
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = offer.seller.username,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Product info section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Product image
                AsyncImage(
                    model = offer.product.image,
                    contentDescription = "Product image",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                // Product details
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    // Product title with arrow
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = offer.product.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium,
                            maxLines = 2,
                            modifier = Modifier.weight(1f),
                            overflow = TextOverflow.Ellipsis,
                            lineHeight = 18.sp
                        )

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "View details",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Posted price - show based on available options
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Posted Price: ",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        // Show price based on available options
                        when {
                            offer.product.cashPrice != null -> {
                                Text(
                                    text = "${Currency.CASH.symbol}${offer.product.cashPrice.toInt()}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            offer.product.coinPrice != null -> {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "${offer.product.coinPrice.toInt()}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(
                                        modifier = Modifier.size(14.dp),
                                        painter = painterResource(R.drawable.coin),
                                        contentDescription = null,
                                        tint = Color.Unspecified
                                    )
                                }
                            }
                            offer.product.mixPrice != null -> {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "${Currency.CASH.symbol}${offer.product.mixPrice.enteredCash.toInt()}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = " + ${offer.product.mixPrice.enteredCoin.toInt()}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(
                                        modifier = Modifier.size(14.dp),
                                        painter = painterResource(R.drawable.coin),
                                        contentDescription = null,
                                        tint = Color.Unspecified
                                    )
                                }
                            }
                            else -> {
                                Text(
                                    text = "${Currency.CASH.symbol}${offer.product.mrp?.toInt() ?: 0}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Offer amount
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Offer: ",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        if (offer.offeredIn == "cash") {
                            Text(
                                text = "${Currency.CASH.symbol}${offer.offeredPrice?.toInt()}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        } else {
                            Text(
                                text = "${offer.offeredPrice?.toInt()}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                modifier = Modifier.size(16.dp),
                                painter = painterResource(R.drawable.coin),
                                contentDescription = null,
                                tint = Color.Unspecified
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Status badges only
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatusBadge(status = offer.status)

                // Product status indicator if unavailable
                if (offer.product.status != "available") {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.errorContainer
                    ) {
                        Text(
                            text = offer.product.status.replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                            },
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun StatusBadge(
    status: String,
    modifier: Modifier = Modifier
) {
    val (backgroundColor, textColor, statusText) = when (status.lowercase()) {
        "pending" -> Triple(
            Color(0xFFFF9800).copy(alpha = 0.1f), // Orange background
            Color(0xFFDE8400), // Orange text
            "Pending"
        )

        "accepted" -> Triple(
            Color(0xFF4CAF50).copy(alpha = 0.1f), // Green background
            Color(0xFF4CAF50), // Green text
            "Accepted"
        )

        else -> Triple(
            MaterialTheme.colorScheme.surfaceVariant,
            MaterialTheme.colorScheme.onSurfaceVariant,
            status.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
        )
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor
    ) {
        Text(
            text = statusText,
            style = MaterialTheme.typography.labelMedium,
            color = textColor,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun OfferStatusRow(
    modifier: Modifier = Modifier,
    onStatusClicked: (String?) -> Unit,
    selectedStatus: String?,
    availableStatuses: List<OfferStatusOption>
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(availableStatuses) { status ->
                val isStatusSelected = selectedStatus == status.displayValue.lowercase()
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            color = if (isStatusSelected) LinkColor.copy(0.5f)
                            else MaterialTheme.colorScheme.onPrimaryContainer.copy(0.12f),
                            shape = CircleShape
                        )
                        .background(
                            if (isStatusSelected) MaterialTheme.colorScheme.secondaryContainer
                            else MaterialTheme.colorScheme.primaryContainer
                        )
                        .clickable {
                            onStatusClicked(
                                if (isStatusSelected) null
                                else status.displayValue.lowercase()
                            )
                        }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = status.displayValue,
                        color = if (isStatusSelected) MaterialTheme.colorScheme.onPrimaryContainer
                        else MaterialTheme.colorScheme.onPrimaryContainer.copy(0.6f),
                        fontSize = 14.sp,
                        fontWeight = if (isStatusSelected) FontWeight.W500 else FontWeight.Normal,
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SellerOfferItem(
    offer: SellerBargain,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onAcceptOffer: ((String) -> Unit)? = null
) {
    var isConfirmAccept by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = CardShape.small,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Buyer info header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = Constants.BASE_URL_AVATAR + offer.buyer.avatar,
                        contentDescription = "Buyer profile",
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = offer.buyer.username,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Text(
                    text = getTimeAgo(offer.createdAt),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Product info section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Product image
                AsyncImage(
                    model = offer.product.image,
                    contentDescription = "Product image",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                // Product details
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = offer.product.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Listed price vs Asked price
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Listed Price",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            // Show listed price based on available options (same logic as OfferItem)
                            when {
                                offer.product.cashPrice != null -> {
                                    Text(
                                        text = "${Currency.CASH.symbol}${offer.product.cashPrice.toInt()}",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                                offer.product.coinPrice != null -> {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "${offer.product.coinPrice.toInt()}",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(
                                            modifier = Modifier.size(16.dp),
                                            painter = painterResource(R.drawable.coin),
                                            contentDescription = null,
                                            tint = Color.Unspecified
                                        )
                                    }
                                }
                                offer.product.mixPrice != null -> {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "${Currency.CASH.symbol}${offer.product.mixPrice.enteredCash.toInt()}",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = " + ${offer.product.mixPrice.enteredCoin.toInt()}",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(
                                            modifier = Modifier.size(16.dp),
                                            painter = painterResource(R.drawable.coin),
                                            contentDescription = null,
                                            tint = Color.Unspecified
                                        )
                                    }
                                }
                                else -> {
                                    Text(
                                        text = "${Currency.CASH.symbol}${offer.product.mrp?.toInt() ?: 0}",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }

                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = "Asked Price",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (offer.offeredIn == "cash") {
                                    Text(
                                        text = "${Currency.CASH.symbol}${offer.offeredPrice?.toInt()}",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                } else {
                                    Text(
                                        text = "${offer.offeredPrice?.toInt()}",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(
                                        modifier = Modifier.size(16.dp),
                                        painter = painterResource(R.drawable.coin),
                                        contentDescription = null,
                                        tint = Color.Unspecified
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Message (if present)
            offer.message?.let { message ->
                if (message.isNotBlank()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        )
                    ) {
                        Text(
                            text = "\"$message\"",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(12.dp),
                            fontStyle = FontStyle.Italic,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Status badge and accept button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatusBadge(status = offer.status)

                // Accept button for pending offers
                if (offer.status == "pending" && onAcceptOffer != null) {
                    Button(
                        onClick = {
                            isConfirmAccept = true // Show confirmation dialog
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Accept",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }

    // Confirmation dialog
    if (isConfirmAccept) {
        ConfirmDialog(
            dialogText = "Do you want to accept this offer from ${offer.buyer.username}?",
            onConfirm = {
                isConfirmAccept = false
                onAcceptOffer?.invoke(offer._id)
            },
            onCancel = {
                isConfirmAccept = false
            },
            dialogTitle = "Accept Offer",
            confirmButtonText = "Accept",
            cancelButtonText = "Cancel"
        )
    }
}
