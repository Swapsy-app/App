package com.example.freeupcopy.ui.presentation.authentication_screen.otp_screen

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.freeupcopy.ui.presentation.authentication_screen.otp_screen.componants.OtpSection
import com.example.freeupcopy.ui.presentation.authentication_screen.otp_screen.componants.PageDescriptionOtp
import com.example.freeupcopy.ui.theme.SwapGoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpVerificationScreen(
    email: String,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onSuccessfulVerification: () -> Unit,
    otpViewModel: OtpViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val state by otpViewModel.state.collectAsState()

    LaunchedEffect(state.error) {
        state.error.takeIf { it.isNotBlank() }?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(state.signUpOtpVerifyResponse) {
        if (state.signUpOtpVerifyResponse != null) {
            Toast.makeText(context, state.signUpOtpVerifyResponse!!.message, Toast.LENGTH_SHORT).show()
            onSuccessfulVerification()
        }
    }

    LaunchedEffect(
        state.isSuccessfulOtpResend
    ) {
        if (state.isSuccessfulOtpResend) {
            Toast.makeText(context, "OTP sent successfully.", Toast.LENGTH_SHORT).show()
        }

    }


    // Handle the timer for resend button
    LaunchedEffect(state.cooldownTime) {
        if (state.cooldownTime > 0) {
            kotlinx.coroutines.delay(1000L) // Delay for 1 second
            otpViewModel.onEvent(OtpUiEvent.CooldownCount)
        } else {
            otpViewModel.onEvent(OtpUiEvent.ResendChange(true))
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets(0.dp)),
        topBar = {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                ),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            val currentState = lifecycleOwner.lifecycle.currentState
                            if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                onBackClick()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                            contentDescription = "back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
        ) {
            val color = MaterialTheme.colorScheme.secondary
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    color = color.copy(alpha = 0.2f),
                    radius = 700f,
                    center = Offset(0f, -100f)
                )
                drawCircle(
                    color = color.copy(alpha = 0.2f),
                    radius = 1000f,
                    center = Offset(0f, -100f)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = paddingValues.calculateTopPadding()),
            ) {
                PageDescriptionOtp()

                OtpSection(
                    email = email,
                    otpValues = state.otpValues, // List of otp values
                    //isVerifyEnabled = state.isVerifyEnabled, // Enable the verify button
                    isResendEnabled = state.isResendEnabled, // Enable the resend button
                    cooldownTime = state.cooldownTime, // Cooldown time for resend button
                    onOtpInputComplete = { isOtpValid ->
                        otpViewModel.onEvent(OtpUiEvent.VerifyChange(isOtpValid))
                    },
                    onUpdateOtpValuesByIndex = { index, value ->
                        otpViewModel.onEvent(OtpUiEvent.OtpChange(index, value))
                    },
                    onResendClick = {
                        otpViewModel.onEvent(OtpUiEvent.ResendOtp(email))
                    },
                    onSuccessfulVerification = {
                        val currentState = lifecycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            //onSuccessfulVerification()
                            otpViewModel.onEvent(OtpUiEvent.VerifyOtp(email))
                        }
                    }
                )
            }
        }
    }
}


@Preview
@Composable
fun OtpVerificationScreenPreview() {
    SwapGoTheme {
        OtpVerificationScreen(
            onBackClick = {},
            onSuccessfulVerification = {},
            email = "sksahilislam0123@gmail.com"
        )
    }
}