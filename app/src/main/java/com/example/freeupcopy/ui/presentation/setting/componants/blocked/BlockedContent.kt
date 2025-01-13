package com.example.freeupcopy.ui.presentation.setting.componants.blocked

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.ui.presentation.setting.componants.SettingOptions

@Composable
fun BlockedContent(){
    Column(Modifier.fillMaxSize()) {
        Text("Blocked Content", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        SettingOptions("Blocked Products") { }
        SettingOptions("Blocked Users") { }
    }
}
@Preview(showBackground = true) @Composable
fun PreviewBlocked(){
    BlockedContent()
}