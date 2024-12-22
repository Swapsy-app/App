package com.example.freeupcopy.ui.presentation.sell_screen.location_screen.add_location_screen


import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.presentation.authentication_screen.componants.OrText
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.CardShape
import com.example.freeupcopy.ui.theme.SwapsyTheme
import com.example.freeupcopy.ui.theme.TextFieldShape
import com.example.freeupcopy.utils.clearFocusOnKeyboardDismiss
import com.google.android.gms.location.LocationServices

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLocationScreen(
    modifier: Modifier = Modifier,
    onLocationAdded: () -> Unit,
    onClose: () -> Unit,
    addLocationViewModel: AddLocationViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val sheetState = rememberModalBottomSheetState()

    val state by addLocationViewModel.state.collectAsState()

    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        addLocationViewModel.onEvent(AddLocationUiEvent.PermissionGranted(isGranted))
        if (isGranted) {
            addLocationViewModel.onEvent(AddLocationUiEvent.MyCurrentLocation(context, fusedLocationClient))
        }
        else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }
    LaunchedEffect(state.error) {
        if (state.error.isNotEmpty()) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        }
    }


    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
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
                    IconButton(onClick = { onClose() }) {
                        Icon(Icons.Rounded.Close, contentDescription = "Close")
                    }
                }
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(NavigationBarDefaults.windowInsets)
                    .defaultMinSize(minHeight = 70.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = 50.dp)
                            .fillMaxWidth()
                            .clip(ButtonShape)
                            .clickable {
                                val validationResult = addLocationViewModel.validateAll()
                                if (validationResult.isValid) {
                                    val currentState = lifecycleOwner.lifecycle.currentState
                                    if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                        val fullAddress =
                                            "${state.flatNo}, ${state.landmark}, ${state.roadName}, ${state.pincode}, ${state.city}, ${state.state}"
                                        addLocationViewModel.onEvent(
                                            AddLocationUiEvent.ChangeAddress(
                                                fullAddress
                                            )
                                        )
                                        addLocationViewModel.onEvent(AddLocationUiEvent.ConfirmSheetOpen)
                                    }
                                } else {
                                    Toast
                                        .makeText(
                                            context,
                                            validationResult.errorMessage.orEmpty(),
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }
                            }
                            .background(MaterialTheme.colorScheme.tertiary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Save Location", color = MaterialTheme.colorScheme.onTertiary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    ) { innerPadding ->

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { locationPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION) },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = Color.Black.copy(alpha = 0.6f)
                ),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
                modifier = Modifier.widthIn(min = 300.dp),
                enabled = state.isPermissionGranted
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_current_location),
                    contentDescription = "add location",
                    tint = MaterialTheme.colorScheme.onPrimary
                )

                Spacer(modifier = Modifier.width(24.dp))

                Text(
                    text = "My Current Location",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.width(24.dp))

                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            if (!state.isPermissionGranted) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.permission_text),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    fontStyle = FontStyle.Italic,
                    fontSize = 14.sp,
                    lineHeight = 16.sp
                )
            }

            OrText(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            )


            LocationTextField(
                value = state.flatNo,
                onValueChange = {
                    addLocationViewModel.onEvent(AddLocationUiEvent.FlatNoChanged(it))
                },
                label = { Text("Flat/House No.") },
                placeholder = "e.g., A-101",
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            )

            Spacer(modifier = Modifier.height(8.dp))

            LocationTextField(
                value = state.landmark,
                onValueChange = {
                    addLocationViewModel.onEvent(AddLocationUiEvent.LandmarkChanged(it))
                },
                label = { Text("Landmark") },
                placeholder = "e.g., Near City Mall",
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            Spacer(modifier = Modifier.height(8.dp))

            LocationTextField(
                value = state.roadName,
                onValueChange = {
                    addLocationViewModel.onEvent(AddLocationUiEvent.RoadNameChanged(it))
                },
                label = { Text("Road Name/Area/Colony") },
                placeholder = "e.g., MG Road",
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier
                    .clearFocusOnKeyboardDismiss()
                    .focusRequester(focusRequester)
                    .fillMaxWidth(),
                value = state.pincode,
                onValueChange = {
                    addLocationViewModel.onEvent(AddLocationUiEvent.PincodeChanged(it))
                },
                label = { Text("Pincode") },
                placeholder = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "e.g., 560001",
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f),
                        fontStyle = FontStyle.Italic,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                maxLines = 1,
                keyboardActions = KeyboardActions(
                    onDone = {
                        addLocationViewModel.onEvent(AddLocationUiEvent.PincodeSubmit(context, state.pincode))
                        focusManager.clearFocus()
                    }
                ),
                shape = TextFieldShape
            )
            Spacer(modifier = Modifier.height(8.dp))

            LocationTextField(
                value = state.state,
                onValueChange = {},
                label = { Text("State") },
                enabled = false
            )
            Spacer(modifier = Modifier.height(8.dp))

            LocationTextField(
                value = state.city,
                onValueChange = {},
                label = { Text("City") },
                enabled = false
            )
        }


    }
    if (state.isConfirmSheetOpen) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                addLocationViewModel.onEvent(AddLocationUiEvent.ConfirmSheetOpen)
            },
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            windowInsets = WindowInsets(0.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(16.dp)
            ) {
                ConfirmLocation(
                    address = state.address,
                    onConfirmClick = {
                        addLocationViewModel.onEvent(AddLocationUiEvent.SaveLocation)
                        onLocationAdded()
                    },
                    onCancelClick = {
                        addLocationViewModel.onEvent(AddLocationUiEvent.ConfirmSheetOpen)
                    }
                )
            }
        }
    }
}

@Composable
fun LocationTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable (() -> Unit)? = null,
    placeholder: String? = null,
    enabled: Boolean = true,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    textStyle: TextStyle = LocalTextStyle.current
) {
    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        label = label,
        placeholder = {
            LocationPlaceholder(text = placeholder ?: "")
        },
        modifier = modifier
            .fillMaxWidth()
            .clearFocusOnKeyboardDismiss(),
        shape = TextFieldShape,
        enabled = enabled,
        colors = colors,
        keyboardOptions = keyboardOptions,
        maxLines = 1,
        singleLine = true,
        keyboardActions = keyboardActions,
        textStyle = textStyle
    )
}


@Composable
fun ConfirmLocation(
    modifier: Modifier = Modifier,
    address: String,
    onConfirmClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.map),
            contentDescription = "Location",
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.CenterHorizontally)
                .alpha(0.7f)
        )
        Spacer(modifier = Modifier.size(36.dp))


        Text(
            text = "Confirm location",
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.size(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, MaterialTheme.colorScheme.primary, CardShape.medium)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = address,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                lineHeight = 16.sp
            )
        }

        Spacer(modifier = Modifier.size(30.dp))

        Text(
            text = "Please check your location before pressing \'confirm\'",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            fontStyle = FontStyle.Italic,
            fontSize = 14.sp,
            lineHeight = 16.sp
        )

        Spacer(modifier = Modifier.size(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.6f),
                onClick = { onCancelClick() },
                shape = ButtonShape,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                //border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.onPrimaryContainer)
            ) {
                Text(
                    text = "Cancel",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.size(10.dp))

            Button(
                onClick = { onConfirmClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = ButtonShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                )
            ) {
                Text(
                    text = "Confirm",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onTertiary
                )
            }
        }

    }
}

@Composable
fun LocationPlaceholder(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f),
        fontStyle = FontStyle.Italic,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Preview(showBackground = true)
@Composable
fun AddLocationScreenPreview() {
    SwapsyTheme {
        ConfirmLocation(
            address = "123, ABC Colony, XYZ Road, 123456, City, State, XYZ Road, 123456, City, State",
            onConfirmClick = {},
            onCancelClick = {}
        )
    }
}
