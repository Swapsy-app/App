package com.example.freeupcopy.ui.presentation.product_screen

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.presentation.product_screen.componants.BargainElement
import com.example.freeupcopy.ui.presentation.product_screen.componants.BargainOptionsSheet
import com.example.freeupcopy.ui.presentation.product_screen.componants.Comments
import com.example.freeupcopy.ui.presentation.product_screen.componants.DeliveryTime
import com.example.freeupcopy.ui.presentation.product_screen.componants.ImagePreviewDialog
import com.example.freeupcopy.ui.presentation.product_screen.componants.ProductDetails
import com.example.freeupcopy.ui.presentation.product_screen.componants.ProductFAB
import com.example.freeupcopy.ui.presentation.product_screen.componants.ProductScreenBottomBar
import com.example.freeupcopy.ui.presentation.product_screen.componants.SellerDetail
import com.example.freeupcopy.ui.theme.SwapGoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    onReplyClick: (String?) -> Unit,
    productViewModel: ProductViewModel = viewModel()
) {
    val state = productViewModel.state.collectAsState()
    val lifeCycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    var currentIndex by remember { mutableIntStateOf(0) }

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
                                    state.value.productLink
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
                            productViewModel.onClose()
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
                specialOffer = state.value.specialOffer,
                coinsOffered = state.value.listedCoinPrice,
                mrp = state.value.mrp,
                priceOffered = state.value.listedCashPrice,
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
                            imageVector = if (state.value.isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "like icon",
                            tint = if (state.value.isLiked) Color.Red else LocalContentColor.current,
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
                    ProductDetails(
                        numberOfLikes = state.value.likeCounter.toInt(),
                        shareCounter = state.value.shareCounter.toInt(),
                        onImagePreview = { index ->
                            currentIndex = index
                            productViewModel.onEvent(ProductUiEvent.OnImagePreview)
                        }
                    )
                }

                item {
                    SellerDetail(
                        isFollowed = state.value.isFollowed,
                        onFollow = {
                            productViewModel.onEvent(ProductUiEvent.OnFollow)
                        }
                    )
                }

                item {
                    DeliveryTime(
                        pinCode = state.value.pincode,
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
                        bargainOffers = state.value.bargainOfferLists,
                        onOpenPopup = {
                            //productViewModel.onOpenPopup()
                            productViewModel.onEvent(ProductUiEvent.BargainOptionsClicked)
                        },
                        onEditOffer = {
                            productViewModel.onEvent(ProductUiEvent.EditBargainOption(it))
                        },
                        currentUserId = "U002",

                    )
                }
                item {
                    Comments(
                        comment = state.value.comments,
                        userComment = state.value.comment,
                        onCommentChange = {
                            productViewModel.onEvent(ProductUiEvent.ChangeComment(it))
                        },
                        sendComment = { productViewModel.sendComment() },
//                        sendReply = { productViewModel.sendReply(it) },
                        onReplyClick = { itemId ->
                            //productViewModel.onEvent(ProductUiEvent.OnReplyClick(itemId))
                            val currentState = lifeCycleOwner.lifecycle.currentState
                            if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                onReplyClick(itemId)
                            }
                        },
//                        userReply = state.value.reply,
//                        onReplyChange = { reply ->
//                            productViewModel.onEvent(ProductUiEvent.ChangeReply(reply))
//                        },
//                        toReplyId = state.value.toReplyId,
                    )
                }
            }
        }
    }
    if (state.value.isSheetOpen) {
        ModalBottomSheet(
            modifier = Modifier,
            sheetState = sheetState,
            onDismissRequest = { productViewModel.onEvent(ProductUiEvent.BargainOptionsClicked) },
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            //windowInsets = BottomSheetDefaults.windowInsets.only(WindowInsetsSides.Bottom)
//            windowInsets = WindowInsets(0.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(start = 16.dp, bottom = 16.dp, end = 16.dp)
            ) {
                BargainOptionsSheet(
                    listedPrice = state.value.listedCashPrice,
                    mrp = state.value.mrp,
                    bargainAmount = state.value.bargainAmount,
                    onBargainAmountChange = {
                        productViewModel.onEvent(ProductUiEvent.ChangeBargainAmount(it))
                    },
                    message = state.value.bargainMessage,
                    onBargainMessageChange = {
                        productViewModel.onEvent(ProductUiEvent.ChangeBargainMessage(it))
                    },
                    onBargainRequest = {
                        val validationResult = productViewModel.validateAll()
                        if (validationResult.isValid) {
                            productViewModel.onEvent(
                                ProductUiEvent.BargainRequest(
                                    state.value.bargainMessage,
                                    state.value.bargainAmount
                                )
                            )
                        } else {
                            Toast.makeText(
                                context,
                                validationResult.errorMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    onClosePopup = { productViewModel.onEvent(ProductUiEvent.BargainOptionsClicked) },
                    fifteenPercentRecommended = state.value.fifteenPercentRecommended ?: Pair("", ""),
                    tenPercentRecommended = state.value.tenPercentRecommended ?: Pair("", ""),
                    currencySelected = state.value.bargainCurrencySelected,
                    onCurrencySelected = {
                        productViewModel.onEvent(ProductUiEvent.BargainCurrencySelectedChange(it))
                    },
                    selectedIndex = state.value.bargainSelectedIndex,
                    onSelectedIndexChange = {
                        productViewModel.onEvent(ProductUiEvent.BargainSelectedChange(it))
                    },
                    //isEditing = false
                )
            }
        }
    }

    if (state.value.isImageOpen) {
        ImagePreviewDialog(
            initialImageIndex = currentIndex,
            images = listOf(
                R.drawable.p1,
                R.drawable.p2,
                R.drawable.p3,
                R.drawable.p4,
                R.drawable.p5,
                R.drawable.p6
            ),
            onDismiss = { productViewModel.onEvent(ProductUiEvent.OnImagePreview) }
        )
    }
}

@Preview(
    showBackground = true,
)
@Composable
fun PreviewProductScreen() {
    SwapGoTheme {
        ProductScreen(
            onReplyClick = {}
        )
    }
}

