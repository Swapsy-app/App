package com.example.freeupcopy.ui.presentation.reply_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.freeupcopy.ui.presentation.product_screen.componants.ReplyTextField
import com.example.freeupcopy.ui.presentation.reply_screen.components.CommentRow
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.SwapGoTheme
import com.example.freeupcopy.utils.clearFocusOnKeyboardDismiss

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReplyScreen(
    modifier: Modifier = Modifier,
    onClose: () -> Unit,
    replyViewModel: ReplyViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val state by replyViewModel.state.collectAsState()

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

//    var input by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .clearFocusOnKeyboardDismiss()
            .imePadding(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                val currentState = lifecycleOwner.lifecycle.currentState
                                if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                    onClose()
                                }
                            }
                        ) {
                            Icon(Icons.Rounded.Close, contentDescription = "Close")
                        }
                    },
                    actions = {
                        TextButton(
                            modifier = Modifier
                                .padding(end = 8.dp),
                            onClick = {}
                        ) {
                            Text(
                                text = "POST",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                    }
                )

                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                )

            }
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(NavigationBarDefaults.windowInsets)
                    .defaultMinSize(minHeight = 70.dp),
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = "@${state.comment?.username}",
                )

                Row(Modifier.height(IntrinsicSize.Min)) {
                    ReplyTextField(
                        value = state.reply,
                        onValueChange = {
                            replyViewModel.onEvent(ReplyUiEvent.ReplyChanged(it))
                        },
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .weight(1f)
                            .focusRequester(focusRequester),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                        keyboardActions = KeyboardActions(
                            onSend = {

                                focusManager.clearFocus()
                            }
                        ),
                        placeholder = "Add a reply...",
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        )
                    )

                    Box (
                        modifier = Modifier
                            .padding(top = 4.dp, bottom = 4.dp, end = 8.dp)
                            .clip(ButtonShape)
                            .fillMaxHeight().aspectRatio(1f)
                            .background(Color.Black),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Send,
                            contentDescription = "Share",
                            tint = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                }

            }
        }
    ) { innerPadding ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = "Replying to ...",
                color = Color.Gray,
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic
            )
            Spacer(modifier = Modifier.height(6.dp))

            // state.comment is nullable its fine but it should not take nullable value
            // composable so change it in the future

            CommentRow(
                comment = state.comment!!
            )
        }
    }
}


@Preview
@Composable
fun PreviewReplyScreen() {
    SwapGoTheme {

        ReplyScreen(
            onClose = {}
        )
    }
}