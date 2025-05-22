package com.example.freeupcopy.ui.presentation.setting_screen.componants.blocked

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.freeupcopy.ui.presentation.setting_screen.componants.SettingOptions

@Composable
fun BlockedContent(){
    Column(Modifier.fillMaxSize()) {
        SettingOptions("Blocked Products") { }
        SettingOptions("Blocked Users") { }
    }
}
@Preview(showBackground = true) @Composable
fun PreviewBlocked(){
    BlockedContent()
}