package com.example.freeupcopy.ui.presentation.setting.componants.need_help

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardColors
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.ui.presentation.setting_screen.componants.SettingOptions

@Composable
fun NeedHelp(){
    Column(
        Modifier.fillMaxSize()
            .padding(16.dp)
    ) {
        ElevatedCard(
            colors = CardColors(
                containerColor = Color(0xFFDDEFF7),
                contentColor = Color.Black,
                disabledContentColor = Color.Black,
                disabledContainerColor = Color(0xFFEDF2F7)
            )
        ) {
            SettingOptions("My Tickets"){ }
        }
        Spacer(Modifier.height(16.dp))
        ElevatedCard(
            colors = CardColors(
                containerColor = Color(0xFFDDEFF7),
                contentColor = Color.Black,
                disabledContentColor = Color.Black,
                disabledContainerColor = Color(0xFFEDF2F7)
            )
        ) {

            SettingOptions("Buying"){ }
            SettingOptions("How Coins Work"){ }
            SettingOptions("Selling"){ }
            SettingOptions("Account"){ }
            SettingOptions("About"){ }
            SettingOptions("Community Safety"){ }
        }
    }
}

@Preview(showBackground = true) @Composable
fun PreviewNeedHelp(){
    NeedHelp()
}