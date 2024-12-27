package com.example.freeupcopy.ui.presentation.product_screen.componants

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.presentation.sell_screen.location_screen.add_location_screen.AddLocationUiEvent
import com.example.freeupcopy.ui.theme.SwapsyTheme
import com.example.freeupcopy.ui.theme.TextFieldShape
import com.example.freeupcopy.utils.clearFocusOnKeyboardDismiss

@Composable
fun DeliveryTime(
    userLocation: String,
    dateOfPickup: String,
    dateOfDelivery: String,
    pinCode: String,
    onPinCodeChange: (String) -> Unit,
) {
    val focusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp)
    ) {
        Text(
            text = "Delivery Time",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.size(16.dp))

        OutlinedTextField(
            modifier = Modifier
                .clearFocusOnKeyboardDismiss()
                .focusRequester(focusRequester)
                .fillMaxWidth(),
            value = pinCode,
            onValueChange = {
                onPinCodeChange(it)
            },
            label = {
                Text("Pincode")
            },
            placeholder = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "e.g., 560001",
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f),
                    fontStyle = FontStyle.Italic,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            maxLines = 1,
            keyboardActions = KeyboardActions(
                onDone = {

                    focusManager.clearFocus()
                }
            ),
            shape = TextFieldShape
        )

        Text(
            modifier = Modifier.padding(start = 8.dp, top = 2.dp),
            text = "Delivery to : $userLocation",
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f),
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.size(20.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.buy),
                    contentDescription = "buy icon",
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "Today",
                    fontWeight = FontWeight.W500,
                )
                Text(
                    text = "Place Order",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W300,
                    textAlign = TextAlign.Center
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.right_arrow),
                contentDescription = "right arrow",
                modifier = Modifier.size(32.dp),
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.truck),
                    contentDescription = "truck icon",
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = dateOfPickup,
                    fontWeight = FontWeight.W500,
                )
                Text(
                    text = "Pickup from Seller",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W300,
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
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = dateOfPickup,
                    fontWeight = FontWeight.W500,
                )
                Text(
                    text = "Deliver to You",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W300,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewDeliveryTime() {
    SwapsyTheme {
        DeliveryTime(
            pinCode = "",
            onPinCodeChange = {},
            userLocation = "Delhi",
            dateOfPickup = "24 Sep",
            dateOfDelivery = "26 Sep"
        )
    }
}
