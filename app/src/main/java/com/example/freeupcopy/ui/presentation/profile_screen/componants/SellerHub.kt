package com.example.freeupcopy.ui.presentation.profile_screen.componants

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.theme.CardShape
import com.example.freeupcopy.ui.theme.SwapGoTheme

//@Composable
//fun SellerHub(
//    modifier: Modifier = Modifier,
//    isPackingMaterialOn: Boolean,
//    isBundleOffersOn: Boolean,
//    isOnlineModeOn: Boolean,
//    onPackingMaterialClick: () -> Unit,
//    onBundleOffersClick: () -> Unit,
//    onRanksClick: () -> Unit,
//    onOnlineModeClick: () -> Unit
//) {
//    Column(
//        verticalArrangement = Arrangement.Center,
//        modifier = modifier
//            .clip(CardShape.medium)
//            .padding(horizontal = 16.dp)
//            .background(MaterialTheme.colorScheme.surface)
//            .fillMaxWidth(),
//    ) {
//        Text(
//            text = "Seller Hub",
//            fontWeight = FontWeight.Bold,
//            fontSize = 18.sp
//        )
//        Spacer(modifier = Modifier.size(16.dp))
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center
//        ) {
//            SellingToolsButton(
//                modifier = Modifier.weight(1f),
//                painter = painterResource(id = R.drawable.ic_your_orders),
//                label = "Packing Material",
//                isTurnedOn = isPackingMaterialOn,
//                onClick = onPackingMaterialClick
//            )
//            Spacer(modifier = Modifier.size(8.dp))
//            SellingToolsButton(
//                modifier = Modifier.weight(1f),
//                painter = painterResource(id = R.drawable.ic_bundle_offers),
//                label = "Bundle Offers",
//                isTurnedOn = isBundleOffersOn,
//                onClick = onBundleOffersClick
//            )
//        }
//        Spacer(modifier = Modifier.size(8.dp))
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center
//        ) {
//            Column(
//                modifier = Modifier
//                    .weight(1f)
//                    .clip(ButtonShape)
//                    .clickable { onRanksClick() }
//                    .border(
//                        width = 1.dp,
//                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.12f),
//                        shape = ButtonShape
//                    )
//                    .background(MaterialTheme.colorScheme.primaryContainer)
//                    .padding(8.dp)
//                    .heightIn(min = 50.dp),
//                verticalArrangement = Arrangement.Center,
//            ) {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Icon(
//                        painter = painterResource(R.drawable.ic_ranks),
//                        contentDescription = "Ranks"
//                    )
//                    Spacer(modifier = Modifier.size(8.dp))
//                    Text(
//                        text = "Ranks",
//                        fontSize = 14.sp
//                    )
//                }
//            }
//            Spacer(modifier = Modifier.size(8.dp))
//            SellingToolsButton(
//                modifier = Modifier.weight(1f),
//                painter = painterResource(id = R.drawable.ic_online_mode),
//                label = "Online Mode",
//                isTurnedOn = isOnlineModeOn,
//                onClick = onOnlineModeClick
//            )
//        }
//    }
//}

@Composable
fun SellerHub(
    modifier: Modifier = Modifier,
    isCodOn: Boolean,
    isBundleOffersOn: Boolean,
    isOnlineModeOn: Boolean,
    onPackingMaterialClick: () -> Unit,
    onShippingGuideClick: () -> Unit,
    onBundleOffersClick: () -> Unit,
    onRanksClick: () -> Unit,
    onOnlineModeClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .clip(CardShape.medium)
            .padding(horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth(),
    ) {
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "Seller Hub",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.size(2.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .clip(CardShape.medium)
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                    CardShape.medium
                )
        ) {
            SellingToolsButton(
                painter = painterResource(id = R.drawable.ic_your_orders),
                label = "Packing Material",
//                isTurnedOn = isPackingMaterialOn,
                onClick = onPackingMaterialClick,
                tint = Color(0xFF21C2F3),
                description = "Request Supplies",
            )

            HorizontalDivider(
                thickness = 1.dp,
                //color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.12f)
                color = Color.LightGray.copy(0.3f)
            )

            SellingToolsButton(
                painter = painterResource(id = R.drawable.ic_shipping_guide),
                label = "Shipping Guide",
//                isTurnedOn = isPackingMaterialOn,
                onClick = onShippingGuideClick,
                tint = Color(0xFFE8D115),
                description = "Steps to ship your products",
            )

            HorizontalDivider(
                thickness = 1.dp,
                //color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.12f)
                color = Color.LightGray.copy(0.3f)
            )

            SellingToolsButton(
                painter = painterResource(id = R.drawable.ic_bundle_offers),
                label = "Bundle Offers",
                //isTurnedOn = isBundleOffersOn,
                onClick = onBundleOffersClick,
                tint = Color(0xFFB100FF),
                description = "Create special deals",
                status = {
                    Text(
                        text = if (isBundleOffersOn) "ON" else "OFF",
                        fontSize = 12.sp,
                        color = if (isBundleOffersOn) Color(0xFF4CAF50) else Color(0xFFE91E63),
                        fontWeight = FontWeight.W500
                    )
                }
            )

            HorizontalDivider(
                thickness = 1.dp,
                //color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.12f)
                color = Color.LightGray.copy(0.3f)
            )

            SellingToolsButton(
                painter = painterResource(id = R.drawable.ic_cash),
                label = "Cash on Delivery",
//                isTurnedOn = isBundleOffersOn,
                onClick = onBundleOffersClick,
                tint = Color(0xFF9FCB00),
                description = "Enable COD",
                status = {
                    Text(
                        text = if (isCodOn) "ON" else "OFF",
                        fontSize = 12.sp,
                        color = if (isCodOn) Color(0xFF4CAF50) else Color(0xFFE91E63),
                        fontWeight = FontWeight.W500
                    )
                }
            )

            HorizontalDivider(
                thickness = 1.dp,
                color = Color.LightGray.copy(0.3f)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onRanksClick() }
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_ranks),
                    contentDescription = "ranks",
                    tint = Color(0xFFFFA400)
                )

                Spacer(modifier = Modifier.size(16.dp))

                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Seller Rank",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W500,
                        lineHeight = 18.sp
                    )
                    Text(
                        text = "Level 2 Seller",
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                }
            }

            HorizontalDivider(
                thickness = 1.dp,
                color = Color.LightGray.copy(0.3f)
            )


            SellingToolsButton(
                painter = painterResource(id = R.drawable.ic_online_mode),
                label = "Online Mode",
                //isTurnedOn = isOnlineModeOn,
                onClick = onOnlineModeClick,
                description = "Enable Online Mode",
                tint = Color(0xFF4CAF50),
                status = {
                    Text(
                        text = if (isOnlineModeOn) "ON" else "OFF",
                        fontSize = 12.sp,
                        color = if (isOnlineModeOn) Color(0xFF4CAF50) else Color(0xFFE91E63),
                        fontWeight = FontWeight.W500
                    )
                }
            )
        }
    }
}


//@Composable
//fun SellingToolsButton(
//    modifier: Modifier = Modifier,
//    isTurnedOn: Boolean,
//    painter: Painter,
//    label: String,
//    onClick: () -> Unit,
//    tint: Color = MaterialTheme.colorScheme.onPrimaryContainer
//) {
//    Column(
//        modifier = modifier
//            .clip(ButtonShape)
//            .clickable { onClick() }
//            .border(
//                width = 1.dp,
//                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.12f),
//                shape = ButtonShape
//            )
//            .background(MaterialTheme.colorScheme.primaryContainer)
//            .padding(8.dp)
//            .heightIn(min = 50.dp),
//        verticalArrangement = Arrangement.Center,
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Icon(
//                painter = painter,
//                contentDescription = label,
//                tint = tint
//            )
//            Spacer(modifier = Modifier.size(8.dp))
//            Text(
//                text = label,
//                fontSize = 14.sp
//            )
//        }
//        Text(
//            text = if (isTurnedOn) "ON" else "OFF",
//            fontSize = 12.sp,
//            color = if (isTurnedOn) Color(0xFF4CAF50) else Color(0xFFE91E63),
//            fontWeight = FontWeight.W500
//        )
//    }
//}

@Composable
fun SellingToolsButton(
    modifier: Modifier = Modifier,
    painter: Painter,
    label: String,
    description: String,
    status: @Composable (() -> Unit)? = null,
    onClick: () -> Unit,
    tint: Color = MaterialTheme.colorScheme.onPrimaryContainer
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painter,
            contentDescription = label,
            tint = tint
        )

        Spacer(modifier = Modifier.size(16.dp))

        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = label,
                fontSize = 15.sp,
                fontWeight = FontWeight.W500,
                lineHeight = 18.sp
            )
            Text(
                text = description,
                fontSize = 12.sp,
                lineHeight = 18.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        status?.invoke()
    }
}


@Preview(showBackground = true)
@Composable
fun SellingToolsPreview() {
    SwapGoTheme {
        SellerHub(
            isCodOn = true,
            isBundleOffersOn = false,
            isOnlineModeOn = true,
            onPackingMaterialClick = {},
            onBundleOffersClick = {},
            onRanksClick = {},
            onOnlineModeClick = {},
            onShippingGuideClick = {}
        )
    }
}