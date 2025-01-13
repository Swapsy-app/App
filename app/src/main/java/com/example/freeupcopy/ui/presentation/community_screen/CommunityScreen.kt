package com.example.freeupcopy.ui.presentation.community_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.ui.presentation.community_screen.modals.SellerCard
import com.example.freeupcopy.ui.theme.SwapsyTheme

@Composable
fun CommunityScreen(modifier: Modifier = Modifier) {
    Scaffold {innerPadding->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(innerPadding)
        ) {
            item {
                Row(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                ) {
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Latest from the Members I follow",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(Modifier.width(16.dp))
                    Icon(
                        imageVector = Icons.Rounded.ArrowForward,
                        contentDescription = null,
                    )
                }
            }
            items(5){
                SellerCard(
                    modifier = Modifier.padding(8.dp,0.dp,8.dp,8.dp,),
                    isOnline = true,
                    sellerUsername = "they.call.me.zoro",
                    sellerRating = "4.77"
                )
            }

        }
    }

}
@Preview(showBackground = true) @Composable
fun PreviewCommunityScreen(){
    SwapsyTheme {
        CommunityScreen()
    }
}