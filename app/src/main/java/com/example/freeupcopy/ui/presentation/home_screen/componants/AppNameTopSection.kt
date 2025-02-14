package com.example.freeupcopy.ui.presentation.home_screen.componants

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.presentation.home_screen.HomeScreen
import com.example.freeupcopy.ui.theme.CardShape
import com.example.freeupcopy.ui.theme.CashColor1
import com.example.freeupcopy.ui.theme.CashColor2
import com.example.freeupcopy.ui.theme.CoinColor1
import com.example.freeupcopy.ui.theme.CoinColor2
import com.example.freeupcopy.ui.theme.SwapGoTheme

@Composable
fun AppNameTopSection(
    modifier: Modifier = Modifier,
    onCoinClick: () -> Unit,
    onCashClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .offset()
            //.background(MaterialTheme.colorScheme.surface)
            .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 10.dp),
        verticalAlignment = Alignment.Bottom
    ) {
//        Box(
//            modifier = Modifier
//                .clip(CardShape.medium)
//                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.9f))
//                .padding(horizontal = 12.dp, vertical = 4.dp),
//            contentAlignment = Alignment.Center
//        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_logo_full),
                contentDescription = "SwapGo",
                tint = Color.Unspecified,
                modifier = Modifier.width(120.dp)
            )
//        }
        Spacer(modifier = Modifier.weight(1f))
        CoinButton {
            onCoinClick()
        }
        Spacer(modifier = Modifier.size(8.dp))
        CashButton {
            onCashClick()
        }
    }
}


@Composable
fun CoinButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(100))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFFFFAAB).copy(0.15f),
                        CoinColor2.copy(0.2f)
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(100f, 100f) // Adjust these offsets for tilt
                ),
            )
    ){
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(100))
                .clickable { onClick() }
//                .border(
//                    1.dp,
//                    Brush.linearGradient(
//                        colors = listOf(CoinColor1, CoinColor2),
//                        start = Offset(0f, 0f),
//                        end = Offset(100f, 100f) // Adjust these offsets for tilt
//                    ),
//                    shape = CircleShape
//                )
                .padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Coins",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFAF7100)
            )
            Spacer(modifier = Modifier.size(3.5.dp))
            Icon(
                modifier = Modifier.size(22.dp),
                painter = painterResource(id = R.drawable.coin),
                contentDescription = "coin",
                tint = Color.Unspecified
            )
        }
    }
}


@Composable
fun CashButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(100))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFE2FF9D).copy(0.15f),
                        CashColor2.copy(0.15f)
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(100f, 100f) // Adjust these offsets for tilt
                )
            )
    ){
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(100))
                .clickable { onClick() }
//                .border(
//                    1.dp,
//                    Brush.linearGradient(
//                        colors = listOf(
//                            CashColor1,
//                            CashColor2
//                        ),
//                        start = Offset(0f, 0f),
//                        end = Offset(100f, 100f) // Adjust these offsets for tilt
//                    ),
//                    shape = CircleShape
//                )
                .padding(horizontal = 8.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Cash",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF008300)
            )
            Spacer(modifier = Modifier.size(3.5.dp))
            Icon(
                modifier = Modifier.size(22.dp),
                painter = painterResource(id = R.drawable.ic_cash),
                contentDescription = "coin",
                tint = CashColor2
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHomeScreen() {
    SwapGoTheme {
        HomeScreen(
            lazyColumnState = LazyListState(),
            onSearchBarClick = {},
            onInboxClick = {},
            onCartClick = {},
            onCoinClick = {},
            onCashClick = {}
        )
    }
}