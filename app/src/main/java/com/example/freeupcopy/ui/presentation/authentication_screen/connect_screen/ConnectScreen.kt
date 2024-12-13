package com.example.freeupcopy.ui.presentation.authentication_screen.connect_screen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.presentation.authentication_screen.componants.GoogleButton
import com.example.freeupcopy.ui.presentation.authentication_screen.componants.OrText
import com.example.freeupcopy.ui.presentation.authentication_screen.connect_screen.componants.DescriptionPart
import com.example.freeupcopy.ui.theme.Lobster
import com.example.freeupcopy.ui.theme.SwapsyTheme
import com.example.freeupcopy.utils.clearFocusOnKeyboardDismiss
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ConnectScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    connectViewModel: ConnectViewModel = hiltViewModel()
) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    val state by connectViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        connectViewModel.onEvent(ConnectUiEvent.TransitionChange(true))
        connectViewModel.smallCircleRadius.animateTo(
            targetValue = 700f,
            animationSpec = tween(
                durationMillis = 500
            )
        )
        connectViewModel.bigCircleRadius.animateTo(
            targetValue = 1000f,
            animationSpec = tween(
                durationMillis = 700
            )
        )
    }

    Scaffold(
        modifier = modifier
            .clearFocusOnKeyboardDismiss()
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets(0.dp)),
        topBar = {
            TopAppBar(
                title = {
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            onBackClick()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                            contentDescription = "back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
        ) {
            val color = MaterialTheme.colorScheme.secondary
            val color1 = MaterialTheme.colorScheme.tertiary

            Canvas(modifier = Modifier.fillMaxSize()) {
                val width = size.width
                val height = size.height

                drawCircle(
                    color = color.copy(alpha = 0.2f),
                    radius = connectViewModel.smallCircleRadius.value,
                    center = Offset(0f, -100f)
                )
                drawCircle(
                    color = color.copy(alpha = 0.2f),
                    radius = connectViewModel.bigCircleRadius.value,
                    center = Offset(0f, -100f)
                )
                drawCircle(
                    color = color.copy(alpha = 0.2f),
                    radius = 500f,
                    center = Offset(x = width, y = height * 0.8f)
                )
                drawCircle(
                    color = color1.copy(alpha = 0.2f),
                    radius = 50f,
                    center = Offset(x = width * 0.75f, y = height * 0.55f)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
            ) {
                AnimatedVisibility(
                    visible = state.transitionAnim,
                    enter = fadeIn(
                        animationSpec = tween(
                            durationMillis = 1000,
                            delayMillis = 500
                        )
                    ) +
                            slideInVertically(
                                animationSpec = tween(
                                    durationMillis = 1000,
                                    delayMillis = 500
                                )
                            ),
                    modifier = Modifier
                        .padding(it)
                        .padding(top = 30.dp)
                        .align(Alignment.CenterHorizontally),
                ) {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontFamily = Lobster,
                        fontSize = 50.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Column {
                    DescriptionPart()

                    ConnectSection(
                        onLoginClick = {
                            val currentState = lifeCycleOwner.lifecycle.currentState
                            if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                onLoginClick()
                            }
                        },
                        onSignUpClick = {
                            val currentState = lifeCycleOwner.lifecycle.currentState
                            if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                onSignUpClick()
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ConnectSection(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .padding(NavigationBarDefaults.windowInsets.asPaddingValues()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(10.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(
                modifier = Modifier
                    .widthIn(min = 140.dp)
                    .heightIn(min = 45.dp),
                onClick = {
                    onSignUpClick()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary
                )
            ) {
                Text(
                    text = "Sign Up",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.size(16.dp))

            OutlinedButton(
                modifier = Modifier
                    .widthIn(min = 140.dp)
                    .heightIn(min = 45.dp),
                onClick = {
                    onLoginClick()
                },
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimary),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                )
            ) {
                Text(
                    text = "Login",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }

        OrText(
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.77f),
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)
        )

        GoogleButton(
            modifier = Modifier.padding(horizontal = 30.dp),
            shape = CircleShape
        )
    }
}


@Preview
@Composable
fun ConnectScreenPreview() {
    SwapsyTheme {

        ConnectScreen(
            onBackClick = {},
            onLoginClick = {},
            onSignUpClick = {}
        )
    }
}
