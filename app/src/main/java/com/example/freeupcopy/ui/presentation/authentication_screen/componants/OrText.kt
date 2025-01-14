package com.example.freeupcopy.ui.presentation.authentication_screen.componants

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun OrText(
    modifier: Modifier = Modifier,
    text: String = "Or",
    color: Color
) {
    Column(modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(
                modifier = Modifier
                    .size(1.dp)
                    .background(color.copy(alpha = 0.2f))
                    .weight(1f)
            )
            Text(
                text = text,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                color = color,
                textAlign = TextAlign.Center
            )
            Spacer(
                modifier = Modifier
                    .size(1.dp)
                    .weight(1f)
                    .background(color.copy(alpha = 0.2f))
            )

        }
    }

}
