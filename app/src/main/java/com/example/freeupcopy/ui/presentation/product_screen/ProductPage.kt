@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.freeupcopy.ui.presentation.product_page





import android.content.Intent
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.freeupcopy.R
import com.example.freeupcopy.domain.model.BargainOffers
import com.example.freeupcopy.domain.model.Comment
import com.example.freeupcopy.domain.model.Reply
import com.example.freeupcopy.ui.presentation.product_page.componants.Comments
import com.example.freeupcopy.ui.presentation.product_page.componants.DeliveryTime
import com.example.freeupcopy.ui.presentation.product_page.componants.ProductDetails
import com.example.freeupcopy.ui.presentation.product_page.componants.SellerDetail
import com.example.freeupcopy.ui.presentation.product_screen.componants.BargainElement
import com.example.freeupcopy.ui.presentation.product_screen.componants.BargainPopup
import com.example.freeupcopy.ui.theme.SwapsyTheme
import com.example.freeupcopy.ui.viewmodel.ProductScreenViewModel

@Composable
fun ProductScreen(
    productScreenViewModel: ProductScreenViewModel = viewModel()
) {
    val productScreenUiState = productScreenViewModel.productScreenUiState.collectAsState()
    val lifeCycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(
                            horizontalArrangement = Arrangement.Absolute.Right,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            ProductTopBarButtons(
                                onShareClick = {
                                    val shareIntent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        putExtra(Intent.EXTRA_TEXT, productScreenUiState.value.productLink)
                                        type = "text/plain"
                                    }
                                    context.startActivity(Intent.createChooser(shareIntent, "Share via"))
                                },
                                onCart = { /* todo */ }
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            productScreenViewModel.onClose()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "close"
                        )
                    }
                }
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(0.85f))
                    .windowInsetsPadding(NavigationBarDefaults.windowInsets)
                    .defaultMinSize(minHeight = 70.dp)

                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .heightIn(min = 50.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { }
                            .background(MaterialTheme.colorScheme.primary),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(
                            text = "₹${productScreenUiState.value.specialOffer[1]}  +  ${productScreenUiState.value.specialOffer[0]}",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Image(
                            painter = painterResource(id = R.drawable.coin),
                            contentDescription = "coin",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    Row {
                        Row(
                            modifier = Modifier
                                .weight(0.70f)
                                .heightIn(min = 50.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { }
                                .border(
                                    1.dp,
                                    MaterialTheme.colorScheme.onPrimaryContainer,
                                    RoundedCornerShape(10.dp)
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = productScreenUiState.value.coinsOffered,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.size(4.dp))
                            Image(
                                painter = painterResource(id = R.drawable.coin),
                                contentDescription = "coin",
                                modifier = Modifier.size(24.dp)
                            )

                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        Row(
                            modifier = Modifier
                                .weight(0.70f)
                                .heightIn(min = 50.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { }
                                .border(
                                    1.dp,
                                    MaterialTheme.colorScheme.onPrimaryContainer,
                                    RoundedCornerShape(10.dp)
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "₹" + productScreenUiState.value.priceOriginal,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                textDecoration = TextDecoration.LineThrough,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(
                                text = "₹" + productScreenUiState.value.priceOffered,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                    }
                }

            }
        },
        floatingActionButton = {
            Row(

            ) {
                Box(
                    modifier = Modifier
                        .border(
                            1.dp,
                            Color.Gray,
                            RoundedCornerShape(10.dp)
                        )
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Icon(
                        imageVector = if (productScreenUiState.value.isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "like icon",
                        tint = Color.Red,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { productScreenViewModel.likeProduct() }
                    )
                }
                Spacer(modifier = Modifier.size(8.dp))
                Box(
                    modifier = Modifier
                        .border(
                            1.dp,
                            Color.Gray,
                            RoundedCornerShape(10.dp)
                        )
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Share,
                        contentDescription = "share icon",
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                val shareIntent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(
                                        Intent.EXTRA_TEXT,
                                        productScreenUiState.value.productLink
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
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Start
    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .fillMaxWidth()
        ) {
            Divider(
                Modifier
                    .fillMaxWidth(),
                thickness = 1.dp
            )
            Spacer(modifier = Modifier.size(16.dp))
            LazyColumn(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .fillMaxWidth()
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (productScreenUiState.value.isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "like icon",
                            tint = Color.Red,
                            modifier = Modifier.clickable { productScreenViewModel.likeProduct() }
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = productScreenUiState.value.likeCounter,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.size(16.dp))
                        Icon(
                            imageVector = Icons.Rounded.Share,
                            contentDescription = "share icon",
                            modifier = Modifier.clickable {
                                val shareIntent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, productScreenUiState.value.productLink)
                                    type = "text/plain"
                                }
                                context.startActivity(Intent.createChooser(shareIntent, "Share via"))
                            }
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = productScreenUiState.value.shareCounter,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
                item { Spacer(modifier = Modifier.size(16.dp)) }
                item { ProductDetails(
                    innerPaddingValues = paddingValues,
                    imageSelected = productScreenUiState.value.imageIndex, // did 1 based indexing to lazy to change it now
                    changeImage = { productScreenViewModel.changeImage(it) }
                    )
                }
                item { Spacer(modifier = Modifier.size(16.dp)) }
                item { SellerDetail(
                    isFollowed = productScreenUiState.value.isFollowed,
                    onFollow = { productScreenViewModel.onFollow() }
                ) }
                item { Spacer(modifier = Modifier.size(16.dp)) }
                item {
                    DeliveryTime(
                        innerPaddingValues = paddingValues,
                        pinCode = productScreenUiState.value.pincode,
                        onPinCodeChange = { productScreenViewModel.changePincode(it) }
                    )
                }
                item { Spacer(modifier = Modifier.size(16.dp)) }
                item {
                    BargainElement(
                        bargainOffers = productScreenUiState.value.bargainOffersList,
                        innerPaddingValues = paddingValues,
                        onOpenPopup = { productScreenViewModel.onOpenPopup() }
                    )
                }
                item { Spacer(modifier = Modifier.size(16.dp)) }
                item {
                    Comments(
                        comment = productScreenUiState.value.comments,
                        userComment = productScreenUiState.value.comment,
                        onCommentChange = { productScreenViewModel.changeComment(it) },
                        sendComment = { productScreenViewModel.sendComment() },
                        commentReplying = productScreenUiState.value.commentReplying,
                        sendReply = { productScreenViewModel.sendReply(it) },
                        onReply = { productScreenViewModel.clickOnReply(it)},
                        userReply = productScreenUiState.value.reply,
                        onReplyChange = { productScreenViewModel.changeReply(it) }
                    )
                }
            }
        }
    }
    if(productScreenUiState.value.isPopupOpen){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            BargainPopup(
                isRupeeSelected = productScreenUiState.value.isRupeeSelected,
                optionFocused = productScreenUiState.value.optionFocused,
                listedPrice = productScreenUiState.value.priceOffered,
                recommendation = productScreenUiState.value.recommendation,
                onCoin = { productScreenViewModel.onCoin() },
                onRupee = { productScreenViewModel.onRupee() },
                onFocus0 = { productScreenViewModel.onFocus0() },
                onFocus1 = { productScreenViewModel.onFocus1() },
                onFocus2 = { productScreenViewModel.onFocus2() },
                bargainText = productScreenUiState.value.bargainTextField,
                onChangeBargainText = { productScreenViewModel.onChangeBargainText(it) },
                messageToSeller = productScreenUiState.value.messageToSeller,
                onChangeMessageToSeller = { productScreenViewModel.onChangeMessageToSeller(it) },
                onClosePopup = { productScreenViewModel.onClosePopup() },
            )
        }
    }
}
@Composable
fun ProductTopBarButtons(
    onCart : () -> Unit,
    onShareClick: () -> Unit
){
    Row {
        Icon(
            imageVector = Icons.Rounded.ShoppingCart,
            contentDescription = "cart button",
            Modifier
                .clickable {
                    onCart()
                }
        )
        Spacer(modifier = Modifier.size(16.dp))
        Icon(
            imageVector = Icons.Rounded.Share,
            contentDescription = "share button",
            Modifier
                .clickable {
                    onShareClick()
                }
        )
    }
}

@Preview(
    showBackground = true,

)

@Composable
fun PreviewProductScreen(){
    SwapsyTheme {
        ProductScreen()
    }
}

