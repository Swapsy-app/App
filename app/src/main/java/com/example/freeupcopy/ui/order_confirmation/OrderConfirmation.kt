package com.example.freeupcopy.ui.order_confirmation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.theme.SwapGoBlue
import com.example.freeupcopy.ui.theme.SwapGoYellow

@Composable
fun OrderDetailsScreen(
    viewModel: OrderStateViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SwapGoBlue)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Order Details", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = SwapGoYellow)
            }
        }
    ) { innerPadding ->
        Box(Modifier.fillMaxSize()){
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                OrderTrackingScreen(time = state.orderPlacedDate)
                Spacer(modifier = Modifier.height(16.dp))
                OrderInfoSection(time = state.orderPlacedDate)
                Spacer(modifier = Modifier.height(16.dp))
                ReceiverInfoSection(state.receiver,state.location)
                Spacer(modifier = Modifier.height(16.dp))
                PriceDetailsSection(state.finalPrice,state.deliveryCharge,state.finalEarning)
                Spacer(modifier = Modifier.height(16.dp))
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(vertical = 16.dp)
                    .align(alignment = Alignment.BottomCenter)
                    .clickable {  }
                    .background(color = SwapGoBlue),

                ) {
                Text("Save", fontSize = 24.sp, color = SwapGoYellow, fontWeight = FontWeight.Bold, modifier = Modifier.align(
                    Alignment.Center))
            }
        }
    }
}

@Composable
fun OrderTrackingScreen(
    time: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OrderStatusItem("Order Placed", time, true)
        OrderStatusItem("Confirmed by seller", "", false)
        OrderStatusItem("Shipped", "", false)
        OrderStatusItem("Out for Delivery", "", false)
        OrderStatusItem("Arriving Soon", "", false)
    }
}

@Composable
fun OrderStatusItem(status: String, time: String, isCompleted: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(
                        if (isCompleted) SwapGoBlue else Color.Gray,
                        shape = CircleShape
                    )
            )
            if (status != "Arriving Soon") {
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(24.dp)
                        .background( if (isCompleted) SwapGoBlue else Color.Gray)
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = status, fontWeight = FontWeight.Bold)
            if (time.isNotEmpty()) {
                Text(text = time, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun OrderInfoSection(
    time: String
) {
    ElevatedCard(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp),colors = CardColors(
        containerColor = Color(0xFFDDEFF7),
        contentColor = Color.Black,
        disabledContentColor = Color.Black,
        disabledContainerColor = Color(0xFFEDF2F7)
    )) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row {
                Text("Order Created", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
                Text(time)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text("Order ID", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
                Text("Copy", color = SwapGoBlue, modifier = Modifier.clickable { })
            }
        }
    }
}

@Composable
fun ReceiverInfoSection(
    buyerName : String,
    buyerAddress : String
) {
    ElevatedCard(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp),colors = CardColors(
        containerColor = Color(0xFFDDEFF7),
        contentColor = Color.Black,
        disabledContentColor = Color.Black,
        disabledContainerColor = Color(0xFFEDF2F7)
    ))  {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Receiver: $buyerName", fontWeight = FontWeight.Bold)
            Text(buyerAddress)
        }
    }
}

@Composable
fun PriceDetailsSection(
    finalPrice : String,
    delivery : String,
    earning : String
) {
    ElevatedCard(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp),colors = CardColors(
        containerColor = Color(0xFFDDEFF7),
        contentColor = Color.Black,
        disabledContentColor = Color.Black,
        disabledContainerColor = Color(0xFFEDF2F7)
    ))  {
        Column(modifier = Modifier.padding(16.dp)) {
            Row {
                Text("Final Price")
                Spacer(modifier = Modifier.weight(1f))
                Text(finalPrice)
            }
            Row {
                Text("Pickup & Delivery")
                Spacer(modifier = Modifier.weight(1f))
                Text(delivery)
            }
            Row {
                Text("You Earn", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
                Text(earning, fontWeight = FontWeight.Bold)
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewOrderDetails() {
    OrderDetailsScreen()
}
