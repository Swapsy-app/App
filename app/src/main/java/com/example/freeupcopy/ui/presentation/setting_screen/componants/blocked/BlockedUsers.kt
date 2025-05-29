package com.example.freeupcopy.ui.presentation.setting_screen.componants.blocked

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.ui.presentation.community_screen.modals.SellerCard

@Composable
fun BlockedUsers(){
    LazyColumn(Modifier.fillMaxSize()) {
        item{ Text("Blocked Users", fontSize = 28.sp, fontWeight = FontWeight.Bold) }
        item { Spacer(Modifier.height(16.dp)) }
        items(2){
            SellerCard(
                modifier = Modifier.padding(8.dp,0.dp,8.dp,8.dp,),
                isOnline = true,
                sellerUsername = "they.call.me.zoro",
                sellerRating = "4.77",
                followUser = null,
                onSellerClick = { /*TODO*/ }
            )
        }
    }
}

@Preview(showBackground = true) @Composable
fun PreviewBlockedUsers(){
    BlockedUsers()
}