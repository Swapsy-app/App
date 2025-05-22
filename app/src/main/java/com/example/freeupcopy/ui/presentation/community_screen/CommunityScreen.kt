package com.example.freeupcopy.ui.presentation.community_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.ui.presentation.community_screen.modals.SellerCard
import com.example.freeupcopy.ui.theme.SwapGoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            Column {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    title = { Text("Community", fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.12f),
                    thickness = 1.dp
                )
            }
        }
    ) { innerPadding->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
//                .background(MaterialTheme.colorScheme.surface)
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
    SwapGoTheme {
        CommunityScreen(
            onBackClick = {}
        )
    }
}