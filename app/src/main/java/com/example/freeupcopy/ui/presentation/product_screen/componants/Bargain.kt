package com.example.freeupcopy.ui.presentation.product_screen.componants

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.R
import com.example.freeupcopy.common.Constants
import com.example.freeupcopy.common.Constants.MAX_BARGAIN_MESSAGE_LENGTH
import com.example.freeupcopy.domain.enums.Currency
import com.example.freeupcopy.domain.model.BargainOffer
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.CardShape
import com.example.freeupcopy.ui.theme.SwapsyTheme
import com.example.freeupcopy.ui.theme.TextFieldContainerColor
import com.example.freeupcopy.ui.theme.TextFieldShape
import com.example.freeupcopy.utils.clearFocusOnKeyboardDismiss

@Composable
fun BargainElement(
    modifier: Modifier = Modifier,
    bargainOffers: List<BargainOffer>,
    onOpenPopup: () -> Unit,
    currentUserId: String,
    onEditOffer: (BargainOffer) -> Unit
) {
    // State to track whether all offers are shown
    var showAllOffers by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.secondary.copy(alpha = 0.175f)
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                )
            )
            .padding(16.dp)
    ) {
        Text(
            text = "Bargain offers",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(16.dp))

        ElevatedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onOpenPopup,
            shape = ButtonShape,
            elevation = ButtonDefaults.buttonElevation(4.dp),
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(
                text = "Make an Offer",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        // Show limited or all offers based on the state
        val offersToDisplay = if (showAllOffers) bargainOffers else bargainOffers.take(3)
        offersToDisplay.forEach { offer ->
            BargainOfferRow(
                bargainOffer = offer,
                isCurrentUser = offer.userId == currentUserId,
                onEditOffer = onEditOffer,

                )
            if (offer != offersToDisplay.last()) {
                Spacer(modifier = Modifier.size(10.dp))
            }
        }

        Spacer(modifier = Modifier.size(2.dp))

        // Show "Show More" or "Show Less" button
        if (bargainOffers.size > 3) {
            TextButton(
                onClick = { showAllOffers = !showAllOffers },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = if (showAllOffers) "Show Less" else "Show ${bargainOffers.size - 3} more offers",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
                Icon(
                    modifier = Modifier.rotate(if (showAllOffers) 180f else 0f),
                    imageVector = Icons.Rounded.ArrowDropDown,
                    contentDescription = if (showAllOffers) "Show less" else "Show more",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun BargainOfferRow(
    modifier: Modifier = Modifier,
    bargainOffer: BargainOffer,
    isCurrentUser: Boolean,
    onEditOffer: (BargainOffer) -> Unit
) {
    Row(
        modifier = modifier
            .clip(CardShape.medium)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Row(
                verticalAlignment = Alignment.Top
            ) { // User and time
                Icon(
                    imageVector = Icons.Rounded.AccountCircle,
                    contentDescription = "User image",
                )
                Spacer(modifier = Modifier.size(8.dp))
                Column {
                    Row {
                        Text(
                            text = bargainOffer.username,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = bargainOffer.timeStamp,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                        )
                    }
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = bargainOffer.message,
                        fontSize = 14.sp,
                        lineHeight = 18.sp
                    )
                    if (isCurrentUser) {

                        Spacer(modifier = Modifier.size(4.dp))
                        Text(
                            modifier = Modifier.clickable { onEditOffer(bargainOffer) },
                            text = "Edit offer",
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }


                }

            }
        }
        Column(
            modifier = Modifier.padding(start = 24.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Top
        ) {
            if (isCurrentUser) {
                Box(
                    modifier = Modifier
                        .clip(ButtonShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(horizontal = 10.dp, vertical = 3.dp)
                ) {
                    Text(
                        "Requested",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.size(6.dp))
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                when (bargainOffer.currency) {
                    Currency.CASH -> {
                        Icon(
                            modifier = Modifier.size(14.dp),
                            painter = painterResource(id = R.drawable.ic_rupee),
                            contentDescription = null,
                        )
                    }

                    Currency.COIN -> {
                        Icon(
                            modifier = Modifier
                                .padding(end = 2.dp)
                                .size(14.dp),
                            painter = painterResource(id = R.drawable.coin),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }
                }
                Text(
                    text = bargainOffer.amount,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }

        }
    }
}

@Composable
fun BargainOptionsSheet(
    modifier: Modifier = Modifier,
    listedPrice: String,
    mrp: String,
    fifteenPercentRecommended: Pair<String, String>,
    tenPercentRecommended: Pair<String, String>,
    bargainAmount: String,
    onBargainAmountChange: (String) -> Unit,
    message: String,
    onBargainMessageChange: (String) -> Unit,
    onClosePopup: () -> Unit,
    onBargainRequest: () -> Unit,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    currencySelected: Currency,
    onCurrencySelected: (Currency) -> Unit
) {

    val focusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Bargain offers",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = onClosePopup
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "close"
                )
            }
        }
        Spacer(modifier = Modifier.size(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BargainCurrencyType(
                currency = Currency.CASH,
                isSelected = currencySelected == Currency.CASH,
                modifier = Modifier
                    .weight(1f),
                onClick = {
                    //currencySelected = Currency.CASH
                    onCurrencySelected(Currency.CASH)
                }
            )
            BargainCurrencyType(
                currency = Currency.COIN,
                isSelected = currencySelected == Currency.COIN,
                modifier = Modifier.weight(1f),
                onClick = {
                    // currencySelected = Currency.COIN
                    onCurrencySelected(Currency.COIN)
                }
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        Row {
            Text(
                text = "Listed price",
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "₹$mrp",
                textDecoration = TextDecoration.LineThrough,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "₹$listedPrice",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.size(20.dp))

        Row(
            modifier = Modifier.height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            BargainOptionBox(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                value = if (currencySelected == Currency.CASH) "${Currency.CASH.symbol}${fifteenPercentRecommended.first}" else fifteenPercentRecommended.second,
                isSelected = selectedIndex == 0,
                onClick = {
                    onBargainAmountChange(
                        if (currencySelected == Currency.CASH) fifteenPercentRecommended.first else fifteenPercentRecommended.second
                    )
                    //selectedIndex = 0
                    onSelectedIndexChange(0)
                }
            )
            BargainOptionBox(
                modifier = Modifier.fillMaxHeight(),
                value = if (currencySelected == Currency.CASH) "${Currency.CASH.symbol}${tenPercentRecommended.first}" else tenPercentRecommended.second,
                isRecommended = true,
                isSelected = selectedIndex == 1,
                onClick = {
                    onBargainAmountChange(
                        if (currencySelected == Currency.CASH) tenPercentRecommended.first else tenPercentRecommended.second
                    )
                    //selectedIndex = 1
                    onSelectedIndexChange(1)
                }
            )
            BargainOptionBox(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                value = "Other",
                isSelected = selectedIndex == 2,
                onClick = {
                    //selectedIndex = 2
                    onSelectedIndexChange(2)
                }
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        if (selectedIndex == 2) {

            OutlinedTextField(
                modifier = Modifier
                    .clearFocusOnKeyboardDismiss()
                    .focusRequester(focusRequester)
                    .fillMaxWidth(),
                value = bargainAmount,
                onValueChange = {
                    if (!it.contains(Regex("[,.\\s-]")) && it.length <= 10) {
                        onBargainAmountChange(it)
                    }
                },
                placeholder = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = if (currencySelected == Currency.CASH) "Cash" else "Coins",
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
                        focusManager.clearFocus()
                    }
                )
            )

            Spacer(modifier = Modifier.size(16.dp))
        }

        OutlinedTextField(
            value = message,
            onValueChange = {
                if (it.length <= MAX_BARGAIN_MESSAGE_LENGTH) {
                    onBargainMessageChange(it)
                }
            },
            placeholder = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Say something to the seller",
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f),
                    fontStyle = FontStyle.Italic,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clearFocusOnKeyboardDismiss(),
            shape = TextFieldShape,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = TextFieldContainerColor,
                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.15f),
            ),
            supportingText = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "${MAX_BARGAIN_MESSAGE_LENGTH - message.length} characters remaining",
                        color = if (message.length >= MAX_BARGAIN_MESSAGE_LENGTH) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        )
        Spacer(modifier = Modifier.size(16.dp))

        // only show the delete button if the user is editing and if he is making initial offer
        // then do not show this option
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedButton(
                modifier = Modifier.weight(0.4f).height(50.dp),
                onClick = {},
                shape = ButtonShape,
            ) {
                Text(
                    text = "Delete",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
            }
            Spacer(modifier = Modifier.size(8.dp))

            Button(
                modifier = Modifier.weight(0.6f).height(50.dp),
                onClick = onBargainRequest,
                shape = ButtonShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Request",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
//        Row(
//            modifier = Modifier
//                .heightIn(min = 50.dp)
//                .fillMaxWidth()
//                .clip(ButtonShape)
//                .clickable {
//                    onBargainRequest()
//                }
//                .background(MaterialTheme.colorScheme.onPrimaryContainer),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center
//        ) {
//            Text(
//                text = "Request",
//                fontWeight = FontWeight.Bold,
//                fontSize = 18.sp,
//                color = MaterialTheme.colorScheme.onPrimary
//            )
//        }
    }

}

@Composable
fun BargainOptionBox(
    modifier: Modifier = Modifier,
    value: String,
    isRecommended: Boolean = false,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {

    Box(
        modifier = modifier
            .clip(CardShape.medium)
            .clickable { onClick() }
            .background(
                if (isSelected) MaterialTheme.colorScheme.secondaryContainer else
                    MaterialTheme.colorScheme.primaryContainer
            )
            .border(
                if (isSelected) 2.dp else 1.dp,
                if (isSelected) MaterialTheme.colorScheme.primary else
                    MaterialTheme.colorScheme.onPrimaryContainer.copy(0.15f),
                CardShape.medium
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            if (isRecommended) {
                Text(
                    text = "Recommended",
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
                    fontSize = 13.5.sp
                )
            }
        }
    }
}

@Composable
fun BargainCurrencyType(
    modifier: Modifier = Modifier,
    currency: Currency,
    isSelected: Boolean,
    onClick: (Currency) -> Unit
) {
    Box(
        modifier = modifier
            .clip(CardShape.medium)
            .clickable { onClick(currency) }
            .background(
                if (isSelected) MaterialTheme.colorScheme.secondaryContainer else
                    MaterialTheme.colorScheme.primaryContainer
            )
            .border(
                1.dp,
                if (isSelected) MaterialTheme.colorScheme.primary else
                    MaterialTheme.colorScheme.onPrimaryContainer.copy(0.15f),
                CardShape.medium
            )
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = if (currency == Currency.CASH) R.drawable.ic_rupee else R.drawable.coin),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(if (currency == Currency.CASH) "Cash" else "Coins")
        }
    }

}

@Preview(
    showBackground = true
)
@Composable
fun BargainPopupPreview() {
    SwapsyTheme {
        BargainOptionsSheet(
            listedPrice = "200",
            mrp = "300",
            bargainAmount = "",
            onBargainAmountChange = {},
            message = "",
            onBargainMessageChange = {},
            onClosePopup = {},
            onBargainRequest = {},
            fifteenPercentRecommended = Pair("150", "300"),
            tenPercentRecommended = Pair("100", "200"),
            selectedIndex = 0,
            onSelectedIndexChange = {},
            currencySelected = Currency.CASH,
            onCurrencySelected = {}
        )
//        BargainOfferRow(
//            bargainOffer = BargainOffer(
//                id = "BO12345",
//                username = "johnoe",
//                userId = "U001",
//                message = "Looking for a 20% discount on bulk orders.",
//                timeStamp = "1 day ago",
//                amount = "2000",
//                currency = Currency.CASH
//            ),
//            isCurrentUser = true,
//            onEditOffer = {}
//        )
    }
}

//@Preview(
//    showBackground = true
//)
//@Composable
//fun BargainElementPreview() {
//    SwapsyTheme {
//        BargainElement(
//            onOpenPopup = {},
//            bargainOffers = listOf(
//                BargainOffer(
//                    id = "BO12345",
//                    user = "John Doe",
//                    userId = "U001",
//                    text = "Looking for a 20% discount on bulk orders.",
//                    timeStamp = "1 day ago"
//                ),
//                BargainOffer(
//                    id = "BO12346",
//                    user = "Jane Smith",
//                    userId = "U002",
//                    text = "Can I get free shipping for orders above $50?",
//                    timeStamp = "1 day ago"
//                ),
//                BargainOffer(
//                    id = "BO12347",
//                    user = "Alex Johnson",
//                    userId = "U003",
//                    text = "Would you accept a counteroffer of $30 for this item?",
//                    timeStamp = "2 day ago"
//                ),
//                BargainOffer(
//                    id = "BO12348",
//                    user = "Emily Davis",
//                    userId = "U004",
//                    text = "If I buy two items, can I get a third one free?",
//                    timeStamp = "2 day ago"
//                ),
//                BargainOffer(
//                    id = "BO12349",
//                    user = "Michael Brown",
//                    userId = "U005",
//                    text = "Is there any holiday discount available?",
//                    timeStamp = "4 day ago"
//                )
//            )
//        )
//    }
//}