package com.example.freeupcopy.ui.presentation.reply_screen

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.freeupcopy.data.remote.dto.product.Reply
import com.example.freeupcopy.ui.presentation.product_screen.componants.MentionSuggestionItem
import com.example.freeupcopy.ui.presentation.reply_screen.components.CommentRow
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.TextFieldShape
import com.example.freeupcopy.utils.clearFocusOnKeyboardDismiss

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReplyScreen(
    modifier: Modifier = Modifier,
    onReplyPosted:(Reply) -> Unit,
    onClose: () -> Unit,
    replyViewModel: ReplyViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val state by replyViewModel.state.collectAsState()

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val showMentionSuggestions by remember(state.isMentioning, state.mentionResults) {
        mutableStateOf(state.isMentioning && state.mentionResults.isNotEmpty())
    }

    LaunchedEffect(state.error) {
        if (state.error.isNotEmpty()) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
            Log.e("ReplyScreen", "Error: ${state.error}")
        }
    }

    LaunchedEffect(state.onSuccessFullAdd) {
        if (state.onSuccessFullAdd) {
            state.successfulReply?.let { onReplyPosted(it) }
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .clearFocusOnKeyboardDismiss(),
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
    ) { innerPadding ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "Replying to ...",
                color = Color.Gray,
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic
            )
            Spacer(modifier = Modifier.height(6.dp))

            state.commentUser?.let {
                CommentRow(
                    commentUser = it,
                    text = state.textReplying
                )
            }

            Spacer(modifier = Modifier.height(16.dp))




            AnimatedVisibility(
                visible = showMentionSuggestions,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Surface(
                    modifier = Modifier.padding(bottom = 12.dp),
                    color = Color.Transparent
                ) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(state.mentionResults) {
                            MentionSuggestionItem(
                                user = it,
                                onClick = {
                                    replyViewModel.onEvent(ReplyUiEvent.SelectMention(it))
                                }
                            )
                        }
                    }
                }
            }




            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 70.dp),
                verticalArrangement = Arrangement.Center
            ) {

                Row(Modifier.height(IntrinsicSize.Min)) {
                    ReplyTextField(
                        value = state.reply,
                        onValueChange = { newValue ->
                            replyViewModel.onEvent(ReplyUiEvent.ReplyChanged(newValue))
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

                    Box(
                        modifier = Modifier
                            .padding(top = 4.dp, bottom = 4.dp, end = 8.dp)
                            .clip(ButtonShape)
                            .fillMaxHeight()
                            .aspectRatio(1f)
                            .background(Color.Black)
                            .clickable {
                                replyViewModel.onEvent(ReplyUiEvent.PostReply)
                            },
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
    }
}

@Composable
fun ReplyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors()
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(
                    alpha = 0.4f
                ),
                fontStyle = FontStyle.Italic,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        modifier = modifier,
        keyboardActions = keyboardActions,
        shape = TextFieldShape,
        keyboardOptions = keyboardOptions,
        colors = colors,
        singleLine = true,
        maxLines = 1
    )
}