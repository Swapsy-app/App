package com.example.freeupcopy.ui.presentation.profile_screen.seller_profile_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.presentation.profile_screen.posted_products_screen.components.ActionsButton
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.TextFieldContainerColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerProfileActionsBottomSheet(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onReport: () -> Unit,
    onBlock: () -> Unit
) {
    if (isVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            windowInsets = WindowInsets(0.dp),
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .navigationBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ActionsButton(
                    action = "Report User",
                    onClick = onReport,

                )

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                        .background(MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.10f))
                )

                ActionsButton(
                    action = "Block User",
                    onClick = onBlock,
                    icon = {
                        Icon(
                            modifier = Modifier.alpha(0.5f).size(22.dp),
                            painter = painterResource(R.drawable.ic_block),
                            contentDescription = "Block user",
                        )
                    }
                )
                ActionsButton(
                    action = "Cancel",
                    onClick = onDismiss,
                    backgroundColor = TextFieldContainerColor
                )
            }
        }
    }
}

//@Composable
//fun ActionsButton(
//    modifier: Modifier = Modifier,
//    action: String,
//    onClick: () -> Unit,
//    textColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
//    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
//    icon: @Composable (() -> Unit)? = null
//) {
//    Row(
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp)
//            .clip(ButtonShape)
//            .clickable { onClick() }
//            .background(backgroundColor)
//            .padding(vertical = 16.dp),
//        horizontalArrangement = Arrangement.Center,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        icon?.let {
//            it()
//            Spacer(modifier = Modifier.size(8.dp))
//        }
//        Text(
//            text = action,
//            fontWeight = FontWeight.W500,
//            color = textColor
//        )
//    }
//}