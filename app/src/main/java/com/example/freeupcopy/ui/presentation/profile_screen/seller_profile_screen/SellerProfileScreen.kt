package com.example.freeupcopy.ui.presentation.profile_screen.seller_profile_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.SwapGoTheme
import com.example.freeupcopy.ui.theme.TextFieldContainerColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerProfileScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
) {
    val lifeCycleOwner = LocalLifecycleOwner.current

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = "@himanshu_dey",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.W500,
                            )
                            //Spacer(modifier = Modifier.size(8.dp))
                            IconButton(
                                onClick = {}
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_copy),
                                    contentDescription = "copy",
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                                )
                            }
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
                                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                                contentDescription = "back"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    )
                )
            }

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Row {
                Icon(
                    modifier = Modifier
                        .size(100.dp)
                        .alpha(0.15f),
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "profile photo",
                )

                Spacer(modifier = Modifier.size(4.dp))

                Column(
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(
                        text = "Himanshu Dey",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "Active 1 hour ago",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.75f),
                    )
                    Spacer(modifier = Modifier.size(2.dp))
                    Row {
                        SellerInfoButton(
                            backgroundColor = Color(0xFFE5FFD8),
                            text = "Ships in 4 days"
                        )

                        Spacer(modifier = Modifier.size(4.dp))

                        SellerInfoButton(
                            backgroundColor = MaterialTheme.colorScheme.secondaryContainer.copy(0.8f),
                            text = "Student"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.size(4.dp))

            Text(
                text = "We sell genuine products at great price. " +
                        "Hope you will love our products and enjoy the experience.",
                fontSize = 15.sp,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.size(10.dp))

            Row {
                Text(
                    text = "18 Followers",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W500,
                )
                Text(
                    text = "  •  ",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "4 Following",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W500,
                )
            }
            Spacer(modifier = Modifier.size(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
                    .clip(ButtonShape)
                    .background(TextFieldContainerColor)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f),
                        shape = ButtonShape
                    )
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.ic_ranks),
                    contentDescription = "ranks",
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "Silver Seller  •  Joined 1 year ago",
                    fontSize = 14.sp,
                )
            }

            Spacer(modifier = Modifier.size(20.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f).height(50.dp),
                    onClick = {},
                    shape = ButtonShape,
                ) {
                    Text(
                        "Edit Profile"
                    )
                }
                OutlinedButton(
                    modifier = Modifier.weight(1f).height(50.dp),
                    onClick = {},
                    shape = ButtonShape,
                ) {
                    Row {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = Icons.Outlined.Share,
                            contentDescription = "share",
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            "Share"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SellerInfoButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    text: String
) {
    Box(
        modifier = modifier
            .clip(ButtonShape)
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f),
                shape = ButtonShape
            )
            .padding(horizontal = 16.dp, vertical = 4.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.W500,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}

@Preview
@Composable
private fun SellerProfileScreenPreview() {
    SwapGoTheme {
        SellerProfileScreen(
            onBack = {}
        )
    }
}