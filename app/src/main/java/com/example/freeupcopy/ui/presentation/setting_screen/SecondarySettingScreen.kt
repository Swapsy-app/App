package com.example.freeupcopy.ui.presentation.setting_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.freeupcopy.domain.enums.Settings
import com.example.freeupcopy.ui.presentation.setting.componants.need_help.NeedHelp
import com.example.freeupcopy.ui.presentation.setting_screen.componants.blocked.BlockedContent
import com.example.freeupcopy.ui.presentation.setting_screen.componants.account.AccountSettings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondarySettingsScreen(
    modifier: Modifier = Modifier,
    screenType: Settings,
    onBack: () -> Unit,
) {
    val lifeCycleOwner = LocalLifecycleOwner.current

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = screenType.valueName,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
//                        if (state.isLoading) {
//                            CircularProgressIndicator(
//                                modifier = Modifier.size(24.dp),
//                                color = MaterialTheme.colorScheme.onPrimaryContainer
//                            )
//                        }
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            val currentState = lifeCycleOwner.lifecycle.currentState
                            if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                onBack()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                contentDescription = "close"
                            )
                        }
                    }
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                )
            }
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
        ) {
            when(screenType) {
                Settings.ACCOUNT_SETTINGS -> {
                    AccountSettings()
                }

                Settings.BLOCKED_CONTENT -> {
                    BlockedContent()
                }

                Settings.ABOUT_US -> {

                }

                Settings.NEED_HELP -> {
                    NeedHelp()
                }
                else -> {

                }
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSetting() {
    SecondarySettingsScreen(
        screenType = Settings.NEED_HELP,
        onBack = {}
    )
}