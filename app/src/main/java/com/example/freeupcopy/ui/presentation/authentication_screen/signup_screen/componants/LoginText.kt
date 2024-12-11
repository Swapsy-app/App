package com.example.freeupcopy.ui.presentation.authentication_screen.signup_screen.componants

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.ui.theme.LinkColor

@Composable
fun LoginText(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Already have an account?  ",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            fontSize = 15.sp,
            modifier = modifier
        )
        Text(
            modifier = Modifier.clickable {
                onLoginClick()
            },
            text = "Login",
            color = LinkColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500
        )
    }
}