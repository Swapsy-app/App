package com.example.freeupcopy.ui.presentation.profile_screen.badges

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
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.theme.SwapGoBlue
import com.example.freeupcopy.ui.theme.SwapGoYellow

@Composable
fun BadgesScreen() {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SwapGoBlue)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.medal),
                contentDescription = "cash on delivery",
                modifier = Modifier.size(60.dp)
            )
            Spacer(Modifier.width(30.dp))
            Text("Our Badges", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = SwapGoYellow)
        }
        LazyColumn(Modifier.fillMaxSize()) {
            item {
                BadgeCard(
                    image = painterResource(R.drawable.starter),
                    label = "Starter",
                    disc = listOf(
                        "Posted min 2 items",
                        "Rating 3.5+",
                        "Shipped your first product",
                    ),
                    activated = true
                )
            }
            item {
                BadgeCard(
                    image = painterResource(R.drawable.achiever),
                    label = "Achiever",
                    disc = listOf(
                        "Rating 4.0+",
                        "Shipped min 7 items",
                        "Less than 25% cancellation rate",
                        "Average Shipping within 3 days after order is placed",
                    ),
                    activated = true
                )
            }
            item {
                BadgeCard(
                    image = painterResource(R.drawable.conqueror),
                    label = "Conqueror",
                    disc = listOf(
                        "Rating 4.5+",
                        "Shipped minimum 15 items",
                        "Less than 20% cancellation rate",
                        "Average Shipping within 2 days after order is placed",
                    ),
                    activated = false
                )
            }
            item {
                BadgeCard(
                    image = painterResource(R.drawable.legend),
                    label = "Legend",
                    disc = listOf(
                        "Rating 4.8+",
                        "Shipped min 25 products",
                        "Less than 10% cancellation rates",
                        "Less than 10% complain rates",
                        "Less than 20% returns",
                    ),
                    activated = false
                )
            }
            item {
                BadgeCard(
                    image = painterResource(R.drawable.trend_setter),
                    label = "Trendsetter",
                    disc = listOf(
                        "Apply Only",
                        "Influencers in a category",
                        "Professional photos of the posted item",
                        "Creating content in other social media",
                        "A certain amount of coins will be transferred to your account monthly based on your followers",
                        "An affiliated money will be transferred between 0.5 to 2% of the order value when one of your followers buys through your post.",
                    ),
                    activated = false
                )
                Spacer(Modifier.height(8.dp))
                TrendsetterButton()
            }
        }
    }
}


@Composable
fun BadgeCard(
    image : Painter,
    label : String,
    disc : List<String>,
    activated : Boolean,

){
    var open by remember { mutableStateOf(false) }

    Box(
        Modifier.padding(16.dp,16.dp,16.dp,0.dp).clickable {
            open = !open
        }.animateContentSize()
    ) {
        ElevatedCard(
            colors = CardColors(
                containerColor = Color.White,
                contentColor = if(activated) Color.DarkGray else Color.Gray,
                disabledContainerColor = Color.White,
                disabledContentColor = if(activated) Color.DarkGray else Color.Gray
            )
        ) {
            Row(
                Modifier.fillMaxWidth().padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = image,
                    contentDescription = label,
                    modifier = Modifier.size(70.dp)
                )
                Spacer(Modifier.width(16.dp))
                Column {
                    if (open){
                        Text(text = if (activated) label else "\uD83D\uDD12 $label", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        disc.forEach {
                            Text("\uD83D\uDD38 $it")
                            Spacer(Modifier.height(4.dp))
                        }
                    }
                    else{
                        Text(text = if (activated) label else "\uD83D\uDD12 $label", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun TrendsetterButton() {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(48.dp)
            .background(
                SwapGoBlue,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
            )
            .clickable {

            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Unlock Trendsetter Benefits",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true) @Composable
private fun BadgePreview(){
    BadgesScreen()
}