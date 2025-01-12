package com.example.freeupcopy.ui.presentation.profile_screen.componants

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.theme.CardShape
import com.example.freeupcopy.ui.theme.SwapGoTheme

@Composable
fun ProfileBanner(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onWishlistClick: () -> Unit,
    onYourOrdersClick: () -> Unit,
    onYourOffersClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(CardShape.medium)
            .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            .border(
                1.dp,
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                CardShape.medium
            )
            .background(MaterialTheme.colorScheme.surface),
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .fillMaxWidth()
                .height(100.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f),
                            MaterialTheme.colorScheme.secondaryContainer
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                    ),
                )
        )

        BannerButton(
            modifier = Modifier,
            painter = painterResource(id = R.drawable.ic_offer),
            label = "Your Offers",
            tint = Color(0xFFF38600),
            onClick = onYourOffersClick
        )
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.12f)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            BannerButton(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(bottomStart = 16.dp)),
                painter = painterResource(id = R.drawable.ic_your_orders),
                label = "Your Orders",
                tint = Color(0xFF2196F3),
                onClick = onYourOrdersClick
            )
            VerticalDivider(
                modifier = Modifier.padding(vertical = 10.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.12f)
                )
            BannerButton(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(bottomEnd = 16.dp)),
                painter = painterResource(id = R.drawable.ic_favorite),
                label = "Wishlist",
                tint = Color(0xFFE91E63),
                onClick = onWishlistClick
            )
        }
    }
}

@Composable
fun BannerButton(
    modifier: Modifier = Modifier,
    painter: Painter,
    label: String,
    tint: Color,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable { }
            .heightIn(min = 50.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(start = 16.dp, end = 6.dp)
    ) {
        Icon(
            painter = painter,
            contentDescription = label,
            tint = tint
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileBannerPreview() {
    SwapGoTheme {
        ProfileBanner(
            onClick = {},
            onWishlistClick = {},
            onYourOrdersClick = {},
            onYourOffersClick = {}
        )
    }
}