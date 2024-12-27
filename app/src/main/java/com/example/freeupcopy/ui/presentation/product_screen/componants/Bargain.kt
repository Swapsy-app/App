package com.example.freeupcopy.ui.presentation.product_screen.componants

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.R
import com.example.freeupcopy.domain.model.BargainOffer
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.CardShape
import com.example.freeupcopy.ui.theme.SwapsyTheme

@Composable
fun BargainElement(
    bargainOffers: List<BargainOffer>,
    onOpenPopup: () -> Unit,
    onShowMore: () -> Unit = {}
) {
    // State to track whether all offers are shown
    var showAllOffers by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
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
            BargainOffer(
                bargainOffer = offer
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
fun BargainOffer(
    modifier: Modifier = Modifier,
    bargainOffer: BargainOffer,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(CardShape.medium)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp)
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
                        text = bargainOffer.user,
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
                    text = bargainOffer.text
                )
            }
            
        }
    }
}

@Composable
fun BargainPopup(
    isRupeeSelected: Boolean,
    optionFocused: Int,
    listedPrice: String,
    recommendation: List<List<String>>,
    onCoin: () -> Unit,
    onRupee: () -> Unit,
    onFocus0: () -> Unit,
    onFocus1: () -> Unit,
    onFocus2: () -> Unit,
    bargainText: String,
    onChangeBargainText: (String) -> Unit,
    messageToSeller: String,
    onChangeMessageToSeller: (String) -> Unit,
    onClosePopup: () -> Unit
) {
    Card(
        modifier = Modifier
            .clip(
                RoundedCornerShape(32.dp, 32.dp, 0.dp, 0.dp)
            ),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.background,
            disabledContentColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "close",
                        modifier = Modifier.clickable {
                            onClosePopup()
                        }
                    )
                }
                Spacer(modifier = Modifier.size(24.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Row(
                            modifier = Modifier
                                .weight(0.40f)
                                .heightIn(min = 50.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .clickable {
                                    onRupee()
                                }
                                .border(
                                    1.dp,
                                    if (isRupeeSelected) Color.Black else Color.Gray,
                                    RoundedCornerShape(10.dp)
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Rupees",
                                color = if (isRupeeSelected) Color.Black else Color.Gray,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        Row(
                            modifier = Modifier
                                .weight(0.40f)
                                .heightIn(min = 50.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .clickable {
                                    onCoin()
                                }
                                .border(
                                    1.dp,
                                    if (isRupeeSelected) Color.Gray else Color.Black,
                                    RoundedCornerShape(10.dp)
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Coins",
                                color = if (isRupeeSelected) Color.Gray else Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.size(48.dp))
            Text(
                text = "Listed price  ₹ $listedPrice",
                fontSize = 18.sp,
                color = Color.Gray,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.size(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Row(
                        modifier = Modifier
                            .weight(0.40f)
                            .heightIn(min = 50.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .clickable {
                                onFocus0()
                            }
                            .border(
                                1.dp,
                                if (optionFocused == 0) Color.Black else Color.Gray,
                                RoundedCornerShape(10.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (isRupeeSelected) {
                            Text(
                                text = "₹ ${recommendation[0][0]}",
                                color = if (optionFocused == 0) Color.Black else Color.Gray,
                                fontSize = 18.sp
                            )
                        } else {
                            Row {
                                Text(
                                    text = recommendation[1][0],
                                    color = if (optionFocused == 0) Color.Black else Color.Gray,
                                    fontSize = 18.sp
                                )
                                Spacer(modifier = Modifier.size(4.dp))
                                Image(
                                    painter = painterResource(id = R.drawable.coin),
                                    contentDescription = "coin icon",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    Row(
                        modifier = Modifier
                            .weight(0.40f)
                            .heightIn(min = 50.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .clickable {
                                onFocus1()
                            }
                            .border(
                                1.dp,
                                if (optionFocused == 1) Color.Black else Color.Gray,
                                RoundedCornerShape(10.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            if (isRupeeSelected) {
                                Text(
                                    text = "₹ ${recommendation[0][1]}",
                                    color = if (optionFocused == 1) Color.Black else Color.Gray,
                                    fontSize = 18.sp
                                )
                            } else {
                                Row {
                                    Text(
                                        text = recommendation[1][1],
                                        color = if (optionFocused == 1) Color.Black else Color.Gray,
                                        fontSize = 18.sp
                                    )
                                    Spacer(modifier = Modifier.size(4.dp))
                                    Image(
                                        painter = painterResource(id = R.drawable.coin),
                                        contentDescription = "coin icon",
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(
                                text = "Recommended",
                                color = if (optionFocused == 1) Color.Black else Color.Gray,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    Row(
                        modifier = Modifier
                            .weight(0.40f)
                            .heightIn(min = 50.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .clickable {
                                onFocus2()
                            }
                            .border(
                                1.dp,
                                if (optionFocused == 2) Color.Black else Color.Gray,
                                RoundedCornerShape(10.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Other",
                            color = if (optionFocused == 2) Color.Black else Color.Gray,
                            fontSize = 18.sp
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.size(16.dp))
            if (optionFocused == 2) {
                OutlinedTextField(
                    value = bargainText,
                    onValueChange = {
                        onChangeBargainText(it)
                    },
                    label = { Text(text = if (isRupeeSelected) "Rupees" else "Coins") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.size(16.dp))
            }
            Spacer(modifier = Modifier.size(32.dp))
            Text(
                text = "Message for the Seller",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.size(16.dp))
            OutlinedTextField(
                value = messageToSeller,
                onValueChange = {
                    onChangeMessageToSeller(it)
                },
                label = { Text(text = "Say a few appreciating words...") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(32.dp))
            Row(
                modifier = Modifier
                    .heightIn(min = 50.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { }
                    .background(MaterialTheme.colorScheme.primary),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Submit",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}

//@Preview(
//    showBackground = true
//)
//@Composable
//fun BargainPopupPreview() {
//    BargainPopup(
//        isRupeeSelected = true,
//        optionFocused = 1,
//        listedPrice = "200",
//        recommendation = listOf(listOf("150", "180"), listOf("500", "800")),
//        onCoin = {},
//        onRupee = {},
//        onFocus0 = {},
//        onFocus1 = {},
//        onFocus2 = {},
//        bargainText = "",
//        onChangeBargainText = {},
//        messageToSeller = "",
//        onChangeMessageToSeller = {},
//        onClosePopup = {}
//    )
//}

@Preview(
    showBackground = true
)
@Composable
fun BargainElementPreview() {
    SwapsyTheme {
        BargainElement(
            onOpenPopup = {},
            bargainOffers = listOf(
                BargainOffer(
                    id = "BO12345",
                    user = "John Doe",
                    userId = "U001",
                    text = "Looking for a 20% discount on bulk orders.",
                    timeStamp = "1 day ago"
                ),
                BargainOffer(
                    id = "BO12346",
                    user = "Jane Smith",
                    userId = "U002",
                    text = "Can I get free shipping for orders above $50?",
                    timeStamp = "1 day ago"
                ),
                BargainOffer(
                    id = "BO12347",
                    user = "Alex Johnson",
                    userId = "U003",
                    text = "Would you accept a counteroffer of $30 for this item?",
                    timeStamp = "2 day ago"
                ),
                BargainOffer(
                    id = "BO12348",
                    user = "Emily Davis",
                    userId = "U004",
                    text = "If I buy two items, can I get a third one free?",
                    timeStamp = "2 day ago"
                ),
                BargainOffer(
                    id = "BO12349",
                    user = "Michael Brown",
                    userId = "U005",
                    text = "Is there any holiday discount available?",
                    timeStamp = "4 day ago"
                )
            )
        )
    }
}