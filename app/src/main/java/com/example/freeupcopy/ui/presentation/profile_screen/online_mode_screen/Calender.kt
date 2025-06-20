package com.example.freeupcopy.ui.presentation.profile_screen.online_mode_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.ui.theme.BottomSheetShape
import com.example.freeupcopy.ui.theme.TextFieldShape
import java.text.SimpleDateFormat
import java.util.*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDocked(
    label: String,
    selectedStartDate: Long? = null,
    onDateSelected: (Long?) -> Unit = {},
    initialValue: Long? = null // Add this parameter to control initial value
) {
    var showDatePicker by remember { mutableStateOf(false) }

    // Get current time in local timezone and set to start of day
    val currentTimeMillis = System.currentTimeMillis()
    val calendar = Calendar.getInstance()

    val minSelectableDate = when {
        label.contains("Start", ignoreCase = true) -> {
            // Start date can be today or later - set to start of today in local timezone
            calendar.timeInMillis = currentTimeMillis
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            calendar.timeInMillis
        }
        label.contains("End", ignoreCase = true) && selectedStartDate != null -> {
            // End date must be at least one day after start date
            calendar.timeInMillis = selectedStartDate
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            calendar.timeInMillis
        }
        else -> {
            calendar.timeInMillis = currentTimeMillis
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            calendar.timeInMillis
        }
    }

    // Use initialValue for DatePicker state, but only set it if provided
    val initialDateForPicker = when {
        label.contains("End", ignoreCase = true) -> {
            // For end date, don't set any initial value - let user select
            null
        }
        label.contains("Start", ignoreCase = true) -> {
            // For start date, use today as default
            convertLocalToUtc(minSelectableDate)
        }
        else -> {
            initialValue?.let { convertLocalToUtc(it) }
        }
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDateForPicker
    )

    // Only show selected date if there's actually a selection
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertUtcToLocalDate(it)
    } ?: ""

    // Validation logic with proper timezone conversion
    val isValidDate = remember(datePickerState.selectedDateMillis, minSelectableDate) {
        datePickerState.selectedDateMillis?.let { selectedMillis ->
            val localSelectedMillis = convertUtcToLocal(selectedMillis)
            localSelectedMillis >= minSelectableDate
        } ?: false
    }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { },
            label = { Text(label) },
            readOnly = true,
            placeholder = {
                // Show placeholder text when no date is selected
                Text(
                    text = when {
                        label.contains("Start", ignoreCase = true) -> "Select start date"
                        label.contains("End", ignoreCase = true) -> "Select end date"
                        else -> "Select date"
                    },
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            },
            trailingIcon = {
                IconButton(onClick = { showDatePicker = !showDatePicker }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                focusedLabelColor = MaterialTheme.colorScheme.primary
            ),
            shape = TextFieldShape,
            modifier = Modifier.fillMaxWidth(),
            supportingText = {
                when {
                    label.contains("Start", ignoreCase = true) -> {
                        Text(
                            text = "Select today or a future date",
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            fontSize = 12.sp
                        )
                    }
                    label.contains("End", ignoreCase = true) -> {
                        Text(
                            text = if (selectedStartDate != null) {
                                "Must be after ${convertLocalToDisplayDate(selectedStartDate)}"
                            } else {
                                "Please select start date first"
                            },
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            fontSize = 12.sp
                        )
                    }
                }
            }
        )

        if (showDatePicker) {
            val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

            ModalBottomSheet(
                onDismissRequest = { showDatePicker = false },
                sheetState = sheetState,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                shape = BottomSheetShape,
                dragHandle = null
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Select $label",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = when {
                            label.contains("Start", ignoreCase = true) ->
                                "Choose today (${convertLocalToDisplayDate(currentTimeMillis)}) or any future date"
                            label.contains("End", ignoreCase = true) && selectedStartDate != null ->
                                "End date must be after ${convertLocalToDisplayDate(selectedStartDate)}"
                            label.contains("End", ignoreCase = true) && selectedStartDate == null ->
                                "Please select start date first"
                            else -> "Select a valid date"
                        },
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    DatePicker(
                        state = datePickerState,
                        showModeToggle = false,
                        colors = DatePickerDefaults.colors(
                            selectedDayContainerColor = MaterialTheme.colorScheme.primary,
                            todayDateBorderColor = MaterialTheme.colorScheme.primary,
                            containerColor = Color(0xFFE4F4FD)
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (datePickerState.selectedDateMillis != null && !isValidDate) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                        ) {
                            Text(
                                text = when {
                                    label.contains("Start", ignoreCase = true) ->
                                        "Start date cannot be in the past"
                                    label.contains("End", ignoreCase = true) ->
                                        "End date must be after start date"
                                    else -> "Invalid date selected"
                                },
                                color = MaterialTheme.colorScheme.onErrorContainer,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            onClick = { showDatePicker = false }
                        ) {
                            Text("Cancel")
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = {
                                if (isValidDate) {
                                    // Convert UTC back to local time for callback
                                    val localTimeMillis = datePickerState.selectedDateMillis?.let {
                                        convertUtcToLocal(it)
                                    }
                                    onDateSelected(localTimeMillis)
                                    showDatePicker = false
                                }
                            },
                            enabled = isValidDate,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.tertiary,
                                contentColor = MaterialTheme.colorScheme.onTertiary
                            )
                        ) {
                            Text("OK")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}


// Convert UTC time from DatePicker to local time for display
fun convertUtcToLocalDate(utcMillis: Long): String {
    val utcCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    utcCalendar.timeInMillis = utcMillis

    val localCalendar = Calendar.getInstance()
    localCalendar.set(
        utcCalendar.get(Calendar.YEAR),
        utcCalendar.get(Calendar.MONTH),
        utcCalendar.get(Calendar.DAY_OF_MONTH),
        0, 0, 0
    )
    localCalendar.set(Calendar.MILLISECOND, 0)

    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(localCalendar.time)
}

// Convert local time to UTC for DatePicker
fun convertLocalToUtc(localMillis: Long): Long {
    val localCalendar = Calendar.getInstance()
    localCalendar.timeInMillis = localMillis

    val utcCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    utcCalendar.set(
        localCalendar.get(Calendar.YEAR),
        localCalendar.get(Calendar.MONTH),
        localCalendar.get(Calendar.DAY_OF_MONTH),
        0, 0, 0
    )
    utcCalendar.set(Calendar.MILLISECOND, 0)

    return utcCalendar.timeInMillis
}

// Convert UTC time from DatePicker back to local time
fun convertUtcToLocal(utcMillis: Long): Long {
    val utcCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    utcCalendar.timeInMillis = utcMillis

    val localCalendar = Calendar.getInstance()
    localCalendar.set(
        utcCalendar.get(Calendar.YEAR),
        utcCalendar.get(Calendar.MONTH),
        utcCalendar.get(Calendar.DAY_OF_MONTH),
        0, 0, 0
    )
    localCalendar.set(Calendar.MILLISECOND, 0)

    return localCalendar.timeInMillis
}

// Convert local time to display format
fun convertLocalToDisplayDate(localMillis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(localMillis))
}

// Keep backward compatibility
fun convertMillisToDate(millis: Long): String {
    return convertUtcToLocalDate(millis)
}
