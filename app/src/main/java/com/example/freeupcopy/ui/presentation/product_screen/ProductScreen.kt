package com.example.freeupcopy.ui.presentation.product_screen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.freeupcopy.data.remote.dto.product.Reply
import com.example.freeupcopy.ui.navigation.Screen
import com.example.freeupcopy.ui.presentation.product_card.ProductCard
import com.example.freeupcopy.ui.presentation.product_screen.componants.BargainElement
import com.example.freeupcopy.ui.presentation.product_screen.componants.BargainOptionsSheet
import com.example.freeupcopy.ui.presentation.product_screen.componants.Comments
import com.example.freeupcopy.ui.presentation.product_screen.componants.DeliveryTime
import com.example.freeupcopy.ui.presentation.product_screen.componants.ImagePreviewDialog
import com.example.freeupcopy.ui.presentation.product_screen.componants.ProductDetails
import com.example.freeupcopy.ui.presentation.product_screen.componants.ProductFAB
import com.example.freeupcopy.ui.presentation.product_screen.componants.ProductScreenBottomBar
import com.example.freeupcopy.ui.presentation.product_screen.componants.SellerDetail
import com.example.freeupcopy.ui.presentation.sell_screen.location_screen.location_screen.PleaseWaitLoading
import com.example.freeupcopy.utils.getListedPrice
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedGetBackStackEntry")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    navController: NavHostController,
    onReplyClickComment: (String?) -> Unit,
    onReplyClickReply: (String?, String?) -> Unit,
    onBack: () -> Unit,
    onUserClick: (String) -> Unit,
    productViewModel: ProductViewModel = hiltViewModel()
) {
    val state by productViewModel.state.collectAsState()
    val similarProducts = productViewModel.similarProducts.collectAsLazyPagingItems()

    val lifeCycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var currentIndex by remember { mutableIntStateOf(0) }

    val scope = rememberCoroutineScope()

    // 1️⃣ Remember the back-stack entry
    val thisEntry = remember(navController) {
        navController.getBackStackEntry(Screen.ProductScreen(state.productId))
    }

    // 2️⃣ Get a StateFlow from SavedStateHandle, defaulting to null
    val newReplyFlow = thisEntry
        .savedStateHandle
        .getStateFlow<Reply?>("new_reply", null)

    // 3️⃣ Collect it as Compose state
    val newReply by newReplyFlow.collectAsState()

    LaunchedEffect(state.error) {
        Log.e("ProductScreen", "Error: ${state.error}")
        state.error.takeIf { it.isNotBlank() }?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    // 4️⃣ When a new reply arrives, update your ViewModel and clear it
    LaunchedEffect(newReply) {
        newReply?.let { reply ->
            productViewModel.prependReplyToComment(reply)
            thisEntry.savedStateHandle.remove<Reply>("new_reply")
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {

            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = {},
                actions = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ShoppingCart,
                            contentDescription = "cart button",
                        )
                    }
                    IconButton(
                        onClick = {
                            val shareIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    state.productLink
                                )
                                type = "text/plain"
                            }
                            context.startActivity(
                                Intent.createChooser(
                                    shareIntent,
                                    "Share via"
                                )
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Share,
                            contentDescription = "share button",
                        )
                    }

                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.MoreVert,
                            contentDescription = "more button",
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
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                            contentDescription = "close"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    scrolledContainerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            )
        },
        bottomBar = {
            ProductScreenBottomBar(
                specialOffer = state.productDetail?.price?.mix,
                coinsOffered = state.productDetail?.price?.coin,
                mrp = state.productDetail?.price?.mrp?.toInt().toString(),
                priceOffered = state.productDetail?.price?.cash
            )
        },
        floatingActionButton = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ProductFAB(
                    onClick = {
                        productViewModel.onEvent(ProductUiEvent.LikeProduct)
                    },
                    icon = {
                        Icon(
                            imageVector = if (state.isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "like icon",
                            tint = if (state.isLiked) Color.Red else LocalContentColor.current,
                        )
                    }
                )

                ProductFAB(
                    onClick = {},
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.ShoppingCart,
                            contentDescription = "cart icon",
                        )
                    }
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Start
    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .fillMaxWidth()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                item {
                    state.apply {
                        ProductDetails(
                            title = productDetail?.title ?: "No Title",
                            condition = productDetail?.condition ?: "No Condition",
                            images = productDetail?.images ?: emptyList(),
//                            size =
                            brand = productDetail?.brand ?: "No Brand",
                            description = productDetail?.description ?: "No Description",
                            category =
                                if(productDetail?.category == null) "No Category"
                                else productDetail.category.tertiaryCategory
                                    ?: (productDetail.category.primaryCategory),
                            manufacturingCountry = productDetail?.manufacturingCountry ?: "Unknown",
                            numberOfLikes = wishlistCount ?: 0,
                            shareCounter = 0,
                            color = productDetail?.color ?: "No Color",
                            fabric = productDetail?.fabric ?: "No Fabric",
                            occasion = productDetail?.occasion ?: "No Occasion",
                            shape = productDetail?.shape ?: "No Shape",
                            onImagePreview = { index ->
                                currentIndex = index
                                productViewModel.onEvent(ProductUiEvent.OnImagePreview)
                            },
                        )
                    }
                }

                item {
                    SellerDetail(
                        isFollowed = state.isFollowed,
                        onFollow = {
                            productViewModel.onEvent(ProductUiEvent.OnFollow)
                        }
                    )
                }

                item {
                    DeliveryTime(
                        pinCode = state.pincode,
                        onPinCodeChange = {
                            productViewModel.onEvent(ProductUiEvent.ChangePincode(it))
                        },
                        userLocation = "Mumbai, Maharashtra",
                        dateOfPickup = "24 Sep",
                        dateOfDelivery = "27 Sep"

                    )
                }
                item {
                    BargainElement(
                        bargainOffers = state.bargains,
                        onOpenPopup = {
                            //productViewModel.onOpenPopup()
                            productViewModel.onEvent(ProductUiEvent.BargainOptionsClicked)
                        },
                        onEditOffer = {
                            productViewModel.onEvent(ProductUiEvent.EditBargainOption(it))
                        },
                        currentUserId = state.user?._id,
                        isLoadingMore = state.isLoadingMoreBargains,
                        hasMoreBargains = state.hasMoreBargains,
                        onLoadMoreBargains = { productViewModel.loadMoreBargains() }
                    )
                }
                item {
                    Comments(
                        user = state.user,
                        comments = state.comments,
                        userComment = state.comment,
                        onCommentChange = {
                            productViewModel.onEvent(ProductUiEvent.ChangeComment(it))
                        },
                        sendComment = {
                            productViewModel.onEvent(ProductUiEvent.PostComment(state.mentionedUsers))
                        },
                        onReplyClickComment = { itemId ->
                            val currentState = lifeCycleOwner.lifecycle.currentState
                            if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                onReplyClickComment(itemId)
                            }
                        },
                        onReplyClickReply = { commentId, replyId ->
                            val currentState = lifeCycleOwner.lifecycle.currentState
                            if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                onReplyClickReply(commentId, replyId)
                            }
                        },
                        isLoadingMore = productViewModel.isLoadingMore,
                        hasMoreComments  = productViewModel.hasMoreComments,
                        loadMoreComments = { productViewModel.loadMoreComments() },
                        // Mention-related props
                        isMentioning = state.isMentioning,
                        mentionResults = state.mentionResults,
                        onSelectMention = { user ->
                            productViewModel.onEvent(ProductUiEvent.SelectMention(user))
                        },
                        onCancelMention = {
                            productViewModel.onEvent(ProductUiEvent.CancelMention)
                        },
                        onLongPressComment = { commentId, commentSenderId ->
                            productViewModel.onEvent(ProductUiEvent.ToggleConfirmDeleteDialog(
                                commentId,
                                commentSenderId
                            ))
                        },
                        onLongPressReply = { replyId, replySenderId ->
                            productViewModel.onEvent(ProductUiEvent.ToggleConfirmDeleteReply(
                                replyId,
                                replySenderId
                            ))
                        },
                        onUserClick = {
                            val currentState = lifeCycleOwner.lifecycle.currentState
                            if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                onUserClick(it)
                            }
                        },
                        commentReplies = state.commentReplies,
                        loadMoreReplies = { commentId ->
                            productViewModel.loadMoreReplies(commentId)
                        },
                        canLoadMoreReplies = { commentId, replyCount ->
                            productViewModel.canLoadMoreReplies(commentId, replyCount)
                        }
                    )
                }

                item {
                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Similar Products",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                        )

                        HorizontalDivider(
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.12f)
                        )
                    }
                }

                // Replace the current implementation for similar products
                item {
                    when {
                        // Show loading state when initially loading
                        similarProducts.loadState.refresh is LoadState.Loading -> {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        // Show error state
                        similarProducts.loadState.refresh is LoadState.Error -> {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "Failed to load similar products",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.error
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(onClick = { similarProducts.retry() }) {
                                    Text("Retry")
                                }
                            }
                        }

                        // Show empty state when no products found
                        similarProducts.itemCount == 0 -> {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                            ) {
                                Text(
                                    text = "No similar products found",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        // Show grid when we have products
                        else -> {
                            LazyVerticalStaggeredGrid(
                                columns = StaggeredGridCells.Fixed(2),
                                contentPadding = PaddingValues(
                                    start = 8.dp,
                                    end = 8.dp,
                                    bottom = 16.dp
                                ),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalItemSpacing = 8.dp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = Short.MAX_VALUE.toInt().dp)
                            ) {
                                items(similarProducts.itemCount) { index ->
                                    similarProducts[index]?.let { product ->
                                        ProductCard(
                                            brand = product.brand,
                                            title = product.title,
                                            size = "null",
                                            productThumbnail = if (product.images.size == 1) product.images[0] else null,
                                            cashPrice = if (product.price.cashPrice != null) product.price.cashPrice.toInt()
                                                .toString() else null,
                                            coinsPrice = if (product.price.coinPrice != null) product.price.coinPrice.toInt()
                                                .toString() else null,
                                            combinedPrice = if (product.price.mixPrice != null)
                                                Pair(
                                                    product.price.mixPrice.enteredCash.toInt()
                                                        .toString(),
                                                    product.price.mixPrice.enteredCoin.toInt()
                                                        .toString()
                                                )
                                            else null,
                                            mrp = product.price.mrp?.toInt().toString(),
                                            badge = "null",
                                            isLiked = false,
                                            onLikeClick = {},
                                            onClick = {
                                                scope.launch {
                                                    productViewModel.onEvent(
                                                        ProductUiEvent.IsLoading(
                                                            true
                                                        )
                                                    )

                                                    productViewModel.onEvent(
                                                        ProductUiEvent.SimilarProductClicked(
                                                            productId = product._id,
                                                            productImageUrl = if (product.images.size == 1) product.images[0] else "",
                                                            title = product.title
                                                        )
                                                    )
                                                    delay(100)
                                                    // Call onProductClick after onEvent has been processed.
                                                    navController.navigate(
                                                        Screen.ProductScreen(
                                                            product._id
                                                        )
                                                    )

                                                    productViewModel.onEvent(
                                                        ProductUiEvent.IsLoading(
                                                            false
                                                        )
                                                    )
                                                }
                                            }
                                        )
                                    }
                                }

                                // Add loading indicators and error handling
                                similarProducts.apply {
                                    when {
                                        loadState.append is LoadState.Loading -> {
                                            item(span = StaggeredGridItemSpan.FullLine) {
                                                Box(
                                                    contentAlignment = Alignment.Center,
                                                    modifier = Modifier.padding(vertical = 16.dp)
                                                ) {
                                                    CircularProgressIndicator()
                                                }
                                            }
                                        }

                                        loadState.append is LoadState.Error -> {
                                            item(span = StaggeredGridItemSpan.FullLine) {
                                                Button(
                                                    onClick = { retry() },
                                                    modifier = Modifier.fillMaxWidth()
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
            }
        }
    }
    if (state.isSheetOpen) {
        ModalBottomSheet(
            modifier = Modifier,
            sheetState = sheetState,
            onDismissRequest = { productViewModel.onEvent(ProductUiEvent.BargainOptionsClicked) },
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(start = 16.dp, bottom = 16.dp, end = 16.dp)
            ) {
                BargainOptionsSheet(
                    listedPrice = getListedPrice(state.productDetail?.price),
                    hasCashPrice = state.productDetail?.price?.cash != null,
                    hasCoinPrice = state.productDetail?.price?.coin != null,
                    mrp = state.productDetail?.price?.mrp?.toInt().toString(),
                    bargainAmount = state.bargainAmount,
                    onBargainAmountChange = {
                        productViewModel.onEvent(ProductUiEvent.ChangeBargainAmount(it))
                    },
                    message = state.bargainMessage,
                    onBargainMessageChange = {
                        productViewModel.onEvent(ProductUiEvent.ChangeBargainMessage(it))
                    },
                    onBargainRequest = {
                        val validationResult = productViewModel.validateAll()
                        if (validationResult.isValid) {
                            productViewModel.onEvent(ProductUiEvent.BargainRequest)
                        } else {
                            Toast.makeText(
                                context,
                                validationResult.errorMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    onBargainUpdateRequest = {
                        val validationResult = productViewModel.validateAll()
                        if (validationResult.isValid) {
                            productViewModel.onEvent(ProductUiEvent.BargainUpdateRequest)
                        } else {
                            Toast.makeText(
                                context,
                                validationResult.errorMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    onClosePopup = { productViewModel.onEvent(ProductUiEvent.BargainOptionsClicked) },
                    fifteenPercentRecommended = state.fifteenPercentRecommended ?: Pair("", ""),
                    tenPercentRecommended = state.tenPercentRecommended ?: Pair("", ""),
                    currencySelected = state.bargainCurrencySelected,
                    onCurrencySelected = {
                        productViewModel.onEvent(ProductUiEvent.BargainCurrencySelectedChange(it))
                    },
                    selectedIndex = state.bargainSelectedIndex,
                    onSelectedIndexChange = {
                        productViewModel.onEvent(ProductUiEvent.BargainSelectedChange(it))
                    },
                    //isEditing = false
                    isEditingBargain = state.isEditingBargain,
                    onDeleteBargain = {
                        state.currentEditingBargainId?.let {
                            ProductUiEvent.DeleteBargain(
                                it
                            )
                        }?.let { productViewModel.onEvent(it) }
                    }
                )
            }
        }
    }

    if (state.isImageOpen) {
        ImagePreviewDialog(
            initialImageIndex = currentIndex,
            images = state.productDetail?.images ?: emptyList(),
            onDismiss = { productViewModel.onEvent(ProductUiEvent.OnImagePreview) }
        )
    }

    if (state.isConfirmDeleteDialog) {
        ConfirmDialogBox(
            dialogText = "Are you sure you want to delete this comment?",
            onConfirm = {
                productViewModel.onEvent(ProductUiEvent.DeleteComment(state.deleteCommentId))
            },
            onCancel = {
                productViewModel.onEvent(ProductUiEvent.ToggleConfirmDeleteDialog(state.deleteCommentId, ""))
            },
            dialogTitle = "Confirm Delete",
            confirmButtonText = "Delete",
            cancelButtonText = "Cancel",
        )
    }

    if(state.isConfirmDeleteReply) {
        ConfirmDialogBox(
            dialogText = "Are you sure you want to delete this reply?",
            onConfirm = {
                productViewModel.onEvent(ProductUiEvent.DeleteReply(state.deleteReplyId))
            },
            onCancel = {
                productViewModel.onEvent(ProductUiEvent.ToggleConfirmDeleteReply(state.deleteReplyId, ""))
            },
            dialogTitle = "Confirm Delete",
            confirmButtonText = "Delete",
            cancelButtonText = "Cancel",
        )
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
fun ConfirmDialogBox(
    dialogText: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
    confirmButtonText: String = "Confirm",
    cancelButtonText: String = "Cancel",
    dialogTitle: String? = null
) {
    AlertDialog(
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        onDismissRequest = onCancel,
        title = dialogTitle?.let {
            { Text(text = it, fontWeight = FontWeight.SemiBold) }
        },
        text = {
            Text(text = dialogText)
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(confirmButtonText)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onCancel
            ) {
                Text(cancelButtonText)
            }
        },
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primaryContainer
    )
}