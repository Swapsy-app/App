package com.example.freeupcopy.ui.presentation.setting_screen.componants.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.freeupcopy.ui.presentation.setting_screen.componants.SettingOptions

@Composable
fun AccountSettings(
    number : String = ""
){
    Column(
        Modifier.fillMaxSize()
//            .padding(start = 16.dp, end = 16.dp)
    ) {
        SettingOptions("Mobile Number :        $number"){ }
        SettingOptions("Google Login"){ }
        SettingOptions("Delete Account"){ }
    }
}

//@Composable
//fun SettingOptions(name : String, onClick : () -> Unit){
//    Spacer(Modifier.height(12.dp))
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(24.dp, 8.dp, 24.dp, 8.dp)
//            .clickable { onClick() },
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Text(
//            text = name,
//            fontSize = 20.sp
//        )
//        Icon(
//            imageVector = Icons.Rounded.KeyboardArrowRight,
//            contentDescription = null,
//        )
//    }
//    Spacer(Modifier.height(12.dp))
//    HorizontalDivider(
//        thickness = 1.dp,
//        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
//    )
//}

@Preview(showBackground = true) @Composable
fun PreviewSetting(){
    AccountSettings(number = "8858190988")
}