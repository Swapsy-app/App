package com.example.freeupcopy.ui.presentation.product_page.componants

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SellerDetail(
    sellerName : String = "Seller Name",
    lastActive : String = "Last Active",
    followers : String = "696",
    sold : String = "12",
    rating : String = "4.5",
    numberOfReviews : String = "8",
    isFollowed : Boolean,
    onFollow : () -> Unit
){
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            Row {
                Icon(
                    imageVector = Icons.Rounded.AccountCircle,
                    contentDescription = "Seller's Profile Pic",
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                ) {
                    Text(
                        text = sellerName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = lastActive,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }
            OutlinedCard(
                modifier = Modifier.clickable {
                    onFollow()
                }
            ) {
                Row(
                    modifier = Modifier.size(90.dp, 30.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if(isFollowed) " Followed " else " + Follow " ,
                        fontSize = 18.sp
                    )
                }
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        OutlinedCard(

        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = followers,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Followers",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = sold,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Sold",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Row {
                        Icon(
                            imageVector = Icons.Rounded.Star,
                            contentDescription = "reviews icon",
                            tint = Color.Yellow
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(
                            text = rating,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = numberOfReviews + " Reviews",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Preview @Composable
fun PreviewSellerDetail(
    showBackground : Boolean = true
){
    SellerDetail(
        isFollowed = true,
        onFollow = { }
    )

}