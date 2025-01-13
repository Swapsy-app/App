package com.example.freeupcopy.ui.presentation.purchases.componants

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.theme.CoinColor2
import com.example.freeupcopy.ui.theme.OfferColor2
import java.util.Date

@Composable
fun PurchaseCard(
    image : Painter,
    title: String,
    priceOffered : String?,
    coinsOffered : String?,
    specialOffer : List<String>?,
    availablePurchaseOptions : List<Boolean> = listOf((priceOffered != null),(coinsOffered != null),(specialOffer != null)),
    status : Status = Status.Cancelled,
    forPurchaseHistory : Boolean = false,
    date : String = "",
    priceOriginal : String,
){
    ElevatedCard(
        modifier = Modifier.padding(16.dp,16.dp,16.dp,0.dp),
        colors = CardColors(
            containerColor = Color(0xFFDDEFF7),
            contentColor = Color.Black,
            disabledContentColor = Color.Black,
            disabledContainerColor = Color(0xFFEDF2F7)
        )
    ) {
        Row(
            Modifier.fillMaxWidth().padding(12.dp)
        ) {
            Image(
                painter = image,
                contentDescription = "image",
                modifier = Modifier.size(80.dp).clip(RoundedCornerShape(8.dp)).shadow(elevation = 32.dp, shape = RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(24.dp))
            if (forPurchaseHistory){
                Column {
                    Text(
                        title,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.height(4.dp))
                    if (availablePurchaseOptions[0]){
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "₹${priceOffered}", // Replace with discounted price
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Row{
                                Text(
                                    text = "₹$priceOriginal", // Replace with discounted price
                                    color = Color.Gray,
                                    textDecoration = TextDecoration.LineThrough
                                )
                            }
                        }
                    }
                    else if(availablePurchaseOptions[1]){
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                painter = painterResource(id = R.drawable.ic_coin),
                                contentDescription = "coin",
                                tint = CoinColor2
                            )
                            Spacer(modifier = Modifier.size(4.dp))
                            Text(
                                text = "${coinsOffered}", // Replace with discounted price
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Row{
                                Text(
                                    text = "₹$priceOriginal", // Replace with discounted price
                                    color = Color.Gray,
                                    textDecoration = TextDecoration.LineThrough
                                )
                            }
                        }
                    }
                    else if(availablePurchaseOptions[2]){
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "₹${specialOffer?.get(0)}", // Replace with discounted price
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                modifier = Modifier.size(16.dp),
                                imageVector = Icons.Rounded.Add,
                                contentDescription = "coin",
                                tint = OfferColor2
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                modifier = Modifier.size(16.dp),
                                painter = painterResource(id = R.drawable.ic_coin),
                                contentDescription = "coin",
                                tint = CoinColor2
                            )
                            Spacer(modifier = Modifier.size(4.dp))
                            Text(
                                text = "${specialOffer?.get(1)}", // Replace with discounted price
                                color = Color.Black
                            )
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    Card(
                        colors = CardColors(
                            containerColor = when(status) {
                                Status.Cancelled -> Color(0xFFF6E5E4)
                                Status.Delivered -> Color(0xFFE5F6E4)
                                else -> Color(0xFFFFF6E4)
                            },
                            contentColor = when(status) {
                                Status.Cancelled -> Color(0xFFB22222)
                                Status.Delivered -> Color(0xFF006400)
                                else -> Color(0xFFAA6C00)
                            },
                            disabledContentColor = Color.Black,
                            disabledContainerColor = Color(0xFFF6E5E4)

                        ),
                    ) {
                        Text(status.name + "  " +date,modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Bold)
                    }
                }
            }
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.height(80.dp)
            ) {
                Text(
                    title,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                if (availablePurchaseOptions[0]){
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "₹${priceOffered}", // Replace with discounted price
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Row{
                            Text(
                                text = "₹$priceOriginal", // Replace with discounted price
                                color = Color.Gray,
                                textDecoration = TextDecoration.LineThrough
                            )
                        }
                    }
                }
                else if(availablePurchaseOptions[1]){
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            painter = painterResource(id = R.drawable.ic_coin),
                            contentDescription = "coin",
                            tint = CoinColor2
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(
                            text = "${coinsOffered}", // Replace with discounted price
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Row{
                            Text(
                                text = "₹$priceOriginal", // Replace with discounted price
                                color = Color.Gray,
                                textDecoration = TextDecoration.LineThrough
                            )
                        }
                    }
                }
                else if(availablePurchaseOptions[2]){
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "₹${specialOffer?.get(0)}", // Replace with discounted price
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            modifier = Modifier.size(16.dp),
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "coin",
                            tint = OfferColor2
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            modifier = Modifier.size(16.dp),
                            painter = painterResource(id = R.drawable.ic_coin),
                            contentDescription = "coin",
                            tint = CoinColor2
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(
                            text = "${specialOffer?.get(1)}", // Replace with discounted price
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

enum class Status{
    Cancelled,
    Ordered,
    Shipped,
    Delivered
}

@Preview(showBackground = true) @Composable
fun PreviewPurchaseCard(){
    PurchaseCard(
        image = painterResource(R.drawable.bomber_jacket),
        title = "Adidas Bomber Jacket",
        priceOffered = null,
        coinsOffered = null,
        specialOffer = listOf("4000","2000"),
        priceOriginal = "2000",
        status = Status.Cancelled,
        date = "10 Feb 2025"
    )
}