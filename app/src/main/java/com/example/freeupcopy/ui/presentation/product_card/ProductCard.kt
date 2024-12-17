package com.example.freeupcopy.ui.presentation.product_card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.PinnableContainer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.theme.CashColor1
import com.example.freeupcopy.ui.theme.CashColor2
import com.example.freeupcopy.ui.theme.CoinColor1
import com.example.freeupcopy.ui.theme.CoinColor2
import com.example.freeupcopy.ui.theme.OfferColor1
import com.example.freeupcopy.ui.theme.OfferColor2

@Composable
fun ProductCard(
    containerColor: Color = Color.White,
    companyName : String?,
    productName : String,
    productThumbnail : Painter?,
    size : String,
    priceOffered : String?,
    coinsOffered : String?,
    specialOffer : List<String>?,
    priceOriginal : String,
    badge : String?,
    availablePurchaseOptions : List<Boolean> = listOf((priceOffered != null),(coinsOffered != null),(specialOffer != null)),
    isLiked : Boolean,
    onLikeClick: () -> Unit,
    priorityPriceType : Int?,
    isClothes : Boolean
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(180.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardColors(
            contentColor = Color.Black,
            containerColor = containerColor,
            disabledContentColor = Color.Gray,
            disabledContainerColor = Color.LightGray
        )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Image Section
            Box(modifier = Modifier.height(180.dp)) {
                Image(
                    painter = productThumbnail ?: painterResource(id = R.drawable.add_image),
                    contentDescription = "Product Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                IconButton(
                    onClick = { onLikeClick() },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                        .background(Color.White.copy(alpha = 0.7f), CircleShape)
                        .size(36.dp)
                ) {
                    Icon(
                        imageVector = if(isLiked) Icons.Default.Favorite else Icons.Rounded.FavoriteBorder,
                        contentDescription = "Favorite Icon",
                        tint = if(isLiked) Color.Red else Color.Gray
                    )
                }
            }

            // Product Details Section
            Column(modifier = Modifier.padding(8.dp)) {
                if(companyName != null){
                    Text(
                        text = companyName.toString(), // Replace with product name
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        color = Color.Gray,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = productName, // Replace with product name
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                if(isClothes){
                    Text(
                        text = size, // Replace with product details
                        color = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                if(priorityPriceType != null){
                    if (priorityPriceType == 0){
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
                    else{
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
                }
                else{
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

                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if(badge != null){
                        Text(
                            text = badge.toString(), // Replace with tag
                            color = Color.Green
                        )
                    }
                }
                Spacer(modifier = Modifier.size(8.dp))
                Row {
                    if(availablePurchaseOptions[0]){
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(100))
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
                            Icon(
                                modifier = Modifier.size(16.dp),
                                painter = painterResource(id = R.drawable.ic_cash),
                                contentDescription = "cash",
                                tint = CashColor2
                            )
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                    if(availablePurchaseOptions[1]){
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(100))
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
                            Icon(
                                modifier = Modifier.size(16.dp),
                                painter = painterResource(id = R.drawable.ic_coin),
                                contentDescription = "coin",
                                tint = CoinColor2
                            )
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                    if(availablePurchaseOptions[2]){
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(100))
                                .border(
                                    2.5.dp,
                                    Brush.linearGradient(
                                        colors = listOf(OfferColor1, OfferColor2),
                                        start = Offset(0f, 0f),
                                        end = Offset(100f, 100f) // Adjust these offsets for tilt
                                    ),
                                    shape = CircleShape
                                )
                                .padding(horizontal = 8.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Row {
                                Icon(
                                    modifier = Modifier.size(16.dp),
                                    painter = painterResource(id = R.drawable.ic_coin),
                                    contentDescription = "coin",
                                    tint = CoinColor2
                                )
                                Icon(
                                    modifier = Modifier.size(16.dp),
                                    imageVector = Icons.Rounded.Add,
                                    contentDescription = "plus",
                                    tint = OfferColor2
                                )
                                Icon(
                                    modifier = Modifier.size(16.dp),
                                    painter = painterResource(id = R.drawable.ic_cash),
                                    contentDescription = "cash",
                                    tint = CashColor2
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true) @Composable
fun PreviewProductCard(){
    ProductCard(
        companyName = "Adidas",
        productName = "Adidas Bomber Jacket",
        size = "40 inches",
        productThumbnail = painterResource(id = R.drawable.bomber_jacket),
        priceOffered = null,
        coinsOffered = null,
        specialOffer = listOf("4000","2000"),
        priceOriginal = "2000",
        badge = "Trusted",
        isLiked = true,
        onLikeClick = {},
        priorityPriceType = null,
        isClothes = true
    )
}