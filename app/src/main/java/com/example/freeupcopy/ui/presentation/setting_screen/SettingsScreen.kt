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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.freeupcopy.R
import com.example.freeupcopy.domain.enums.Settings
import com.example.freeupcopy.ui.presentation.product_screen.ConfirmDialogBox
import com.example.freeupcopy.ui.presentation.product_screen.ProductUiEvent
import com.example.freeupcopy.ui.presentation.profile_screen.posted_products_screen.components.ConfirmDialog
import com.example.freeupcopy.ui.presentation.setting_screen.SettingsUiEvent
import com.example.freeupcopy.ui.presentation.setting_screen.SettingsViewModel
import com.example.freeupcopy.ui.presentation.setting_screen.componants.StarterSetting
import com.example.freeupcopy.ui.theme.PrimaryLight
import com.example.freeupcopy.ui.theme.TertiaryLight

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    token: String?,
    onShowLoginBottomSheet: () -> Unit,
    onNavigate: (Settings) -> Unit,
    onRedirectToHome: () -> Unit,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
) {
    val state by settingsViewModel.state.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    var isConfirmLogOut by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(state.onSuccessfulLogOut) {
        if (state.onSuccessfulLogOut) {
            onRedirectToHome()
        }
    }

    Scaffold(
        modifier = Modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .background(PrimaryLight)
                    .padding(16.dp)
                    .statusBarsPadding()
                    .padding(vertical = 50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column {
                    Image(
                        painter = painterResource(R.drawable.ic_logo_full),
                        contentDescription = "cash on delivery",
                        modifier = Modifier.width(250.dp)
                    )
                    Spacer(Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.End
                    ) {
                        Spacer(Modifier.width(130.dp))
                        Text(
                            "Version 1.00.00", fontSize = 13.sp, fontWeight = FontWeight.Bold,
                            color = TertiaryLight
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
        ) {
            StarterSetting(
                token = token,
                onShowLoginBottomSheet = onShowLoginBottomSheet,
                onNavigate = {
                    val currentState = lifecycleOwner.lifecycle.currentState
                    if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                        onNavigate(it)
                    }
                },
                onLogOutClick = {
                    isConfirmLogOut = true
                }
            )
        }
    }

    if (isConfirmLogOut) {
        ConfirmDialog(
            dialogText = "Are you sure you want to log out?",
            onConfirm = {
                isConfirmLogOut = false
                settingsViewModel.onEvent(SettingsUiEvent.LogOut)
            },
            onCancel = {
                isConfirmLogOut = false
            },
            dialogTitle = "Confirm Log Out",
            confirmButtonText = "Log Out",
            cancelButtonText = "Cancel",
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSetting() {
    SettingsScreen(
        token = null,
        onNavigate = {},
        onRedirectToHome = {},
        onShowLoginBottomSheet = {},
    )
}