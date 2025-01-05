package com.example.freeupcopy.ui.presentation.profile_screen.componants

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.R
import com.example.freeupcopy.domain.enums.Currency
import com.example.freeupcopy.domain.enums.PricingModel
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.CardShape
import com.example.freeupcopy.ui.theme.LinkColor
import com.example.freeupcopy.ui.theme.SwapGoTheme
import com.example.freeupcopy.ui.theme.TextFieldContainerColor

@Composable
fun ListingItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    productId: String,
    onClick: (String) -> Unit,
    onActionsClick: (String) -> Unit,
    title: String,
    pricingModels: List<PricingModel>,
    cashPrice: String? = null,
    coinPrice: String? = null,
    combinedPrice: Pair<String, String>? = null,
    viewCount: String,
    favoriteCount: String,
    shareCount: String,
    offerCount: String,
    isConfirmPending: Boolean = false,
    isShippingGuide: Boolean = true
) {

//    Card(
//        modifier = modifier.fillMaxWidth(),
//        onClick = {
//            onClick(productId)
//        },
//        shape = RectangleShape,
//        elevation = CardDefaults.cardElevation(0.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.primaryContainer,
//        )
//    ) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onClick(productId) }
            )
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 16.dp, vertical = 10.dp),
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.p3),
                contentDescription = "Product Image",
                modifier = Modifier
                    .size(90.dp)
                    .clip(CardShape.medium),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.size(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
//                        Row(
//                            modifier = Modifier.alpha(0.6f),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Icon(
//                                modifier = Modifier.size(16.dp),
//                                painter = painterResource(id = R.drawable.ic_password_visibility),
//                                contentDescription = "view count",
//                            )
//                            Spacer(modifier = Modifier.size(4.dp))
//                            Text(
//                                text = viewCount,
//                                fontSize = 13.sp,
//                                fontWeight = FontWeight.SemiBold,
//                                lineHeight = 20.sp
//                            )
//                        }

                        Text(
                            text = title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            lineHeight = 18.sp
                        )
                    }
                    IconButton(
                        onClick = { onActionsClick(productId) },
                        modifier = Modifier
                            .size(30.dp)
                            .align(Alignment.Top)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.MoreVert,
                            contentDescription = "more options",
                        )
                    }
                }

                Spacer(modifier = Modifier.size(6.dp))

                Row(
                    modifier = Modifier
                        .alpha(0.75f)
                        .height(IntrinsicSize.Min)
                ) {
                    if (pricingModels.contains(PricingModel.CASH)) {
                        Text(
                            text = "${Currency.CASH.symbol}$cashPrice",
                            fontWeight = FontWeight.W500
                            //fontSize = 15.sp,
                            //lineHeight = 20.sp
                        )
                        if (pricingModels.contains(PricingModel.COINS)) {
                            VerticalDivider(
                                modifier = Modifier.padding(horizontal = 18.dp, vertical = 4.dp),
                                thickness = 1.dp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
                            )
                        }
                    }
                    if (pricingModels.contains(PricingModel.COINS)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "$coinPrice",
                                fontWeight = FontWeight.W500
                                //fontSize = 15.sp,
                                //lineHeight = 20.sp
                            )
                            Spacer(modifier = Modifier.size(2.dp))
                            Icon(
                                modifier = Modifier.size(15.dp),
                                painter = painterResource(id = R.drawable.coin),
                                contentDescription = "coin",
                                tint = Color.Unspecified
                            )
                        }

//                            if (pricingModels.size > 1) {
//                                VerticalDivider(
//                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
//                                    thickness = 1.dp,
//                                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
//                                )
//                            }
                    }
                }

                if (pricingModels.contains(PricingModel.CASH_AND_COINS)) {
                    Row(
                        modifier = Modifier.alpha(0.75f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${Currency.CASH.symbol}${combinedPrice?.first} + ",
                            fontWeight = FontWeight.W500
                            //fontSize = 15.sp,
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${combinedPrice?.second}",
                                fontWeight = FontWeight.W500
                                //fontSize = 15.sp,
                            )
                            Spacer(modifier = Modifier.size(2.dp))
                            Icon(
                                modifier = Modifier.size(15.dp),
                                painter = painterResource(id = R.drawable.coin),
                                contentDescription = "coin",
                                tint = Color.Unspecified
                            )
                        }
                    }
                }
            }
        }
        Column {
            HorizontalDivider(
                modifier = Modifier.padding(top = 6.dp, bottom = 4.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.1f)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                MoreDetails(
                    count = favoriteCount,
                    painter = painterResource(id = R.drawable.ic_favorite),
                    label = "favorite",
                    tint = Color.Red
                )

                MoreDetails(
                    count = shareCount,
                    imageVector = Icons.Outlined.Share,
                    label = "share",
                    tint = LinkColor
                )

                MoreDetails(
                    count = offerCount,
                    painter = painterResource(id = R.drawable.ic_offer),
                    label = "offer",
                    tint = Color(0xFFF38600)
                )

                MoreDetails(
                    count = viewCount,
                    painter = painterResource(id = R.drawable.ic_password_visibility),
                    label = "view count",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                )
            }
            HorizontalDivider(
                modifier = Modifier.padding(top = 4.dp, bottom = 6.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.1f)
            )
        }

        Row(Modifier.fillMaxWidth()) {
            if (isConfirmPending) {
                ListingButton(
                    modifier = Modifier.weight(0.3f),
                    onClick = {},
//                        icon = {
//                            Icon(
//                                //modifier = Modifier.size(20.dp),
//                                painter = painterResource(id = R.drawable.ic_campaign),
//                                contentDescription = "boost",
//                            )
//                        },
                    text = "Cancel",
                    //backgroundColor = MaterialTheme.colorScheme.secondaryContainer.copy(0.5f),
                    //contentColor = Color(0xFF0079C2)
                )
                Spacer(modifier = Modifier.size(12.dp))
                ListingButton(
                    modifier = Modifier.weight(0.7f),
                    onClick = {},
                    icon = {
                        Icon(
                            //modifier = Modifier.size(20.dp),
                            imageVector = Icons.Rounded.Check,
                            contentDescription = "confirm pending",
                        )
                    },
                    text = "Confirm Sell",
                    backgroundColor = Color(0xFFD7FDC1),
                    contentColor = Color(0xFF005400)
                )
                Spacer(modifier = Modifier.size(10.dp))
            } else if (isShippingGuide) {
                ListingButton(
                    modifier = Modifier.weight(0.7f),
                    onClick = {},
                    icon = {
                        Icon(
                            //modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.ic_shipping_guide),
                            contentDescription = null,
                        )
                    },
                    text = "Shipping Guide",
                    backgroundColor = MaterialTheme.colorScheme.tertiary,
                    contentColor =
                    MaterialTheme.colorScheme.onTertiary
                )
            } else {
                ListingButton(
                    modifier = Modifier.weight(1f),
                    onClick = {},
                    icon = {
                        Icon(
                            //modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.ic_campaign),
                            contentDescription = "boost",
                        )
                    },
                    text = "Boost Product",
                    backgroundColor = MaterialTheme.colorScheme.secondaryContainer.copy(0.5f),
                    contentColor = Color(0xFF0079C2)
                )
                Spacer(modifier = Modifier.size(10.dp))
                ListingButton(
                    modifier = Modifier.weight(1f),
                    onClick = {},
                    icon = {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(R.drawable.ic_edit),
                            contentDescription = "edit"
                        )
                    },
                    text = "Edit",
                    backgroundColor = TextFieldContainerColor
                )
            }
        }
//        }
    }
}

@Composable
fun MoreDetails(
    modifier: Modifier = Modifier,
    count: String,
    painter: Painter,
    label: String,
    tint: Color
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painter,
            contentDescription = label,
            tint = tint
        )

        Text(
            text = count,
            fontSize = 13.sp,
            //fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.75f)
        )
    }
}


@Composable
fun MoreDetails(
    modifier: Modifier = Modifier,
    count: String,
    imageVector: ImageVector,
    label: String,
    tint: Color
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            imageVector = imageVector,
            contentDescription = label,
            tint = tint
        )

        Text(
            text = count,
            fontSize = 13.sp,
            //fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.75f)
        )
    }
}


@Composable
fun ListingButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: @Composable (() -> Unit)? = null,
    text: String,
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer
) {
    OutlinedButton(
        modifier = modifier
            .height(45.dp),
        onClick = { onClick() },
        shape = ButtonShape,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = contentColor,
            containerColor = backgroundColor
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            icon?.invoke()
            Text(
                text = text
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProfileProductListingItemPreview() {
    SwapGoTheme {
        ListingItem(
            imageUrl = "",
            onClick = {},
            onActionsClick = {},
            title = "Fidget Spinner Phone With 4G",
            pricingModels = listOf(
                PricingModel.CASH,
                PricingModel.COINS,
                PricingModel.CASH_AND_COINS
            ),
            cashPrice = "100",
            coinPrice = "450",
            combinedPrice = Pair("60", "250"),
            favoriteCount = "6",
            shareCount = "14",
            offerCount = "2",
            productId = "1",
            viewCount = "100"
        )
    }
}