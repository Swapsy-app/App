package com.example.freeupcopy.ui.presentation.product_page.componants

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.R

@Composable
fun DeliveryTime(
    innerPaddingValues: PaddingValues,
    userLocation : String = "User, Location",
    dateOfPickup : String = "21 Sep",
    dateOfDelivery : String = "27 Sep",
    pinCode : String,
    onPinCodeChange : (String) -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
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
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = "Delivery Time",
            fontSize = 24.sp,
        )
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                value = pinCode,
                onValueChange = { onPinCodeChange(it) },
                label = {
                    Text(text = "Pincode")
                }
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = "Delivery to : $userLocation",
            color = Color.Gray
        )
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.buy),
                    contentDescription = "buy icon",
                    modifier = Modifier
                        .size(32.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "Today",
                    fontSize = 18.sp
                )
                Text(
                    text = "Place Order",
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.right_arrow),
                contentDescription = "right arrow",
                modifier = Modifier.size(32.dp)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.truck),
                    contentDescription = "truck icon",
                    modifier = Modifier
                        .size(32.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = dateOfPickup,
                    fontSize = 18.sp
                )
                Text(
                    text = "Pickup from Seller",
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.right_arrow),
                contentDescription = "right arrow",
                modifier = Modifier.size(32.dp)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.box),
                    contentDescription = "box icon",
                    modifier = Modifier
                        .size(32.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = dateOfDelivery,
                    fontSize = 18.sp
                )
                Text(
                    text = "Deliver to You",
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
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
