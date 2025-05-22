package com.example.freeupcopy.ui.presentation.setting_screen.componants

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.domain.enums.Settings

@Composable
fun StarterSetting(
    token: String?,
    onShowLoginBottomSheet: () -> Unit,
    onNavigate: (Settings) -> Unit,
    onLogOutClick: () -> Unit
) {
    LazyColumn(
        Modifier.fillMaxSize()
    ) {
        items(Settings.entries) { setting ->
            SettingOptions(
                name = setting.valueName,
                onClick = {
                    // Check if this is the account/tax info setting and if token is null
                    if ((setting == Settings.ADDRESS || setting == Settings.MANAGE_TAX_INFO) && token == null) {
                            onShowLoginBottomSheet()
                    } else {
                        // Either it's not an account setting or token is not null
                        onNavigate(setting)
                    }
                }
            )
        }
        if (token != null) {
            item {
                SettingOptions(
                    "Log Out",
                    onClick = {
                        onLogOutClick()
                    }
                )
            }
        }
    }
}

@Composable
fun SettingOptions(
    name : String,
    onClick : () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(
                start = 20.dp,
                top = 20.dp,
                end = 20.dp,
                bottom = 20.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name,
            fontSize = 15.sp
        )
        Icon(
            imageVector = Icons.Rounded.KeyboardArrowRight,
            contentDescription = null,
        )
    }
    HorizontalDivider(
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
    )
}

@Preview(showBackground = true) @Composable
fun PreviewSetting(){
    StarterSetting(
        token = null,
        onNavigate = {},
        onLogOutClick = {},
        onShowLoginBottomSheet = {}
    )
}