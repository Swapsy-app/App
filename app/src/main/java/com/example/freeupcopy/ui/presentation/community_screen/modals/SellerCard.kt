package com.example.freeupcopy.ui.presentation.community_screen.modals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil.compose.SubcomposeAsyncImage
import com.example.freeupcopy.R
import com.example.freeupcopy.common.Constants
import com.example.freeupcopy.common.Constants.BASE_URL_AVATAR
import com.example.freeupcopy.data.remote.dto.your_profile.FollowUser
import com.example.freeupcopy.ui.theme.SwapGoTheme

@Composable
fun SellerCard(
    modifier: Modifier = Modifier,
    isOnline: Boolean,
    sellerUsername: String,
    sellerRating: String,
    followUser: FollowUser?,
    onSellerClick: () -> Unit
) {
    val lifeCycleOwner = LocalLifecycleOwner.current

    Box(modifier = modifier) {
        ElevatedCard(
            modifier = Modifier,
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            onClick = {
                val currentState = lifeCycleOwner.lifecycle.currentState
                if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                    onSellerClick()
                }
            }
        ) {
            Column(
                Modifier.fillMaxWidth().padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        Box {
                            if (followUser != null) {
                                SubcomposeAsyncImage(
                                    modifier = Modifier
                                        .padding(start = 6.dp, end = 4.dp)
                                        .size(50.dp)
                                        .clip(CircleShape),
                                    model = BASE_URL_AVATAR +  followUser.avatar,
                                    loading = {
                                        painterResource(id = R.drawable.im_user)
                                    },
                                    error = {
                                        painterResource(id = R.drawable.im_user)
                                    },
                                    contentDescription = "profile",
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Icon(
                                    modifier = Modifier
                                        .padding(start = 6.dp, end = 4.dp)
                                        .size(50.dp),
                                    imageVector = Icons.Rounded.AccountCircle,
                                    contentDescription = "user profile",
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.15f)
                                )
                            }
                            if (isOnline){
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .background(Color.Green, CircleShape)
                                        .align(Alignment.BottomEnd),
                                )
                            }
                        }
                        Spacer(Modifier.size(16.dp))
                        Column {
                            Text(
                                text = sellerUsername
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    modifier = Modifier.size(17.dp),
                                    tint = Color.Unspecified,
                                    painter = painterResource(R.drawable.ic_star),
                                    contentDescription = "star icon",
                                )
                                Spacer(Modifier.size(4.dp))
                                Text(
                                    text = sellerRating
                                )
                            }
                        }
                    }
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                        contentDescription = "view profile",
                    )
                }
                Spacer(Modifier.size(8.dp))
                Text(
                    text = "Recent Uploads",
                    fontWeight = FontWeight.Thin,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
//                    ProductCard(
//                        modifier = Modifier.width(170.dp),
//                        productImageModifier = Modifier.height(120.dp),
//                        isBigCard = false,
//                        companyName = "Adidas",
//                        productName = "Adidas Bomber Jacket",
//                        size = "40 inches",
//                        productThumbnail = painterResource(id = R.drawable.bomber_jacket),
//                        priceOffered = null,
//                        coinsOffered = null,
//                        specialOffer = listOf("4000","2000"),
//                        priceOriginal = "2000",
//                        badge = "Trusted",
//                        isLiked = true,
//                        onLikeClick = {},
//                        priorityPriceType = null,
//                        isClothes = true
//                    )
//
//                    ProductCard(
//                        modifier = Modifier.width(170.dp),
//                        productImageModifier = Modifier.height(120.dp),
//                        isBigCard = false,
//                        companyName = "Adidas",
//                        productName = "Adidas Bomber Jacket",
//                        size = "40 inches",
//                        productThumbnail = painterResource(id = R.drawable.bomber_jacket),
//                        priceOffered = null,
//                        coinsOffered = null,
//                        specialOffer = listOf("4000","2000"),
//                        priceOriginal = "2000",
//                        badge = "Trusted",
//                        isLiked = true,
//                        onLikeClick = {},
//                        priorityPriceType = null,
//                        isClothes = true
//                    )
                }
            }
        }
    }
}

@Preview(showBackground = true) @Composable
fun PreviewSellerCard(){
    SwapGoTheme  {
        SellerCard(
            isOnline = true,
            sellerUsername = "they.call.me.zoro",
            sellerRating = "4.77",
            followUser = null,
            onSellerClick = { /*TODO*/ }
        )
    }
}