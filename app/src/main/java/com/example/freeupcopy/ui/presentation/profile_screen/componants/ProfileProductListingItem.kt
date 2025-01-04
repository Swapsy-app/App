package com.example.freeupcopy.ui.presentation.profile_screen.componants

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.freeupcopy.ui.theme.SwapsyTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileProductListingItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    productId: String,
    onClick: (String) -> Unit,
    title: String,
    pricingModels: List<PricingModel>,
    cashPrice: String? = null,
    coinPrice: String? = null,
    combinedPrice: Pair<String, String>? = null,
    favoriteCount: String,
    shareCount: String,
    offerCount: String
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = {
            onClick(productId)
        },
        shape = CardShape.medium,
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        )
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp),
        ) {
            Row{
                Image(
                    painter = painterResource(id = R.drawable.p3),
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .size(80.dp)
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
                        Text(
                            text = title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = { },
                            modifier = Modifier.size(30.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.MoreVert,
                                contentDescription = "more options",
                            )
                        }
                    }
                    FlowRow(Modifier.height(IntrinsicSize.Min)) {
                        if(pricingModels.contains(PricingModel.CASH)) {
                            Text(
                                text = "${Currency.CASH.symbol}$cashPrice",
                                fontSize = 14.sp,
                            )
                            if (pricingModels.size > 1) {
                                VerticalDivider(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    thickness = 1.dp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
                                )
                            }
                        }
                        if (pricingModels.contains(PricingModel.COINS)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    modifier = Modifier.size(15.dp),
                                    painter = painterResource(id = R.drawable.coin),
                                    contentDescription = "coin",
                                    tint = Color.Unspecified
                                )
                                Spacer(modifier = Modifier.size(2.dp))
                                Text(
                                    text = "$coinPrice",
                                    fontSize = 14.sp,
                                )
                            }

                            if (pricingModels.size > 1) {
                                VerticalDivider(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    thickness = 1.dp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
                                )
                            }
                        }

                        if (pricingModels.contains(PricingModel.CASH_AND_COINS)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${Currency.CASH.symbol}${combinedPrice?.first} + ",
                                    fontSize = 14.sp,
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        modifier = Modifier.size(15.dp),
                                        painter = painterResource(id = R.drawable.coin),
                                        contentDescription = "coin",
                                        tint = Color.Unspecified
                                    )
                                    Spacer(modifier = Modifier.size(2.dp))
                                    Text(
                                        text = "${combinedPrice?.second}",
                                        fontSize = 14.sp,
                                    )
                                }
                            }
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
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
                    }
                }
            }

            Spacer(modifier = Modifier.size(8.dp))

            Row(Modifier.fillMaxWidth()) {
                ListingButton(
                    modifier = Modifier.weight(1f),
                    onClick = {},
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_campaign),
                            contentDescription = "promote",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    },
                    text = "Promote"
                )
                Spacer(modifier = Modifier.size(8.dp))
                ListingButton(
                    modifier = Modifier.weight(1f),
                    onClick = {},
                    text = "Edit",
                )
            }
        }
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
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(14.dp),
            painter = painter,
            contentDescription = label,
            tint = tint
        )

        Text(
            text = count,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
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
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(14.dp),
            imageVector = imageVector,
            contentDescription = label,
            tint = tint
        )

        Text(
            text = count,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
        )
    }
}


@Composable
fun ListingButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: @Composable (() -> Unit)? = null,
    text: String
) {
    OutlinedButton (
        modifier = modifier
            .height(50.dp),
        onClick = { onClick() },
        shape = ButtonShape,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
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
    SwapsyTheme {
        ProfileProductListingItem(
            imageUrl = "",
            onClick = {},
            title = "Fidget Spinner Phone",
            pricingModels = listOf(PricingModel.CASH, PricingModel.COINS, PricingModel.CASH_AND_COINS),
            cashPrice = "100",
            coinPrice = "450",
            combinedPrice = Pair("100", "450"),
            favoriteCount = "6",
            shareCount = "14",
            offerCount = "2",
            productId = "1"
        )
    }
}