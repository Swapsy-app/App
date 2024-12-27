package com.example.freeupcopy.ui.presentation.product_screen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.presentation.product_screen.componants.BargainElement
import com.example.freeupcopy.ui.presentation.product_screen.componants.BargainPopup
import com.example.freeupcopy.ui.presentation.product_screen.componants.Comments
import com.example.freeupcopy.ui.presentation.product_screen.componants.DeliveryTime
import com.example.freeupcopy.ui.presentation.product_screen.componants.ProductDetails
import com.example.freeupcopy.ui.presentation.product_screen.componants.ProductFAB
import com.example.freeupcopy.ui.presentation.product_screen.componants.ProductScreenBottomBar
import com.example.freeupcopy.ui.presentation.product_screen.componants.SellerDetail
import com.example.freeupcopy.ui.theme.SwapsyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    productViewModel: ProductViewModel = viewModel()
) {
    val state = productViewModel.state.collectAsState()
    val lifeCycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            Column {
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

                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.23f)
                )
            }
        },
        bottomBar = {

            ProductScreenBottomBar(
                specialOffer = state.value.specialOffer,
                coinsOffered = state.value.coinsOffered,
                mrp = state.value.priceOriginal,
                priceOffered = state.value.priceOffered,
            )
            
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .clip(RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp))
//                    .background(MaterialTheme.colorScheme.primaryContainer.copy(0.85f))
//                    .windowInsetsPadding(NavigationBarDefaults.windowInsets)
//                    .defaultMinSize(minHeight = 70.dp)
//                    .padding(16.dp),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Column {
//                    Row(
//                        modifier = Modifier
//                            .heightIn(min = 50.dp)
//                            .fillMaxWidth()
//                            .clip(RoundedCornerShape(10.dp))
//                            .clickable { }
//                            .background(MaterialTheme.colorScheme.primary),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.Center
//                    ) {
//                        Text(
//                            text = "₹${state.value.specialOffer[1]}  +  ${state.value.specialOffer[0]}",
//                            color = MaterialTheme.colorScheme.onPrimary,
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 18.sp
//                        )
//                        Spacer(modifier = Modifier.size(4.dp))
//                        Image(
//                            painter = painterResource(id = R.drawable.coin),
//                            contentDescription = "coin",
//                            modifier = Modifier.size(24.dp)
//                        )
//                    }
//                    Spacer(modifier = Modifier.size(8.dp))
//                    Row {
//                        Row(
//                            modifier = Modifier
//                                .weight(0.70f)
//                                .heightIn(min = 50.dp)
//                                .fillMaxWidth()
//                                .clip(RoundedCornerShape(10.dp))
//                                .clickable { }
//                                .border(
//                                    1.dp,
//                                    MaterialTheme.colorScheme.onPrimaryContainer,
//                                    RoundedCornerShape(10.dp)
//                                ),
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.Center
//                        ) {
//                            Text(
//                                text = state.value.coinsOffered,
//                                color = MaterialTheme.colorScheme.onPrimaryContainer,
//                                fontWeight = FontWeight.Bold,
//                                fontSize = 18.sp
//                            )
//                            Spacer(modifier = Modifier.size(4.dp))
//                            Image(
//                                painter = painterResource(id = R.drawable.coin),
//                                contentDescription = "coin",
//                                modifier = Modifier.size(24.dp)
//                            )
//
//                        }
//                        Spacer(modifier = Modifier.size(8.dp))
//                        Row(
//                            modifier = Modifier
//                                .weight(0.70f)
//                                .heightIn(min = 50.dp)
//                                .fillMaxWidth()
//                                .clip(RoundedCornerShape(10.dp))
//                                .clickable { }
//                                .border(
//                                    1.dp,
//                                    MaterialTheme.colorScheme.onPrimaryContainer,
//                                    RoundedCornerShape(10.dp)
//                                ),
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.Center
//                        ) {
//                            Text(
//                                text = "₹" + state.value.priceOriginal,
//                                color = MaterialTheme.colorScheme.onPrimaryContainer,
//                                textDecoration = TextDecoration.LineThrough,
//                                fontWeight = FontWeight.Bold,
//                                fontSize = 18.sp
//                            )
//                            Spacer(modifier = Modifier.size(8.dp))
//                            Text(
//                                text = "₹" + state.value.priceOffered,
//                                color = MaterialTheme.colorScheme.onPrimaryContainer,
//                                fontWeight = FontWeight.Bold,
//                                fontSize = 18.sp
//                            )
//                        }
//                    }
//                }
//
//            }
        },
        floatingActionButton = {
            Row(
                verticalAlignment = Alignment.CenterVertically
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
                Spacer(modifier = Modifier.size(8.dp))
                ProductFAB(
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
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Share,
                            contentDescription = "share icon",
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
                        imageSelected = state.value.imageIndex, // did 1 based indexing to lazy to change it now
                        changeImage = { productViewModel.changeImage(it) },
                        isLiked = state.value.isLiked,
                        numberOfLikes = state.value.likeCounter.toInt(),
                        productLink = state.value.productLink,
                        shareCounter = state.value.shareCounter.toInt()
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
                        onOpenPopup = { productViewModel.onOpenPopup() },
                        onShowMore = {}
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
                        sendReply = { productViewModel.sendReply(it) },
                        onReplyClick = { itemId ->
                            productViewModel.onEvent(ProductUiEvent.OnReplyClick(itemId))
                        },
                        userReply = state.value.reply,
                        onReplyChange = { reply ->
                            productViewModel.onEvent(ProductUiEvent.ChangeReply(reply))
                        },
                        toReplyId = state.value.toReplyId,
                    )
                }
            }
        }
    }
    if (state.value.isPopupOpen) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            BargainPopup(
                isRupeeSelected = state.value.isRupeeSelected,
                optionFocused = state.value.optionFocused,
                listedPrice = state.value.priceOffered,
                recommendation = state.value.recommendation,
                onCoin = { productViewModel.onCoin() },
                onRupee = { productViewModel.onRupee() },
                onFocus0 = { productViewModel.onFocus0() },
                onFocus1 = { productViewModel.onFocus1() },
                onFocus2 = { productViewModel.onFocus2() },
                bargainText = state.value.bargainTextField,
                onChangeBargainText = { productViewModel.onChangeBargainText(it) },
                messageToSeller = state.value.messageToSeller,
                onChangeMessageToSeller = { productViewModel.onChangeMessageToSeller(it) },
                onClosePopup = { productViewModel.onClosePopup() },
            )
        }
    }
}

@Preview(
    showBackground = true,
)
@Composable
fun PreviewProductScreen() {
    SwapsyTheme {
        ProductScreen()
    }
}

