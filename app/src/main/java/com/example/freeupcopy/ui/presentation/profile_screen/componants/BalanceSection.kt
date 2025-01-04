package com.example.freeupcopy.ui.presentation.profile_screen.componants

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.R
import com.example.freeupcopy.domain.enums.Currency
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.CardShape
import com.example.freeupcopy.ui.theme.CoinColor1
import com.example.freeupcopy.ui.theme.NoteContainerLight
import com.example.freeupcopy.ui.theme.SwapsyTheme

@Composable
fun BalanceSection(
    modifier: Modifier = Modifier,
    cashBalance: String,
    coinBalance: String
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            modifier = Modifier
                .clip(ButtonShape)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.12f),
                    shape = ButtonShape
                )
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(vertical = 8.dp)
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${Currency.CASH.symbol}$cashBalance",
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.W500,
                fontSize = 18.sp
            )
            Text(
                text = "Cash Balance",
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
                fontSize = 12.sp,
            )
        }

        Column(
            modifier = Modifier
                .clip(ButtonShape)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.12f),
                    shape = ButtonShape
                )
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(vertical = 8.dp)
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = coinBalance,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.W500,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.size(4.dp))

                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(R.drawable.coin),
                    contentDescription = "coin icon",
                    tint = Color.Unspecified
                )
            }
            Text(
                text = "Coin Balance",
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
                fontSize = 12.sp,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BalanceSectionPreview() {
    SwapsyTheme {
        BalanceSection(
            cashBalance = "200",
            coinBalance = "1600"
        )
    }
}
