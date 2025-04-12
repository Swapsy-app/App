package com.example.freeupcopy.ui.presentation.profile_screen.edit_profile_screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.example.freeupcopy.R
import com.example.freeupcopy.domain.enums.Occupation
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.CardShape
import com.example.freeupcopy.ui.theme.SwapGoTheme
import com.example.freeupcopy.ui.theme.TextFieldContainerColor
import com.example.freeupcopy.ui.theme.TextFieldShape

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun EditProfileScreen(
    modifier: Modifier = Modifier,
    onClose: () -> Unit,
    viewModel: EditProfileViewModel = hiltViewModel(),
    profilePhotoUrl: String,
    userFullName: String,
    username: String,
    userBio: String,
    userGender: String,
    userOccupation: String,
) {
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()

    var isSelectAvatarDialogOpen by remember { mutableStateOf(false) }

    val lifeCycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        viewModel.updateProfile(
            profilePhotoUrl = profilePhotoUrl,
            userFullName = userFullName,
            username = username,
            userBio = userBio,
            userGender = userGender,
            userOccupation = userOccupation
        )
    }
    
    LaunchedEffect(state.navigateBack) {
        if (state.navigateBack) {
            onClose()
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(
                                text = "Edit Profile",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.W500,
                            )
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
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    )
                )

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.12f),
                    thickness = 1.dp
                )
            }
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
//                                if (!state.isLoading) {
//                                    val validate = priceViewModel.validateAll()
//                                    if (validate.isValid) {
//
//                                        onConfirmClick()
//                                    } else {
//                                        Toast
//                                            .makeText(
//                                                context,
//                                                validate.errorMessage.orEmpty(),
//                                                Toast.LENGTH_SHORT
//                                            )
//                                            .show()
//                                    }
//                                }
                                viewModel.onEvent(EditProfileUiEvent.ConfirmProfileChangesClicked)
                            }
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Confirm", color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .verticalScroll(state = scrollState)
                .padding(start = 16.dp, end = 16.dp, top = 8.dp)
        ) {
            Box {

                Column(
                    modifier = Modifier
                        .padding(top = 80.dp)
                        .fillMaxWidth()
                        .clip(CardShape.medium)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(Modifier.size(40.dp))

                    Text(
                        text = "Full Name",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.W500,
                    )

                    Spacer(Modifier.size(4.dp))

                    OutlinedTextField(
                        value = state.userFullName,
                        onValueChange = {
                            //onTitleValueChange(it)
                            viewModel.onEvent(EditProfileUiEvent.UserFullNameChanged(it))
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        placeholder = {
                            Text(
                                "Your full name",
                                fontStyle = FontStyle.Italic,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(
                                alpha = 0.4f
                            ),
                            //unfocusedContainerColor = TextFieldContainerColor
                        ),
                        shape = TextFieldShape
                    )

                    Spacer(modifier = Modifier.size(16.dp))

                    Text(
                        text = "Username",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.W500,
                    )

                    Spacer(Modifier.size(4.dp))

                    OutlinedTextField(
                        value = state.username,
                        onValueChange = {
                            viewModel.onEvent(EditProfileUiEvent.UsernameChanged(it))
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        placeholder = {
                            Text(
                                "Enter your custom username",
                                fontStyle = FontStyle.Italic,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
                            )
                        },
                        singleLine = true,
                        maxLines = 1,
                        isError = state.usernameError.isNotEmpty(),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(
                                alpha = 0.4f
                            ),
                            //unfocusedContainerColor = TextFieldContainerColor
                        ),
                        supportingText = {
                            if (state.usernameError.isNotEmpty()) {
                                Text(
                                    text = state.usernameError,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        },
                        shape = TextFieldShape
                    )

                    Spacer(modifier = Modifier.size(16.dp))

                    Text(
                        text = "About me",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.W500,
                    )

                    Spacer(Modifier.size(4.dp))

                    OutlinedTextField(
                        value = state.userBio,
                        minLines = 3,
                        maxLines = 4,
                        onValueChange = {
                            //onDescriptionValueChange(it)
                            viewModel.onEvent(EditProfileUiEvent.UserBioChanged(it))
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        placeholder = {
                            Text(
                                "Write about yourself...",
                                fontStyle = FontStyle.Italic,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
                            )
                        },
                        isError = state.userBioError.isNotEmpty(),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(
                                alpha = 0.4f
                            ),
                            unfocusedContainerColor = TextFieldContainerColor
                        ),
                        supportingText = {
                            if (state.userBioError.isNotEmpty()) {
                                Text(
                                    text = state.userBioError,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        },
                        shape = TextFieldShape
                    )


                }
                Box(
                    modifier = Modifier.align(Alignment.TopCenter)
                ) {
//                    Box(
//                        modifier = Modifier
//                            .size(125.dp)
////                            .shadow(2.dp, CircleShape)
//                            .clip(CircleShape)
//                            .background(Color.Gray)
//                            .align(Alignment.TopCenter)
//                    )
                    if (state.profilePhotoUrl.isNotEmpty()) {
                        SubcomposeAsyncImage(
                            modifier = modifier
                                .size(125.dp)
                                .clip(CircleShape),
                            model = state.profilePhotoUrl,
                            loading = {
                                painterResource(id = R.drawable.im_user)
                            },
                            contentDescription = "profile",
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            modifier = modifier
                                .size(125.dp)
                                .clip(CircleShape),
                            painter = painterResource(id = R.drawable.im_user),
                            contentDescription = "profile",
                            contentScale = ContentScale.Crop
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .clickable {
                                isSelectAvatarDialogOpen = true
                            }
                            .border(3.dp, MaterialTheme.colorScheme.onPrimary, CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                            .align(Alignment.BottomEnd),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = Icons.Rounded.Edit,
                            contentDescription = "edit profile picture",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

            }

            Spacer(modifier = Modifier.size(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(CardShape.medium)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Gender",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.W500,
                )
                Spacer(modifier = Modifier.size(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OptionButton(
                        modifier = Modifier
                            .weight(1f)
                            .height(45.dp),
                        isSelected = state.userGender == "Male",
                        text = "Male",
                        onClick = {
                            viewModel.onEvent(EditProfileUiEvent.UserGenderChanged("Male"))
                        }
                    )
                    OptionButton(
                        modifier = Modifier
                            .weight(1f)
                            .height(45.dp),
                        isSelected = state.userGender == "Female",
                        text = "Female",
                        onClick = {
                            viewModel.onEvent(EditProfileUiEvent.UserGenderChanged("Female"))
                        }
                    )
                    OptionButton(
                        modifier = Modifier
                            .weight(1f)
                            .height(45.dp),
                        isSelected = state.userGender == "Others",
                        text = "Others",
                        onClick = {
                            viewModel.onEvent(EditProfileUiEvent.UserGenderChanged("Others"))
                        }
                    )
                }
                Spacer(modifier = Modifier.size(16.dp))

                Text(
                    text = "Occupation",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.W500,
                )
                Spacer(modifier = Modifier.size(8.dp))

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Occupation.entries.forEach { occupation ->
                        OptionButton(
                            modifier = Modifier,
                            isSelected = state.userOccupation == occupation.displayValue,
                            text = occupation.displayValue,
                            onClick = {
                                viewModel.onEvent(
                                    EditProfileUiEvent.UserOccupationChanged(occupation.displayValue)
                                )
                            }
                        )
                    }
                }
//                    OptionButton(
//                        modifier = Modifier.weight(1f),
//                        isSelected = true,
//                        text = "Male"
//                    )
//                    OptionButton(
//                        modifier = Modifier.weight(1f),
//                        isSelected = false,
//                        text = "Female"
//                    )
//                    OptionButton(
//                        modifier = Modifier.weight(1f),
//                        isSelected = false,
//                        text = "Others"
//                    )

            }
        }

        if(isSelectAvatarDialogOpen) {
            ChooseAvatarsDialog(
//                modifier = Modifier.heightIn(max = 600.dp),
                onConfirmSelect = {
                    isSelectAvatarDialogOpen = false
                },
                onDismiss = {
                    isSelectAvatarDialogOpen = false
                },
                avatars = state.avatars,
                selectedAvatar = state.profilePhotoUrl,
                onSelectedAvatarChange = {
                    viewModel.onEvent(EditProfileUiEvent.UserProfilePhotoChange(it))
                }
            )
        }
    }
}

@Composable
fun OptionButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(ButtonShape)
            .clickable { onClick() }
            .background(if (isSelected) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent)
            .border(
                1.dp,
                if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onPrimaryContainer.copy(
                    alpha = 0.4f
                ),
                ButtonShape
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
fun ChooseAvatarsDialog(
    modifier: Modifier = Modifier,
    onConfirmSelect: () -> Unit,
    onDismiss: () -> Unit,
    onSelectedAvatarChange: (String) -> Unit,
    selectedAvatar: String,
    avatars: List<String>
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(CardShape.medium)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(16.dp)
        ) {
            Text(
                text = "Choose Avatar",
                fontSize = 20.sp,
                fontWeight = FontWeight.W500
            )

            Spacer(modifier = Modifier.size(16.dp))

            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 400.dp)
                    .align(Alignment.CenterHorizontally),
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(avatars) {
                    AsyncImage(
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable {
                                onSelectedAvatarChange(it)
                            }
                            .height(72.dp)
                            .border(
                                width = 4.dp,
                                color = if (selectedAvatar == it) MaterialTheme.colorScheme.primary else Color.Transparent,
                                shape = CircleShape
                            ),
                        model = it,
                        contentDescription = "avatar",
                        contentScale = ContentScale.Crop
                    )
                }
            }

//            FlowRow(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.spacedBy(8.dp),
//                verticalArrangement = Arrangement.spacedBy(12.dp),
//                maxItemsInEachRow = 3
//            ) {
//                repeat(30) {
////                    Box(
////                        modifier = Modifier
////                            .weight(1f),
////                        contentAlignment = Alignment.Center
////                    ) {
////                        Box(
////                            modifier = Modifier
////                                .size(90.dp)
////                                .clip(CircleShape)
////                                .background(Color.Gray)
////                        )
////                    }
//
//                }
//            }

            Spacer(modifier = Modifier.size(30.dp))

            Button(
                onClick = onConfirmSelect,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = ButtonShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                )
            ) {
                Text(
                    text = "Select",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onTertiary
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun EditProfileScreenPreview() {
    SwapGoTheme {
        EditProfileScreen(
            onClose = { },
            profilePhotoUrl = "",
            userFullName = "",
            username = "",
            userBio = "",
            userGender = "",
            userOccupation = ""
        )
    }
}

