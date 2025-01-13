package com.example.freeupcopy.ui.presentation.profile_screen.shipping_label

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.ui.presentation.profile_screen.Faqs
import com.example.freeupcopy.ui.theme.LightBlue

@Composable
fun FreeUpShippingLabelsScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item { HeaderSection() } // 🚀 Header with key benefits
        item { StepOneSection() } // 📦 Step 1: Get Shipping Labels
        item { StepsSection() } // ✅ Steps 2-4: Using Labels

        val faqs = listOf(
            "💰 Does combo discount apply on cash or coins items?" to "✅ Yes, combo discounts apply to both cash and coin items.",
            "🤔 How will buyers know I am giving discounts on combos?" to "📢 Your discounts will be visible on the product page when a buyer selects multiple items.",
            "🔄 Will buyers get a combo discount in addition to an offer price I've accepted?" to "✅ Yes, the combo discount will apply even if you've accepted an offer."
        )

        item{
            Column(modifier = Modifier.padding(16.dp)) {
                Text("❓ FAQs", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp))

                faqs.forEach { (question, answer) ->
                    Faqs(question, answer)
                }
            }
        }
    }
}

@Composable
fun HeaderSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightBlue)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            HeaderItem("Lost & Damage\nProtection \uD83D\uDEE1\uFE0F")
            HeaderItem("On-Time\nDeliveries ⏳")
            HeaderItem("Better Ratings &\nMore Sales \uD83D\uDCC8")
        }
    }
}

@Composable
fun StepsSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("🔄 NEXT", fontWeight = FontWeight.Bold, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
        Text("\uD83D\uDCDC Use labels when you receive orders", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(8.dp))
        val steps = listOf(
            "📋 Go to Confirm Pickup Page\n📍 Select 'Scan Shipping Label'",
            "📷 Scan the barcode from the App\n📦 Address details will be auto-filled",
            "📝 Write buyer’s name & address\n🏷️ Paste label on the parcel"
        )
        steps.forEach {
            StepItem(steps.indexOf(it) + 2, it)
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
fun HeaderItem(text: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text, fontSize = 14.sp, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun StepOneSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("🔹 STEP 1", fontWeight = FontWeight.Bold, color = Color.Gray)
        Spacer(Modifier.height(8.dp))
        Text("📦 Get many shipping labels in advance", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            colors = CardColors(
                containerColor = Color(0xFFDDEFF7),
                contentColor = Color.Black,
                disabledContentColor = Color.Black,
                disabledContainerColor = Color(0xFFEDF2F7)
            ),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("🚀 Order from SwapGo", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(4.dp))

                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = { /* Order Now Action */ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonColors(
                        contentColor = Color.White,
                        containerColor = Color(0xFF002F6C),                        disabledContentColor = Color.White,
                        disabledContainerColor = Color(0xFF002F6C)
                    )
                ) {
                    Text("🛒 Order Now")
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text("⚡ OR", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                Text("🖨️ Print Yourself", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { /* Generate Labels */ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonColors(
                        contentColor = Color.White,
                        containerColor = Color(0xFF002F6C),                        disabledContentColor = Color.White,
                        disabledContainerColor = Color(0xFF002F6C)
                    )
                ) {
                    Text("🖨️ Generate New Labels")
                }
            }
        }
    }
}

@Composable
fun StepItem(stepNumber: Int, text: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text("🔹 STEP $stepNumber", fontWeight = FontWeight.Bold, color = Color.Gray)
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            colors = CardColors(
                containerColor = Color(0xFFDDEFF7),
                contentColor = Color.Black,
                disabledContentColor = Color.Black,
                disabledContainerColor = Color(0xFFEDF2F7)
            )
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(text, fontSize = 14.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFreeUpShippingLabelsScreen() {
    FreeUpShippingLabelsScreen()
}
