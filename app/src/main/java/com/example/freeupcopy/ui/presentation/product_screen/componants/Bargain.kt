package com.example.freeupcopy.ui.presentation.product_screen.componants

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.R
import com.example.freeupcopy.domain.model.BargainOffers

@Composable
fun BargainElement(
    bargainOffers: List<BargainOffers>,
    innerPaddingValues : PaddingValues,
    onOpenPopup : () -> Unit
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "BARGAIN OFFER",
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.size(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color.Black
                )
                .clickable {
                    onOpenPopup()
                }
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                text = "Make an Offer",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        bargainOffers.forEach{
            BargainOffers(bargainOffer = it)
            Spacer(modifier = Modifier.size(16.dp))
        }
        Box {
            Divider(
                Modifier
                    .fillMaxWidth()
                    .absoluteOffset(x = -(innerPaddingValues.calculateTopPadding())),
                thickness = 8.dp,
                color = Color.LightGray
            )
            Divider(
                Modifier
                    .fillMaxWidth()
                    .absoluteOffset(x = (innerPaddingValues.calculateTopPadding())),
                thickness = 8.dp,
                color = Color.LightGray
            )
        }
    }
}

@Composable
fun BargainOffers(
    bargainOffer: BargainOffers
){
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row {
                Text(
                    text = bargainOffer.user,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(8.dp))
                Column {
                    Text(
                        text = bargainOffer.text,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = bargainOffer.timeStamp,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}

@Composable
fun BargainPopup(
    isRupeeSelected: Boolean,
    optionFocused : Int,
    listedPrice : String,
    recommendation : List<List<String>>,
    onCoin : () -> Unit,
    onRupee : () -> Unit,
    onFocus0 : () -> Unit,
    onFocus1 : () -> Unit,
    onFocus2 : () -> Unit,
    bargainText : String,
    onChangeBargainText : (String) -> Unit,
    messageToSeller : String,
    onChangeMessageToSeller : (String) -> Unit,
    onClosePopup : () -> Unit
){
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

@Preview(
    showBackground = true
)
@Composable
fun BargainPopupPreview(){
    BargainPopup(
        isRupeeSelected = true,
        optionFocused = 1,
        listedPrice = "200",
        recommendation = listOf(listOf("150","180"),listOf("500","800")),
        onCoin = {},
        onRupee = {},
        onFocus0 = {},
        onFocus1 = {},
        onFocus2 = {},
        bargainText = "",
        onChangeBargainText = {},
        messageToSeller = "",
        onChangeMessageToSeller = {},
        onClosePopup = {}
    )
}

@Preview(
    showBackground = true
)
@Composable
fun BargainElementPreview(){
    BargainElement(
        innerPaddingValues = PaddingValues(0.dp),
        onOpenPopup = {},
        bargainOffers = listOf(
            BargainOffers(
                id = "BO12345",
                user = "John Doe",
                userId = "U001",
                text = "Looking for a 20% discount on bulk orders.",
                timeStamp = "24 Dec"
            ),
            BargainOffers(
                id = "BO12346",
                user = "Jane Smith",
                userId = "U002",
                text = "Can I get free shipping for orders above $50?",
                timeStamp = "23 Dec"
            ),
            BargainOffers(
                id = "BO12347",
                user = "Alex Johnson",
                userId = "U003",
                text = "Would you accept a counteroffer of $30 for this item?",
                timeStamp = "22 Dec"
            ),
            BargainOffers(
                id = "BO12348",
                user = "Emily Davis",
                userId = "U004",
                text = "If I buy two items, can I get a third one free?",
                timeStamp = "21 Dec"
            ),
            BargainOffers(
                id = "BO12349",
                user = "Michael Brown",
                userId = "U005",
                text = "Is there any holiday discount available?",
                timeStamp = "20 Dec"
            )
        )
    )
}