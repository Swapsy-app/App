package com.example.freeupcopy.ui.presentation.profile_screen.componants

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.theme.LinkColor
import com.example.freeupcopy.utils.dashedLine

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar(
    modifier: Modifier = Modifier,
    profilePhotoUrl: String,
    userName: String,
    userRating: String,
    onSettingsClick: () -> Unit,
    onViewProfileClick: () -> Unit
) {
    Column(modifier = modifier) {
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier.size(48.dp),
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "profile photo"
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = userName,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            lineHeight = 18.sp
                        )
                        Spacer(modifier = Modifier.size(3.dp))
                        Text(
                            modifier = Modifier
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                    onClick = onViewProfileClick
                                )
                                .dashedLine(
                                    color = LinkColor,
                                    dashWidth = 4.dp,
                                    dashGap = 3.dp,
                                    strokeWidth = 1.dp,
                                    verticalOffset = (-2).sp
                                ),
                            text = "View Profile",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal,
                            lineHeight = 16.sp,
                            color = LinkColor
                        )
                    }
                    Spacer(modifier = Modifier.size(12.dp))

                    Column(
                        modifier = Modifier
                            .padding(top = 0.dp)
                            .align(Alignment.Top)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(18.dp),
                                painter = painterResource(R.drawable.ic_star),
                                contentDescription = "rating",
                                tint = Color.Unspecified
                            )
                            Spacer(modifier = Modifier.size(4.dp))
                            Text(
                                text = userRating,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            },
            actions = {
                IconButton(
                    onClick = onSettingsClick
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = "settings",
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer
            )
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
        )
    }
}