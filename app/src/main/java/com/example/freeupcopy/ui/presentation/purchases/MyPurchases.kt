package com.example.freeupcopy.ui.presentation.purchases

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.presentation.profile_screen.ExtraScreenViewModel
import com.example.freeupcopy.ui.presentation.profile_screen.holiday.DatePickerDocked
import com.example.freeupcopy.ui.presentation.purchases.componants.PurchaseCard
import com.example.freeupcopy.ui.presentation.purchases.componants.Status
import com.example.freeupcopy.ui.presentation.setting.componants.TaxInfo
import com.example.freeupcopy.ui.theme.SwapGoBlue
import com.example.freeupcopy.ui.theme.SwapGoYellow

@Composable
fun Purchases(
    viewModel: ExtraScreenViewModel = viewModel()
){
    val state by viewModel.state.collectAsState()

    Box {
        Column(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SwapGoBlue)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Image(
                        painter = painterResource(R.drawable.shopping_cart),
                        contentDescription = "cash on delivery",
                        modifier = Modifier.size(60.dp)
                    )
                    Spacer(Modifier.width(30.dp))
                    Text("Purchases", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = SwapGoYellow)
                }
            }
            LazyColumn() {
                items(5){
                    PurchaseCard(
                        image = painterResource(R.drawable.bomber_jacket),
                        title = "Adidas Bomber Jacket",
                        priceOffered = null,
                        coinsOffered = null,
                        specialOffer = listOf("4000","2000"),
                        priceOriginal = "2000",
                        status = Status.Shipped,
                        date = "12 Feb"
                    )
                }
            }
        }

    }
}


@Preview(showBackground = true) @Composable
fun PreviewPurchases(){
    Purchases()
}