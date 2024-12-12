package com.example.freeupcopy.ui.presentation.authentication_screen.otp_screen.componants

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.utils.dashedLine
import com.example.freeupcopy.utils.formatTime

@Composable
fun ResendOtp(
    modifier: Modifier = Modifier,
    isResendEnabled: Boolean,
    cooldownTime: Int,
    onResendClick: () -> Unit
) {
    val resendColor = if (isResendEnabled)
        MaterialTheme.colorScheme.primary
    else
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Didn't receive OTP? ",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            modifier = Modifier
                .dashedLine(
                    color = resendColor,
                    strokeWidth = 2.dp,
                    dashWidth = 5.dp,
                    dashGap = 4.dp
                )
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onResendClick()
//                    if (isResendEnabled) {
//                        // Trigger resend logic here
//                        isResendEnabled = false
//                        cooldownTime = 60 // Reset cooldown
//                    }

                },
            text = if (isResendEnabled) "Resend" else formatTime(cooldownTime),
            fontSize = 14.sp,
            color = resendColor,
            fontWeight = FontWeight.Bold,
            fontStyle = if (isResendEnabled) FontStyle.Italic else FontStyle.Normal
        )
    }
}