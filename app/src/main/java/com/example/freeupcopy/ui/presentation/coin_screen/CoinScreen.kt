package com.example.freeupcopy.ui.presentation.coin_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.freeupcopy.R
import com.example.freeupcopy.data.remote.dto.product.User
import com.example.freeupcopy.domain.enums.Currency
import com.example.freeupcopy.ui.presentation.cash_screen.CashScreen
import com.example.freeupcopy.ui.presentation.product_card.ProductCard
import com.example.freeupcopy.ui.presentation.product_listing.ProductListingUiEvent
import com.example.freeupcopy.ui.presentation.product_listing.componants.SelectedOptionsRow
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.CardShape
import com.example.freeupcopy.ui.theme.CashColor1
import com.example.freeupcopy.ui.theme.SwapGoTheme
import com.example.freeupcopy.ui.theme.TextFieldShape
import com.example.freeupcopy.utils.dashedBorder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    var selectedTabIndex by remember { mutableIntStateOf(2) }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val tabs = listOf("Coin Shop", "Borrow Coins", "Rewards")

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
                                text = "SwapCoin Balance",
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
                    ),
                    actions = {
                        IconButton(
                            onClick = {

                            }
                        ) {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                painter = painterResource(R.drawable.ic_recent_searches),
                                contentDescription = "info"
                            )
                        }
                    }
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
                CoinBalanceCard()
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.primaryContainer)
            ) {
                PrimaryTabRow (
                    modifier = Modifier.fillMaxWidth(),
                    selectedTabIndex = selectedTabIndex,
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = {
                                selectedTabIndex = index
                            },
                            text = {
                                Text(
                                    text = title,
                                    fontSize = 16.sp
                                )
                            },
                            selectedContentColor = MaterialTheme.colorScheme.primary,
                            unselectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
                        )
                    }
                }

                    when (selectedTabIndex) {
                        0 -> {
                            CoinShop()
                        }
                        1 -> {
                            BorrowCoins()
                        }
                        2 -> {
                            Rewards()
                        }
                    }

            }
        }
    }
}

@Composable
fun CoinShop(
    modifier: Modifier = Modifier
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2), // 2 items per row
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalItemSpacing = 12.dp,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        items(10) { index ->
            ProductCard(
                brand = listOf(
                    "Adidas", "Nike", "Puma", null, "Under Armour",
                    "Levi's", "Calvin Klein", "Tommy Hilfiger", "Lacoste", null
                )[index],
                title = listOf(
                    "Just Herbs Mini Kit limited edition",
                    "Nike Air Max",
                    "Puma Suede",
                    "Reebok Classic",
                    "Under Armour Hoodie",
                    "Levi's Jeans",
                    "Calvin Klein T-shirt",
                    "Tommy Hilfiger Polo",
                    "Lacoste L.12.12",
                    "Ralph Lauren Shirt"
                )[index],
                size = listOf(
                    "40 inches", "L", "M", "XL", "L",
                    "32 inches", "S", "M", "XL", "L"
                )[index],
                productThumbnail = "painterResource(id = R.drawable.bomber_jacket)", // Assuming R.drawable.bomber_jacket is a placeholder
                cashPrice = listOf(
                    null, "499", "399", null, "799",
                    "1999", null, "1499", "1299", null
                )[index],
                coinsPrice = listOf(
                    null, "1000", null, "599", null,
                    null, "999", null, null, "1799"
                )[index],
                combinedPrice = listOf(
                    Pair("4000", "2000"), Pair("300", "800"), null, null, null,
                    null, null, Pair("1000", "500"), null, null
                )[index],
                mrp = listOf(
                    "3500", "2999", "1999", "1499", "1999",
                    "3999", "1999", "2999", "2499", "3499"
                )[index],
                badge = listOf(
                    "Trusted", "Sale", "New", null, "Limited Edition",
                    "Classic", "Trendy", "Luxury", "Iconic", null
                )[index],
                isLiked = false,
                onLikeClick = {},
                onClick = {},
                user = User(
                    _id = "123",
                    username = "John Doe",
                    avatar = ""
                )
            )
        }
    }
}

@Composable
fun BorrowCoins(
    modifier: Modifier = Modifier
) {
    var inputValue by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Terms Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer.copy(0.4f),
                    shape = CardShape.medium
                )
                .padding(16.dp)
        ) {
            Text(
                text = "Borrow Terms",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Terms List
            val terms = listOf(
                "50% deposit required (refundable)",
                "2% service fee (non-refundable)",
                "Return within 1 month for refund",
                "Deposit refundable for 2 months"
            )

            terms.forEach { term ->
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "•",
                        fontSize = 22.sp,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = term,
                        fontSize = 15.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
        ) {
            OutlinedTextField(
                value = inputValue,
                onValueChange = { inputValue = it },
                modifier = Modifier
                    .weight(1f),
                shape = TextFieldShape,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.25f),
                ),
                placeholder = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Enter amount to borrow",
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f),
                        fontStyle = FontStyle.Italic,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { /* Handle calculation */ },
                modifier = Modifier
                    .fillMaxHeight()
                ,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                ),
                shape = ButtonShape
            ) {
                Text(
                    text = "Calculate",
//                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun CoinBalanceCard(
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .clip(CardShape.medium)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary.copy(0.25f),
                shape = CardShape.medium
            )
            .padding(16.dp)
    ) {
        Column {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier.size(50.dp),
                    painter = painterResource(R.drawable.coin),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.size(12.dp))
                Column {
                    Text(
                        text = "Your Balance",
//                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.5f),
                    )

                    Text(
                        text = "20",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp
                    )
                }
            }
            Spacer(Modifier.size(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = {},
                    shape = ButtonShape,
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(R.drawable.ic_offer),
                            contentDescription = null,
                        )
                        Spacer(Modifier.size(4.dp))
                        Text(
                            text = "Sell Coins",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(Modifier.size(12.dp))

                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {},
                    shape = ButtonShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    )
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = Icons.Rounded.Add,
                            contentDescription = null,
                        )
                        Spacer(Modifier.size(4.dp))
                        Text(
                            text = "Buy Coins",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Rewards() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        // Profile Completion Section
        EarnCoinsSection(
            title = "Profile Completion",
            items = listOf(
                CoinItem("Add profile details", 130),
                CoinItem("Verify Through Google", 60)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Community Activities Section
        EarnCoinsSection(
            title = "Community Activities",
            items = listOf(
                CoinItem("Rate or review purchase", 100),
                CoinItem("Write Review on playstore", 120),
                CoinItem("Report an issue", 10)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Penalties Section
        EarnCoinsSection(
            title = "Penalties",
            items = listOf(
                CoinItem("Cancel order as seller", -40),
                CoinItem("Ignore order for above 36hrs", -40),
                CoinItem("Other violations", -450, showRange = true)
            )
        )
    }
}

@Composable
private fun EarnCoinsSection(
    title: String,
    items: List<CoinItem>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            items.forEach { item ->
                CoinItemRow(item)
                if (items.last() != item) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun CoinItemRow(
    item: CoinItem
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.description,
            fontSize = 18.sp
        )

        val coinText = when {
            item.showRange -> "-30 to ${item.coins} coins"
            item.coins > 0 -> "+${item.coins} coins"
            else -> "${item.coins} coins"
        }

        Text(
            text = coinText,
            fontSize = 18.sp,
            color = if (item.coins >= 0) Color(0xFF22C55E) else Color(0xFFEF4444)
        )
    }
}

private data class CoinItem(
    val description: String,
    val coins: Int,
    val showRange: Boolean = false
)

@Preview
@Composable
private fun SearchScreenPreview() {
    SwapGoTheme {
        CoinScreen(
            onBack = {}
        )
    }
}