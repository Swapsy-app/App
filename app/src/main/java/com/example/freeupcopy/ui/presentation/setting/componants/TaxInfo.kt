package com.example.freeupcopy.ui.presentation.setting.componants

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.ui.theme.SwapGoBlue
import com.example.freeupcopy.ui.theme.SwapGoYellow

@Composable
fun TaxInfo(){
    Box {
        Column(Modifier.fillMaxSize()) {
            Text("Manage Tax Info", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("15 digit GSTIN") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            Text("Note: If you are selling your product from a GST registered buisness, you are required to provide GSTIN. By not providing the GSTIN, you are accepting that you are not registered under GST act", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(vertical = 16.dp)
                .align(alignment = Alignment.BottomCenter)
                .clickable {  }
                .background(color = SwapGoBlue),

            ) {
            Text("Save", fontSize = 24.sp, color = SwapGoYellow, fontWeight = FontWeight.Bold, modifier = Modifier.align(
                Alignment.Center))
        }
    }
}
@Preview(showBackground = true) @Composable
fun PreviewTaxInfo(){
    TaxInfo()
}