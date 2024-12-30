package com.example.freeupcopy.ui.presentation.product_screen.componants

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.freeupcopy.ui.theme.CardShape
import com.example.freeupcopy.ui.theme.CashColor1
import com.example.freeupcopy.ui.theme.CashColor2
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

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = "location icon",
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f),
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(

                text = "Delivery to:",
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f),
                fontSize = 15.sp
            )
        }
        Spacer(modifier = Modifier.size(4.dp))
        Row(
            modifier = Modifier.height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .clearFocusOnKeyboardDismiss()
                    .focusRequester(focusRequester)
                    .weight(1f),
                value = pinCode,
                onValueChange = {
                    onPinCodeChange(it)
                },
                placeholder = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Enter Pincode",
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
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.02f),
                ),
                shape = TextFieldShape
            )

            ElevatedButton(
                modifier = Modifier.fillMaxHeight(),
                onClick = {

                },
                shape = TextFieldShape,
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color(0xFFF1E6FF),
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Check",
                    fontWeight = FontWeight.SemiBold
                )
            }

        }
        Text(
            modifier = Modifier.padding(top = 2.dp, start = 4.dp),
            text = userLocation,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
        )

        Spacer(modifier = Modifier.size(8.dp))

        ExpectedDeliveryRow()
    }
}

@Composable
fun ExpectedDeliveryRow(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .clip(CardShape.medium)
            .background(MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.03f))
            .padding(16.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_calendar),
            contentDescription = "calendar icon",
        )
        Spacer(modifier = Modifier.size(16.dp))
        Column {
             Text(
                text = "Expected Delivery",
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
                 fontSize = 14.sp
            )
            Text(
                text = "24 September 2024",
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                //color = CashColor2
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDeliveryTime() {
    SwapsyTheme {
        DeliveryTime(
            pinCode = "",
            onPinCodeChange = {},
            userLocation = "Mumbai, Maharashtra",
            dateOfPickup = "24 Sep",
            dateOfDelivery = "26 Sep"
        )
        //ExpectedDeliveryRow()
    }
}
