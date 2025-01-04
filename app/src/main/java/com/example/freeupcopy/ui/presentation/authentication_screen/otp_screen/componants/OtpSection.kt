package com.example.freeupcopy.ui.presentation.authentication_screen.otp_screen.componants

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.ui.presentation.authentication_screen.componants.OtpTextField
import com.example.freeupcopy.ui.theme.BottomSheetShape
import com.example.freeupcopy.ui.theme.ButtonShape

@Composable
fun OtpSection(
    modifier: Modifier = Modifier,
    email: String,
    otpValues: List<String>,
    //isVerifyEnabled: Boolean,
    isResendEnabled: Boolean,
    cooldownTime: Int,
    onSuccessfulVerification: () -> Unit,
    onResendClick: () -> Unit,
    onUpdateOtpValuesByIndex: (Int, String) -> Unit,
    onOtpInputComplete: (Boolean) -> Unit
) {

    Column(
        modifier
            .fillMaxSize()
            .clip(BottomSheetShape)
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier.align(Alignment.Start),
            text = "Email sent to $email",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            fontStyle = FontStyle.Italic
        )

        Spacer(modifier = Modifier.height(30.dp))

        OtpTextField(
            otpValues = otpValues,
            otpLength = 6,
            onUpdateOtpValuesByIndex = { index, value ->
                onUpdateOtpValuesByIndex(index, value)
            },
            onOtpInputComplete = {
                onOtpInputComplete(otpValues.all { it.isNotEmpty() })
            },
            isError = false
        )

        Spacer(modifier = Modifier.height(10.dp))

        ResendOtp(
            modifier = Modifier.padding(start = 16.dp),
            isResendEnabled = isResendEnabled,
            cooldownTime = cooldownTime,
            onResendClick = {
                onResendClick()
            }
        )


        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = {
                onSuccessfulVerification()
            },
            //enabled = isVerifyEnabled,
            modifier = Modifier.width(200.dp),
            shape = ButtonShape,
            //shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary
            )
        ) {
            Text(
                text = "Verify",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onTertiary
            )
        }
    }
}