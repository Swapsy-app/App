package com.example.freeupcopy.ui.presentation.home_screen.componants

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.theme.CashColor1
import com.example.freeupcopy.ui.theme.CashColor2
import com.example.freeupcopy.ui.theme.CoinColor1
import com.example.freeupcopy.ui.theme.CoinColor2

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
            .background(MaterialTheme.colorScheme.surface)
            .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 20.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
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
            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.75f))
    ){
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(100))
                .clickable { onClick() }
                .border(
                    2.5.dp,
                    Brush.linearGradient(
                        colors = listOf(CoinColor1, CoinColor2),
                        start = Offset(0f, 0f),
                        end = Offset(100f, 100f) // Adjust these offsets for tilt
                    ),
                    shape = CircleShape
                )
                .padding(horizontal = 8.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Coin",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = CoinColor2
            )
            Spacer(modifier = Modifier.size(3.5.dp))
            Icon(
                modifier = Modifier.size(22.dp),
                painter = painterResource(id = R.drawable.ic_coin),
                contentDescription = "coin",
                tint = CoinColor2
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
            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.75f))
    ){
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(100))
                .clickable { onClick() }
                .border(
                    2.5.dp,
                    Brush.linearGradient(
                        colors = listOf(
                            CashColor1,
                            CashColor2
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(100f, 100f) // Adjust these offsets for tilt
                    ),
                    shape = CircleShape
                )
                .padding(horizontal = 8.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Cash",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = CashColor2
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