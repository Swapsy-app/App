package com.example.freeupcopy.ui.presentation.sell_screen.componants

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.presentation.sell_screen.CustomDivider
import com.example.freeupcopy.ui.theme.SwapsyTheme
import com.example.freeupcopy.ui.theme.LinkColor
import com.example.freeupcopy.ui.theme.NoteContainerLight
import com.example.freeupcopy.ui.theme.RecommendedContainerColor
import com.example.freeupcopy.ui.theme.RecommendedTextColor
import com.example.freeupcopy.utils.calculateDeliveryFee
import com.example.freeupcopy.utils.calculatePlatformFee
import com.example.freeupcopy.utils.calculateTotalEarnings
import com.example.freeupcopy.utils.clearFocusOnKeyboardDismiss
import com.example.freeupcopy.utils.dashedLine
import com.example.freeupcopy.utils.validateCash

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PriceScreen(
    modifier: Modifier = Modifier,
    onClose: () -> Unit

) {
    val context = LocalContext.current

    val lifeCycleOwner = LocalLifecycleOwner.current
    var mrp by remember { mutableStateOf("") }
    val selectedOptions = remember { mutableStateListOf<String>() }
    val availableOptions =
        remember { mutableStateListOf<String>("Cash", "Coins", "Cash\n+\nCoins") }

    var cashEarning by remember { mutableStateOf("") }
    var coinsEarning by remember { mutableStateOf("") }
    var cAndCEarningCash by remember { mutableStateOf("") }
    var cAndCEarningCoin by remember { mutableStateOf("") }
    var enabled by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }
    var isCashSheetOpen by rememberSaveable { mutableStateOf(false) }
    var isCoinSheetOpen by rememberSaveable { mutableStateOf(false) }
    var isCashAndCoinSheetOpen by rememberSaveable { mutableStateOf(false) }

    var sellingPriceCash by remember { mutableStateOf("") }
    var sellingPriceCoin by remember { mutableStateOf("") }
    var cAndCPriceCash by remember { mutableStateOf("") }
    var cAndCPriceCoin by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .clearFocusOnKeyboardDismiss(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(text = "Price Details")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            onClose()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "close"
                        )
                    }
                }
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f))
                    .windowInsetsPadding(NavigationBarDefaults.windowInsets)
                    .defaultMinSize(minHeight = 70.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = 50.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { }
                            .background(MaterialTheme.colorScheme.secondary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Confirm", color = MaterialTheme.colorScheme.onSecondary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(start = 16.dp, end = 16.dp),
        ) {
            Spacer(modifier = Modifier.size(8.dp))

            Text(text = "MRP (Original price)")
            Spacer(modifier = Modifier.size(16.dp))

            PaymentTextFieldWithoutEarning(
                value = mrp,
                placeholder = "Enter MRP in rupees",
                leadingIcon = R.drawable.ic_rupee,
                onValueChange = {
                    mrp = it
                },
                onDone = {}
            )

            Spacer(modifier = Modifier.size(16.dp))

            Text(text = "Pricing model(s)")

            Spacer(modifier = Modifier.size(10.dp))
            Column {
                AnimatedVisibility(
                    visible = selectedOptions.isEmpty() || (!selectedOptions.contains(
                        "Coins"
                    ) && !selectedOptions.contains("Cash\n+\nCoins")),
                    enter = fadeIn(animationSpec = tween(600)) + expandVertically(animationSpec = tween(600)),
                    exit = fadeOut(animationSpec = tween(600)) + shrinkVertically(animationSpec = tween(600))
                ) {
                    PricingInBox(Modifier.padding(bottom = 10.dp))
                }

                if (selectedOptions.isNotEmpty()) {
                    NoteSection(
                        text = "Your product will be sold in " + when {
                            selectedOptions.containsAll(
                                listOf(
                                    "Cash",
                                    "Coins",
                                    "Cash\n+\nCoins"
                                )
                            ) ->
                                "cash, coins, and cash+coins"

                            selectedOptions.containsAll(listOf("Cash", "Coins")) ->
                                "cash and coins"

                            selectedOptions.containsAll(listOf("Cash", "Cash\n+\nCoins")) ->
                                "cash and cash+coins"

                            selectedOptions.containsAll(listOf("Coins", "Cash\n+\nCoins")) ->
                                "coins and cash+coins"

                            selectedOptions.contains("Cash\n+\nCoins") ->
                                "cash+coins"

                            selectedOptions.contains("Cash") ->
                                "cash only"

                            selectedOptions.contains("Coins") ->
                                "coins only"

                            else -> ""
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.size(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                availableOptions.forEach {
                    PaymentOption(
                        modifier = Modifier.weight(1f),
                        icon = when (it) {
                            "Cash" -> R.drawable.ic_cash
                            "Coins" -> R.drawable.ic_coin
                            else -> null
                        },
                        selected = selectedOptions.contains(it),
                        text = it,
                        onClick = {
                            if (selectedOptions.contains(it)) {
                                selectedOptions.remove(it)
                            } else {
                                selectedOptions.add(it)
                            }
                        }
                    )
                }

            }

            if (
                selectedOptions.contains("Cash")
            ) {
                CustomDivider(Modifier.padding(vertical = 16.dp))
                Row(
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Text(text = "Selling price")
                    Text(
                        text = " (Cash)",
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.size(10.dp))
                PaymentTextField(
                    value = sellingPriceCash,
                    placeholder = "Enter selling price in rupees",
                    leadingIcon = R.drawable.ic_rupee,
                    onValueChange = {
                        sellingPriceCash = it
                    },
                    onDone = {
                        if (it.isNotEmpty()) {
                            val tempCalculate = calculateTotalEarnings(it.toLong(), "cat0")
                            if (tempCalculate < 10) {
                                Toast.makeText(
                                    context,
                                    "Selling amount is too low, you can sell using coins",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                cashEarning = ""
                                sellingPriceCash = ""
                            } else {
                                cashEarning = tempCalculate.toString()
                            }
                        } else {
                            Toast.makeText(context, "Value can not be empty", Toast.LENGTH_SHORT)
                                .show()
                            cashEarning = ""
                        }
                    }
                )
                if (cashEarning.isNotEmpty()) {
                    YourEarning(
                        earnings = "₹$cashEarning",
                        onClick = {
                            isSheetOpen = true
                            isCashSheetOpen = true
                        }
                    )
                }
            }

            if (
                selectedOptions.contains("Coins")
            ) {
                CustomDivider(Modifier.padding(vertical = 16.dp))
                Row(
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Text(text = "Enter selling price")
                    Text(
                        fontSize = 13.sp,
                        text = " (Coins)",
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                    )
                }

                Spacer(modifier = Modifier.size(10.dp))
                PaymentTextField(
                    value = sellingPriceCoin,
                    placeholder = "In coins",
                    leadingIcon = R.drawable.ic_coin,
                    onValueChange = {
                        sellingPriceCoin = it
                    },
                    onDone = {
                        if (it.isNotEmpty()) {
                            if (it.toLong() < 10) {
                                Toast.makeText(
                                    context,
                                    "Selling amount cannot be < 10",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                coinsEarning = ""
                                sellingPriceCoin = ""
                            } else {
                                coinsEarning = it
                            }
                        } else {
                            Toast.makeText(context, "Value can not be empty", Toast.LENGTH_SHORT)
                                .show()
                            coinsEarning = ""
                        }
                    },
                )
                if (coinsEarning.isNotEmpty()) {
                    YourEarning(
                        earnings = "$coinsEarning coins",
                        onClick = {
                            isSheetOpen = true
                            isCoinSheetOpen = true
                        }
                    )
                }
            }

            if (
                selectedOptions.contains("Cash\n+\nCoins")
            ) {
                CashAndCoinRow(
                    cashEntered = cAndCPriceCash,
                    coinEntered = cAndCPriceCoin,
                    enabled = enabled,
                    onCashValueChange = {
                        cAndCPriceCash = it
                        enabled = validateCash(cAndCPriceCash)
                    },
                    onCoinValueChange = {
                        cAndCPriceCoin = it
                    },
                    onNext = {
                        if (it.isNotEmpty()) {
                            val tempCalculate = calculateTotalEarnings(it.toLong(), "cat0")
                            if (tempCalculate < 10) {
                                Toast.makeText(
                                    context,
                                    "Selling amount is too low",
                                    Toast.LENGTH_SHORT
                                ).show()
                                cAndCPriceCash = ""
                                cAndCEarningCash = ""
                                cAndCEarningCoin = ""
                                cAndCPriceCoin = ""
                            } else {
                                cAndCEarningCash = tempCalculate.toString()
                            }
                        } else {
                            Toast.makeText(context, "Value can not be empty", Toast.LENGTH_SHORT)
                                .show()
                            cAndCEarningCash = ""
                            cAndCEarningCoin = ""
                            cAndCPriceCash = ""
                            cAndCEarningCoin = ""
                        }
                    },
                    onDone = {
                        if (it.isNotEmpty()) {
                            if (it.toLong() < 10) {
                                Toast.makeText(
                                    context,
                                    "Selling amount cannot be < 10",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                cAndCEarningCoin = ""
                                cAndCPriceCoin = ""
                            } else {
                                cAndCEarningCoin = it
                            }
                        } else {
                            Toast.makeText(context, "Value can not be empty", Toast.LENGTH_LONG)
                                .show()
                            cAndCEarningCoin = ""
                        }
                    }
                )

                if (cAndCEarningCash.isNotEmpty() && cAndCEarningCoin.isNotEmpty()) {
                    YourEarning(
                        earnings = "₹$cAndCEarningCash + $cAndCEarningCoin coins",
                        onClick = {
                            isSheetOpen = true
                            isCashAndCoinSheetOpen = true
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.size(16.dp))

            if (isSheetOpen) {
                ModalBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = {
                        isSheetOpen = false
                        isCashSheetOpen = false
                        isCoinSheetOpen = false
                        isCashAndCoinSheetOpen = false
                    },
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    windowInsets = BottomSheetDefaults.windowInsets.only(WindowInsetsSides.Bottom)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        when {
                            isCashSheetOpen -> {

                                val deliveryFee =
                                    calculateDeliveryFee("cat0", sellingPriceCash.toLong())
                                val platformFee = calculatePlatformFee(sellingPriceCash.toLong())
                                val totalEarnings =
                                    sellingPriceCash.toLong() - deliveryFee - platformFee

                                CommissionDetails(
                                    initialPrice = sellingPriceCash.toLong(),
                                    deliveryFee = deliveryFee,
                                    platformFee = platformFee,
                                    totalEarnings = totalEarnings,
                                    onClick = {
                                        isSheetOpen = false
                                        isCashSheetOpen = false
                                    }
                                )
                            }

                            isCoinSheetOpen -> {
                                CoinsDetails(
                                    onClick = {
                                        isSheetOpen = false
                                        isCoinSheetOpen = false
                                    }
                                )
                            }

                            isCashAndCoinSheetOpen -> {
                                val deliveryFee =
                                    calculateDeliveryFee("cat0", cAndCPriceCash.toLong())
                                val platformFee = calculatePlatformFee(cAndCPriceCash.toLong())
                                val totalEarnings =
                                    cAndCPriceCash.toLong() - deliveryFee - platformFee

                                CommissionDetails(
                                    initialPrice = cAndCPriceCash.toLong(),
                                    deliveryFee = deliveryFee,
                                    platformFee = platformFee,
                                    totalEarnings = totalEarnings,
                                    onClick = {
                                        isSheetOpen = false
                                        isCashAndCoinSheetOpen = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PaymentOption(
    modifier: Modifier = Modifier,
    selected: Boolean,
    icon: Int? = null,
    text: String,
    onClick: () -> Unit,
    tint: Color = MaterialTheme.colorScheme.onPrimaryContainer
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .border(
                width = 2.dp,
                color = if (selected) MaterialTheme.colorScheme.primary else
                    MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f),
                shape = RoundedCornerShape(10.dp)
            )
            .background(
                color = if (selected) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        icon?.let {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = text,
                tint = if (selected) tint else tint.copy(alpha = 0.3f)
            )
            Spacer(modifier = Modifier.size(8.dp))
        }
        Text(
            text = text,
            textAlign = TextAlign.Center,
            lineHeight = 18.sp,
            color = if (selected) MaterialTheme.colorScheme.onPrimaryContainer
            else MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3f)
        )
    }
}

@Composable
fun RecommendedBecause(
    modifier: Modifier = Modifier,
    text: String
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .border(1.dp, RecommendedTextColor, RoundedCornerShape(6.dp))
            .background(RecommendedContainerColor)
            .padding(horizontal = 6.dp, vertical = 1.dp),
    ) {
        Text(
            text = text,
            fontSize = 13.sp,
            color = RecommendedTextColor
        )
    }
}

@Composable
fun PricingInBox(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(NoteContainerLight.copy(alpha = 0.6f))
            .padding(12.dp),
    ) {
        Text(
            text = "Include coins in your pricing options to tap into a larger customer base, as it provides",
            fontSize = 13.sp,
            lineHeight = 18.sp,
            fontStyle = FontStyle.Italic,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Row {
            RecommendedBecause(text = "Fast sales")
            Spacer(modifier = Modifier.size(8.dp))
            RecommendedBecause(text = "More customers")
        }
    }
}

@Composable
fun CashAndCoinRow(
    modifier: Modifier = Modifier,
    cashEntered: String,
    coinEntered: String,
    enabled: Boolean,
    onCashValueChange: (String) -> Unit,
    onCoinValueChange: (String) -> Unit,
    onNext: (String) -> Unit,
    onDone: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
    ) {
        var cashValue by remember { mutableStateOf(cashEntered) }
        var coinsValue by remember { mutableStateOf(coinEntered) }

        CustomDivider(Modifier.padding(vertical = 16.dp))
        Text(text = "Cash and coins combined")
        Spacer(modifier = Modifier.size(10.dp))
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

            OutlinedTextField(
                modifier = modifier
                    .clearFocusOnKeyboardDismiss()
                    .focusRequester(focusRequester)
                    .fillMaxWidth()
                    .weight(1f),
                value = cashEntered,
                onValueChange = {
                    if (!it.contains(Regex("[,.\\s-]")) && it.length <= 10) {
                        cashValue = it
                        onCashValueChange(cashValue)
                    }
                },
                placeholder = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Cash",
                        textAlign = TextAlign.End,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f),
                        fontStyle = FontStyle.Italic,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_rupee),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                },
                maxLines = 1,
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f),
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                shape = RoundedCornerShape(16.dp),
                textStyle = TextStyle(textAlign = TextAlign.End),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onNext(cashValue)
                        focusManager.clearFocus()
                    }
                )
            )

            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "combined",
                modifier = Modifier
                    .padding(6.dp)
            )
            PaymentTextFieldWithoutEarning(
                modifier = modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .weight(1f),
                value = coinEntered,
                enabled = enabled,
                placeholder = "Coins",
                leadingIcon = R.drawable.ic_coin,
                onDone = {
                    onDone(coinsValue)
                    focusManager.clearFocus()
                },
                onValueChange = {
                    coinsValue = it
                    onCoinValueChange(coinsValue)
                },
            )
        }
    }
}

@Composable
fun CommissionDetails(
    modifier: Modifier = Modifier,
    initialPrice: Long,
    deliveryFee: Long,
    platformFee: Long,
    totalEarnings: Long,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Row {
            Text(
                text = "Buyer Pays",
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.75f)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "₹$initialPrice"
            )
        }
        Spacer(modifier = Modifier.size(10.dp))


        Row {
            Text(
                text = "Share Delivery Fee with Buyer",
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.75f)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "-₹$deliveryFee"
            )
        }
        Spacer(modifier = Modifier.size(6.dp))
        Text(
            text = "(Note: Final adjustments may occur if the courier" +
                    " service determines the package weight to be higher than initially stated.)",
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
            fontStyle = FontStyle.Italic,
            fontSize = 13.sp,
            lineHeight = 18.sp
        )


        Spacer(modifier = Modifier.size(16.dp))
        Row {
            Text(
                text = "Platform Fee",
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.75f)
            )
            Text(
                text = " (5%)",
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f),
                fontStyle = FontStyle.Italic
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "-₹$platformFee"
            )
        }
        CustomDivider(Modifier.padding(vertical = 10.dp))
        Row {
            Text(
                text = "Total Earnings",
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "₹$totalEarnings",
                color = Color(0xFF38BB00),
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        Button(
            onClick = { onClick() },
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            )
        ) {
            Text(
                text = "Got it",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}


@Composable
fun CoinsDetails(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {

        val coinUsages = listOf(
            "To buy items for free",
            "To get discounts on products",
            "Sell coins for cash",
            "To boost product sales"
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(80.dp),
                painter = painterResource(id = R.drawable.ic_coin),
                contentDescription = null
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "Coin are credited to your account once the order has been delivered to the buyer"
            )
        }
        Spacer(modifier = Modifier.size(20.dp))

        Text(
            text = "How coins can be used:",
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.75f),
            fontWeight = FontWeight.W500
        )
        Spacer(modifier = Modifier.size(10.dp))

        coinUsages.forEach { usage ->
            UsageItem(text = usage)
        }

        Spacer(modifier = Modifier.size(16.dp))

        Button(
            onClick = { onClick() },
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            )
        ) {
            Text(
                text = "Got it",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}


@Composable
fun PaymentTextField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    leadingIcon: Int,
    onDone: (String) -> Unit,
    onValueChange: (String) -> Unit
) {
    var quantity by remember { mutableStateOf(value) }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = modifier
            .clearFocusOnKeyboardDismiss()
            .focusRequester(focusRequester)
            .fillMaxWidth(),
        value = value,
        onValueChange = {
            if (!it.contains(Regex("[,.\\s-]")) && it.length <= 10) {
                quantity = it
                onValueChange(quantity)
            }
        },
        placeholder = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = placeholder,
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f),
                fontStyle = FontStyle.Italic,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = leadingIcon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        },
        maxLines = 1,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f),
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
        ),
        shape = RoundedCornerShape(16.dp),
        textStyle = TextStyle(textAlign = TextAlign.End),
        keyboardActions = KeyboardActions(
            onDone = {
                onDone(quantity)
                focusManager.clearFocus()
            }
        )
    )
}

@Composable
fun PaymentTextFieldWithoutEarning(
    modifier: Modifier = Modifier,
    value: String,
    enabled: Boolean = true,
    placeholder: String,
    leadingIcon: Int,
    onDone: (String) -> Unit,
    onValueChange: (String) -> Unit
) {
    var quantity by remember { mutableStateOf(value) }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = modifier
            .clearFocusOnKeyboardDismiss()
            .focusRequester(focusRequester)
            .fillMaxWidth(),
        value = value,
        enabled = enabled,
        onValueChange = {
            if (!it.contains(Regex("[,.\\s-]")) && it.length <= 10) {
                quantity = it
                onValueChange(quantity)
            }
        },
        placeholder = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = placeholder,
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f),
                fontStyle = FontStyle.Italic,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = leadingIcon),
                contentDescription = null
            )
        },
        maxLines = 1,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f),
            disabledLeadingIconColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f)
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
        ),
        shape = RoundedCornerShape(16.dp),
        textStyle = TextStyle(textAlign = TextAlign.End),
        keyboardActions = KeyboardActions(
            onDone = {
                onDone(quantity)
                focusManager.clearFocus()
            }
        )
    )
}

@Composable
fun YourEarning(
    modifier: Modifier = Modifier,
    earnings: String,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = modifier.padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Your earning(s): ",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.75f),
            fontStyle = FontStyle.Italic
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onClick()
            }
        ) {
            // Measure the Text width dynamically
//            val textWidth = remember { mutableStateOf(0) }

            Text(
                text = earnings,
                color = LinkColor,
                fontSize = 14.sp,
                maxLines = 1,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(bottom = 2.dp)
                    .dashedLine(
                        color = LinkColor,
                        strokeWidth = 2.dp,
                        dashWidth = 5.dp,
                        dashGap = 4.dp
                    )
//                    .onGloballyPositioned { coordinates ->
//                        textWidth.value = coordinates.size.width
//                    }
            )

//            Canvas(
//                modifier = Modifier
//                    .width(with(LocalDensity.current) { textWidth.value.toDp() }) // Use text's width
//                    .height(1.dp)
//                    .padding(top = 11.dp) // Adjust padding to align with text
//            ) {
//                val dashWidth = 4.dp.toPx()
//                val dashGap = 4.dp.toPx()
//                val startX = 0f
//                val endX = size.width
//
//                var currentX = startX
//                while (currentX < endX) {
//                    drawLine(
//                        color = LinkColor,
//                        start = Offset(x = currentX, y = 0f),
//                        end = Offset(x = (currentX + dashWidth).coerceAtMost(endX), y = 0f),
//                        strokeWidth = 1.dp.toPx()
//                    )
//                    currentX += dashWidth + dashGap
//                }
//            }
        }
    }
}

@Composable
fun UsageItem(
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "-",
            modifier = Modifier.padding(end = 8.dp),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.75f),
            fontSize = 14.sp,
            fontStyle = FontStyle.Italic
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PriceScreenPreview() {
    SwapsyTheme {
        CoinsDetails(
            onClick = {}
        )
    }
}