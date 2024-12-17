package com.example.freeupcopy.ui.presentation.sell_screen.componants

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.freeupcopy.domain.model.Condition
import com.example.freeupcopy.ui.presentation.sell_screen.SellUiEvent
import com.example.freeupcopy.ui.presentation.sell_screen.SellViewModel
import com.example.freeupcopy.ui.presentation.sell_screen.weight_screen.NoteSection
import com.example.freeupcopy.ui.theme.SwapsyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConditionScreen(
    modifier: Modifier = Modifier,
    onConditionClick: () -> Unit,
    onClose: () -> Unit,
    selectedCondition: String,
    sellViewModel: SellViewModel
) {

    val lifeCycleOwner = LocalLifecycleOwner.current
    val conditionList = listOf(
        Condition(
            tag = "New with Price Tag",
            description = "Completely unused, original price tag still attached, perfect condition"
        ),
        Condition(
            tag = "Almost New",
            description = "Without price tag, never used or used a few times, minimal signs of wear, looks nearly new"
        ),
        Condition(
            tag = "Nice",
            description = "Gently used, well-maintained, great for continued use"
        ),
        Condition(
            tag = "Used",
            description = "Clearly shows wear and tear with flaws"
        ),
    )

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
                        Text(text = "Condition")
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
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Spacer(modifier = Modifier.size(8.dp))

            NoteSection(
                text = "Please choose the option that best describes the current state of the product you are selling"
            )
            Spacer(modifier = Modifier.size(4.dp))

            conditionList.forEach { condition ->
                ConditionItem(
                    onClick = {
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            sellViewModel.onEvent(SellUiEvent.ConditionChange(condition.tag))
                            onConditionClick()
                        }
                    },
                    tag = condition.tag,
                    description = condition.description,
                    isSelected = selectedCondition == condition.tag
                )
            }
//            Row(
//                modifier = modifier
//                    .fillMaxWidth()
//                    .clip(RoundedCornerShape(10.dp))
//                    .background(NoteContainerLight.copy(alpha = 0.75f))
//                    .padding(16.dp),
//            ) {
//                Text(
//                    text = "Products are manufactured in various regions worldwide. Please select 'India' for locally manufactured products or 'Others' for products made elsewhere to continue.",
//                    fontSize = 13.5.sp,
//                    lineHeight = 18.sp,
//                    fontStyle = FontStyle.Italic,
//                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
//                )
//            }
        }
    }
}

@Composable
fun ConditionItem(
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit,
    tag: String,
    description: String,
    isSelected: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick(tag) }
            .border(
                width = 2.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else
                    MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f),
                shape = RoundedCornerShape(16.dp)
            )
            .background(if (isSelected) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent)
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = tag)
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            fontSize = 13.5.sp,
            lineHeight = 16.sp,
            text = description,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewConditionScreen() {
    SwapsyTheme {
        ConditionScreen(
            onConditionClick = {},
            onClose = {},
            selectedCondition = "New with Price Tag",
            sellViewModel = SellViewModel()
        )
    }
}