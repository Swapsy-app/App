package com.example.freeupcopy.ui.presentation.product_card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.example.freeupcopy.R
import com.example.freeupcopy.common.Constants.BASE_URL_AVATAR
import com.example.freeupcopy.data.remote.dto.product.User
import com.example.freeupcopy.data.remote.dto.sell.Size
import com.example.freeupcopy.data.remote.dto.sell.toSizeString
import com.example.freeupcopy.ui.theme.CardShape
import com.example.freeupcopy.ui.theme.CashColor1
import com.example.freeupcopy.ui.theme.CashColor2
import com.example.freeupcopy.ui.theme.CoinColor1
import com.example.freeupcopy.ui.theme.SwapGoTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    brand: String?,
    title: String,
    productThumbnail: String?,
    size: Size?,
    cashPrice: String?,
    coinsPrice: String?,
    combinedPrice: Pair<String, String>?,
    mrp: String,
    badge: String?,
    availablePurchaseOptions: List<Boolean> = listOf(
        (cashPrice != null),
        (coinsPrice != null),
        (combinedPrice != null)
    ),
    user: User,
    isLiked: Boolean,
    onLikeClick: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .width(180.dp)
            .clickable (
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    onClick()
                }
            ),
        shape = CardShape.small,
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.1f)
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column {
            Box {
                SubcomposeAsyncImage(
                    model = productThumbnail,
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.75f),
                    contentScale = ContentScale.Crop,
                    loading = {
                        // Display a loading indicator while the image is being fetched.
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_logo_full),
                                contentDescription = null
                            )
                        }
                    },
                    error = {
                        // Fallback image if loading fails.
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_logo_full),
                                contentDescription = null
                            )
                        }
                    }
                )
                IconButton(
                    onClick = { onLikeClick() },
                    modifier = Modifier
                        .align(Alignment.TopEnd),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.White.copy(alpha = 0.5f),
                        contentColor = if (!isLiked) MaterialTheme.colorScheme.onPrimaryContainer else Color.Red
                    )
                ) {
                    Icon(
                        imageVector = if (isLiked) Icons.Default.Favorite else Icons.Rounded.FavoriteBorder,
                        contentDescription = "Favorite Icon"
                    )
                }
            }

            Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
                if (brand != null) {
                    Text(
                        text = brand.toString(), // Replace with product name
                        fontWeight = FontWeight.W500,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.4f),
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 13.sp
                    )
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    text = title, // Replace with product name
                    //fontWeight = FontWeight.W500,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 15.sp,
                    lineHeight = 20.sp
                )

                if (size != null) {
                    Text(
                        text = "Size: ${size.toSizeString()}", // Replace with product details
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.4f),
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                if (availablePurchaseOptions[0]) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "₹${cashPrice}", // Replace with discounted price
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W500
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Row {
                            Text(
                                text = "₹$mrp", // Replace with discounted price
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.4f),
                                textDecoration = TextDecoration.LineThrough,
                                fontSize = 13.sp
                            )
                        }
                    }
                } else if (availablePurchaseOptions[1]) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "$coinsPrice", // Replace with discounted price
                            color = CashColor2,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W500
                        )

                        Spacer(modifier = Modifier.size(2.dp))

                        Icon(
                            modifier = Modifier.size(16.dp),
                            painter = painterResource(id = R.drawable.coin),
                            contentDescription = "coin",
                            tint = Color.Unspecified
                        )

                        Spacer(modifier = Modifier.width(8.dp))
                        Row {
                            Text(
                                text = "₹$mrp", // Replace with discounted price
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.4f),
                                textDecoration = TextDecoration.LineThrough,
                                fontSize = 13.sp
                            )
                        }
                    }
                } else if (availablePurchaseOptions[2]) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "₹${combinedPrice?.first}", // Replace with discounted price
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W500
                        )

                        Text(
                            text = " + ", // Replace with discounted price
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W500
                        )

                        Text(
                            text = "${combinedPrice?.second}", // Replace with discounted price
                            color = CashColor2,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W500
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

                val numberOfOptions = availablePurchaseOptions.count { it }
                if (numberOfOptions > 1) {

                    Column(
                        Modifier.padding(top = 8.dp)
                    ) {
                        Text(
                            text = "Also available in:",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer

                        )
                        Spacer(Modifier.size(2.dp))
                        FlowRow (horizontalArrangement = Arrangement.spacedBy(4.dp)) {

                            // Coin option
                            if (availablePurchaseOptions[0] && availablePurchaseOptions[1]) {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(CoinColor1.copy(0.25f))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Icon(
                                        modifier = Modifier.size(16.dp),
                                        painter = painterResource(id = R.drawable.coin),
                                        contentDescription = "coin",
                                        tint = Color.Unspecified
                                    )
                                    Spacer(Modifier.size(2.dp))
                                    Text(
                                        text = "Coins",
                                        fontSize = 10.sp
                                    )
                                }
                            }

                            // Combined option
                            if (availablePurchaseOptions[2]) {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(CashColor1.copy(0.3f))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Box {
                                        Icon(
                                            modifier = Modifier.size(16.dp),
                                            painter = painterResource(id = R.drawable.ic_cash),
                                            contentDescription = "cash",
                                            tint = CashColor2
                                        )
                                        Icon(
                                            modifier = Modifier
                                                .size(14.dp)
                                                .offset(x = 7.dp, y = 4.dp),
                                            painter = painterResource(id = R.drawable.coin),
                                            contentDescription = "coin",
                                            tint = Color.Unspecified
                                        )
                                    }
                                    Spacer(Modifier.size(6.dp))
                                    Text(
                                        text = "Cash + Coins",
                                        fontSize = 10.sp,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }
                }



                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
//                    Icon(
//                        modifier = Modifier.size(20.dp),
//                        imageVector = Icons.Default.AccountCircle,
//                        contentDescription = "profile picture",
//                    )
                    SubcomposeAsyncImage(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = {
//                                    onUserClick(comment.userId._id)
                                }
                            ),
                        model = BASE_URL_AVATAR + user.avatar,
                        loading = {
                            painterResource(id = R.drawable.im_user)
                        },
                        error = {
                            painterResource(id = R.drawable.im_user)
                        },
                        contentDescription = "profile",
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = user.username,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun PreviewProductCard() {
//    SwapGoTheme {
//        ProductCard(
//            brand = "Adidas",
//            title = "Adidas Bomber Jacket",
//            size = "40 inches",
//            productThumbnail = painterResource(id = R.drawable.bomber_jacket),
//            cashPrice = "2000",
//            coinsPrice = "1000",
//            combinedPrice = Pair("4000", "2000"),
//            mrp = "4000",
//            badge = "Trusted",
//            isLiked = true,
//            onLikeClick = {},
//        )
//    }
//}