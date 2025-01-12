package com.example.freeupcopy.ui.presentation.profile_screen.componants

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.zIndex
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.theme.BadgeLight
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.CashColor2
import com.example.freeupcopy.ui.theme.LinkColor
import com.example.freeupcopy.ui.theme.SwapGoTheme

@Composable
fun YourPostedProducts(
    modifier: Modifier = Modifier,
    listedCount: String,
    pendingCount: String,
    deliveredCount: String,
    isListedActionRequired: Boolean,
    isPendingActionRequired: Boolean,
    isDeliveredActionRequired: Boolean,
    onPostedProductClick: () -> Unit,
    onListedClick: () -> Unit,
    onPendingClick: () -> Unit,
    onDeliveredClick: () -> Unit
) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    Column(
        modifier = modifier
            .padding(start = 16.dp, end =  16.dp, top = 10.dp),
    ) {
        Card(
            shape = ButtonShape,
            colors = CardDefaults.cardColors(
                containerColor = Color.Black
            ),
            //elevation = CardDefaults.cardElevation(4.dp),
            onClick = {
                val currentState = lifeCycleOwner.lifecycle.currentState
                if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                    onPostedProductClick()
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_store),
                        contentDescription = "store",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "Your Posted Products",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(Modifier.size(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ListedButton(
                modifier = Modifier.weight(1f),
                //backgroundColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                textColor = LinkColor,
                label = "Listed",
                count = listedCount,
                onClick = onListedClick,
                isActionNeeded = isListedActionRequired
            )
            ListedButton(
                modifier = Modifier.weight(1f),
                //backgroundColor = NoteContainerLight.copy(alpha = 0.5f),
                textColor = CashColor2,
                label = "Pending",
                count = pendingCount,
                onClick = onPendingClick,
                isActionNeeded = isPendingActionRequired
            )
            ListedButton(
                modifier = Modifier.weight(1f),
                //backgroundColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f),
                textColor = Color(0xFFF38600),
                label = "Delivered",
                count = deliveredCount,
                onClick = onDeliveredClick,
                isActionNeeded = isDeliveredActionRequired
            )
        }
    }
}

@Composable
fun ListedButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    textColor: Color,
    label: String,
    count: String,
    isActionNeeded: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                //.padding(vertical = 8.dp)
                .fillMaxWidth()
                .clip(ButtonShape)
                .border(
                    width = 1.dp,
                    color = textColor.copy(alpha = 0.3f),
                    shape = ButtonShape
                )
                .clickable { onClick() }
                .background(backgroundColor)
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = count,
                color = textColor,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = label,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
                fontSize = 14.sp,
                fontWeight = FontWeight.W500
            )
        }
        if (isActionNeeded) {
            Box(
                modifier = Modifier
                    .offset(x = 6.dp, y = (-6).dp)
                    .zIndex(1f)
                    .clip(CircleShape)
                    .size(16.dp)
                    .background(BadgeLight)
                    .align(Alignment.TopEnd)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun YourStoreItemsSectionPreview() {
    SwapGoTheme {
        YourPostedProducts(
            listedCount = "10",
            pendingCount = "5",
            deliveredCount = "3",
            isListedActionRequired = true,
            isPendingActionRequired = false,
            isDeliveredActionRequired = true,
            onPostedProductClick = {},
            onListedClick = {},
            onPendingClick = {},
            onDeliveredClick = {}
        )
    }
}