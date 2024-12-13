package com.example.freeupcopy.ui.presentation.authentication_screen.connect_screen.componants

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DescriptionPart(
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(start = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "BUY,",
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            color = Color(0xFF24A6FA)
        )
        Text(
            text = "SELL,",
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            color = MaterialTheme.colorScheme.tertiary
        )
        Text(
            text = "SWAP.",
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = "The Ultimate App for All Your E-commerce Needs – Discover Amazing Deals and Swap Products to Earn!",
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
            fontStyle = FontStyle.Italic
        )

        Spacer(modifier = Modifier.size(20.dp))
    }
}