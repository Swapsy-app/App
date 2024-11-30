package com.example.freeupcopy.ui.presentation.sell_screen.componants

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.theme.NoteContainerLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManufacturingScreen(
    modifier: Modifier = Modifier,
    onManufacturingClick: (String) -> Unit,
    onClose: () -> Unit,
    manufacturingCountry: String
) {

    val lifeCycleOwner = LocalLifecycleOwner.current

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(text = "Manufacturing Country")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            onClose()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "close"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.size(8.dp))

            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ManufacturingCountryButton(
                    modifier = Modifier.weight(1f),
                    label = "India",
                    isSelected = manufacturingCountry == "India",
                    onClick = {
                        onManufacturingClick("India")
                    }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_india),
                        contentDescription = "India",
                        modifier = Modifier
                            .size(24.dp)
                            .alpha(
                                if (manufacturingCountry == "India") 1f else 0.5f
                            ),
                    )
                }
                ManufacturingCountryButton(
                    modifier = Modifier.weight(1f),
                    label = "Others",
                    isSelected = manufacturingCountry == "Others",
                    onClick = {
                        onManufacturingClick("Others")
                    }
                ) { tint ->
                    Icon(
                        tint = tint,
                        painter = painterResource(id = R.drawable.ic_other_countries),
                        contentDescription = "Others",
                    )
                }
            }

            Spacer(modifier = Modifier.size(16.dp))

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(NoteContainerLight.copy(alpha = 0.75f))
                    .padding(16.dp),
            ) {
                Text(
                    text = "Products are manufactured in various regions worldwide. Please select 'India' for locally manufactured products or 'Others' for products made elsewhere to continue.",
                    fontSize = 13.5.sp,
                    lineHeight = 18.sp,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                )
            }
        }
    }
}


@Composable
fun ManufacturingCountryButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    label: String,
    onClick: (String) -> Unit,
    content: @Composable (Color) -> Unit
) {
    val tint = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3f)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 2.5.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else
                    MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f),
                shape = RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(10.dp))
            .background(
                if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f) else Color.Transparent
            )
            .heightIn(min = 70.dp)
            .clickable { onClick(label) },
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            content(tint)
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = label,
                color = if (isSelected) MaterialTheme.colorScheme.primary else
                    MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f),
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}