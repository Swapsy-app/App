package com.example.freeupcopy.ui.presentation.cash_screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.freeupcopy.domain.enums.Currency
import com.example.freeupcopy.ui.presentation.search_screen.SearchScreen
import com.example.freeupcopy.ui.theme.CardShape
import com.example.freeupcopy.ui.theme.CashColor1
import com.example.freeupcopy.ui.theme.NoteContainerLight
import com.example.freeupcopy.ui.theme.RecommendedContainerColor
import com.example.freeupcopy.ui.theme.SwapGoTheme
import com.example.freeupcopy.utils.dashedBorder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CashScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    val lifeCycleOwner = LocalLifecycleOwner.current

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = "Cash Balance",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
//                        if (state.isLoading) {
//                            CircularProgressIndicator(
//                                modifier = Modifier.size(24.dp),
//                                color = MaterialTheme.colorScheme.onPrimaryContainer
//                            )
//                        }
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            val currentState = lifeCycleOwner.lifecycle.currentState
                            if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                onBack()
                            }
                        }) {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                                contentDescription = "back"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )

                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                CashBalanceCard()

                Spacer(Modifier.size(8.dp))

                EarningsInfoSection()
            }
        }
    }
}

@Composable
fun CashBalanceCard(
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .clip(CardShape.medium)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primaryContainer,
//                        RecommendedContainerColor.copy(0.095f),
//                        MaterialTheme.colorScheme.secondaryContainer.copy(0.5f)
                        CashColor1.copy(0.15f)
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                )
            )
//            .border(
//                width = 1.dp,
//                color = MaterialTheme.colorScheme.primary.copy(0.5f),
//                shape = CardShape.medium
//            )
            .dashedBorder(
                color = MaterialTheme.colorScheme.primary,
                shape = CardShape.medium,
            )
            .padding(16.dp)
    ) {
        Column {

            Row(verticalAlignment = Alignment.Top) {
                Column {
                    Text(
                        text = "Your Balance",
//                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.5f),
                    )

                    Text(
                        text = "${Currency.CASH.symbol}20",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp
                    )
                }

                Spacer(Modifier.weight(1f))

                OutlinedButton(
                    onClick = {}
                ) {
                    Text("Withdraw amount")
                }
            }
            Spacer(Modifier.size(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your Total Earnings: ",
                    fontSize = 14.sp
                )
                Text(
                    text = "-${Currency.CASH.symbol}120",
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun EarningsInfoSection() {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .clip(CardShape.medium)
            .background(MaterialTheme.colorScheme.primaryContainer),
//            .background(MaterialTheme.colorScheme.secondaryContainer.copy(0.4f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Header with icon
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "How to Receive Earnings",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            // Explanation text
            Text(
                text = "Earning will be added to your cash balance once your order is completed. " +
                        "If buyer raises any issue, it will be added after the issue is resolved.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "You can withdraw your balance anytime to your bank account, UPI or AmazonPay ID.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Order Delivery Flow Diagram
            OrderDeliveryFlow()
        }
    }
}

@Composable
fun OrderDeliveryFlow() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
    ) {
        // Tree connection lines
        Canvas(modifier = Modifier.fillMaxSize()) {
            val centerX = size.width / 2
            val startY = 50f
            val middleY = 120f
            val endY = 190f

            // Vertical line from "Order Delivered" to fork point
            drawLine(
                color = Color.Gray,
                start = Offset(centerX, startY),
                end = Offset(centerX, middleY - 20),
                strokeWidth = 2f,
                cap = StrokeCap.Round
            )

            // Left branch
            drawLine(
                color = Color.Gray,
                start = Offset(centerX, middleY - 20),
                end = Offset(centerX - size.width / 4, middleY),
                strokeWidth = 2f,
                cap = StrokeCap.Round
            )

            // Right branch
            drawLine(
                color = Color.Gray,
                start = Offset(centerX, middleY - 20),
                end = Offset(centerX + size.width / 4, middleY),
                strokeWidth = 2f,
                cap = StrokeCap.Round
            )

            // Left vertical to check icon
            drawLine(
                color = Color.Gray,
                start = Offset(centerX - size.width / 4, middleY),
                end = Offset(centerX - size.width / 4, endY - 20),
                strokeWidth = 2f,
                cap = StrokeCap.Round
            )

            // Right vertical to check icon
            drawLine(
                color = Color.Gray,
                start = Offset(centerX + size.width / 4, middleY),
                end = Offset(centerX + size.width / 4, endY - 20),
                strokeWidth = 2f,
                cap = StrokeCap.Round
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Order Delivered Header
            Text(
                text = "Order Delivered",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Flow diagram
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Left branch - Positive review
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Buyer gives\npositive review",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

//                    Spacer(modifier = Modifier.height(24.dp))
                    VerticalDivider(
                        modifier = Modifier
                            .height(24.dp)
//                            .offset(x=10.dp)
                        ,
                        thickness = 1.dp,
                        color = Color.Gray
//                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                    )

                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Add to balance\ninstantly",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }

                // Right branch - Other reviews
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Neutral, negative\nor no review",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    VerticalDivider(
                        modifier = Modifier
                            .height(48.dp),
                        thickness = 1.dp,
                        color = Color.Gray
//                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                    )

                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Add to balance\non the 3rd day",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    SwapGoTheme {
        CashScreen(
            onBack = {}
        )
    }
}