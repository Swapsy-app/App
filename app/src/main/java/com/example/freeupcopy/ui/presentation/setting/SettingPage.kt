package com.example.freeupcopy.ui.presentation.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.presentation.setting.componants.StarterSetting
import com.example.freeupcopy.ui.presentation.setting.componants.TaxInfo
import com.example.freeupcopy.ui.presentation.setting.componants.account.AccountSetting
import com.example.freeupcopy.ui.presentation.setting.componants.blocked.BlockedProduct
import com.example.freeupcopy.ui.presentation.setting.componants.need_help.NeedHelp
import com.example.freeupcopy.ui.theme.SwapGoBlue
import com.example.freeupcopy.ui.theme.SwapGoYellow

@Composable
fun SettingScreen(){
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SwapGoBlue)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column {
                    Image(
                        painter = painterResource(R.drawable.full_logo_swapgo),
                        contentDescription = "cash on delivery",
                        modifier = Modifier.width(250.dp)
                    )
                    Spacer(Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.End
                    ){
                        Spacer(Modifier.width(130.dp))
                        Text("Version 1.00.00", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SwapGoYellow)
                    }
                }
            }
        }
    ) {innerPadding->
        Column(Modifier.padding(innerPadding).padding(16.dp,16.dp,16.dp,0.dp)) {
            AccountSetting(number = "8858190988")
        }
    }
}


@Preview(showBackground = true) @Composable
fun PreviewSetting(){
    SettingScreen()
}