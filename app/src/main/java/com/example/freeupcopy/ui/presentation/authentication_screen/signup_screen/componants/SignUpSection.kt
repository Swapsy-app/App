package com.example.freeupcopy.ui.presentation.authentication_screen.signup_screen.componants

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.presentation.authentication_screen.componants.GoogleButton
import com.example.freeupcopy.ui.presentation.authentication_screen.componants.OrText
import com.example.freeupcopy.ui.presentation.authentication_screen.signup_screen.SignUpUiEvent
import com.example.freeupcopy.ui.presentation.authentication_screen.signup_screen.SignUpUiState
import com.example.freeupcopy.ui.viewmodel.SignUpViewModel

@Composable
fun SignUpSection(
    modifier: Modifier = Modifier,
    state: SignUpUiState,
    onLoginClick: () -> Unit,
    onSuccessfulSignUp: () -> Unit,
    signUpViewModel: SignUpViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf(state.name) }
    var mobile by remember { mutableStateOf(state.mobile) }
    var email by remember { mutableStateOf(state.email) }
    var password by remember { mutableStateOf(state.password) }
    var confirmPassword by remember { mutableStateOf(state.confirmPassword) }
    val scope = rememberCoroutineScope()
    val isButtonEnabled = remember { mutableStateOf(true) }
    val error by remember { mutableStateOf(state.error) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .padding(NavigationBarDefaults.windowInsets.asPaddingValues()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = name,
            singleLine = true,
            onValueChange = {
                name = it
                if (it.isEmpty()) isButtonEnabled.value = false
                signUpViewModel.onEvent(SignUpUiEvent.NameChange(it))
            },
            label = { Text(text = "Name") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "name"
                )
            },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.size(8.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            singleLine = true,
            onValueChange = {
                email = it
                if (it.isEmpty()) isButtonEnabled.value = false
                signUpViewModel.onEvent(SignUpUiEvent.EmailChange(it))
            },
            label = { Text(text = "Email") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Email,
                    contentDescription = "email"
                )
            },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.size(8.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mobile,
            singleLine = true,
            onValueChange = {
                mobile = it
                if (it.isEmpty()) isButtonEnabled.value = false
                signUpViewModel.onEvent(SignUpUiEvent.MobileChange(it))
            },
            label = { Text(text = "Mobile No.") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Phone,
                    contentDescription = "mobile"
                )
            },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.size(8.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = {
                password = it
                signUpViewModel.onEvent(SignUpUiEvent.PasswordChange(it))
            },
            label = { Text(text = "Password") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = password
                )
            },
            maxLines = 1,
            singleLine = true,
            trailingIcon = {
                val image = if (passwordVisible) R.drawable.ic_password_visibility
                else R.drawable.ic_password_visibility_off

                val description = if (passwordVisible) "Hide password"
                else "show password"
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(painter = painterResource(id = image), contentDescription = description)
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.size(8.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                signUpViewModel.onEvent(SignUpUiEvent.ConfirmPasswordChange(it))
            },
            label = { Text(text = "Confirm password") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = "confirm password"
                )
            },
            maxLines = 1,
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape = RoundedCornerShape(12.dp),

            )

        Spacer(modifier = Modifier.size(24.dp))

        Button(
            onClick = {
                if(!state.isLoading) {
                    val validate = signUpViewModel.validateAll()
                    if (validate.isValid) {
                        onSuccessfulSignUp()
                        Toast.makeText(context, "Sign up successful", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            context,
                            validate.errorMessage.orEmpty(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            },
            modifier = Modifier.width(200.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.8f),
                contentColor = MaterialTheme.colorScheme.onTertiary
            )
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(
                    text = "Sign Up",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onTertiary
                )
            }
        }

        Spacer(modifier = Modifier.size(4.dp))

        LoginText(
            onLoginClick = { onLoginClick() }
        )

        OrText(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)
        )

//        state.value?.let {
//            when (it) {
//                is Resource.Error -> {
//                    LaunchedEffect(state.value is Resource.Error) {
//                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
//                        isButtonEnabled.value = true
//                    }
//                }
//
//                is Resource.Loading -> {}
//                is Resource.Success -> {
//                    LaunchedEffect(Unit) {
//                        Toast.makeText(context, "Check mail to complete sign up", Toast.LENGTH_LONG)
//                            .show()
//                        navController.navigate(Screens.SignInScreen.route) {
//                            popUpTo(Screens.SignUpScreen.route) {
//                                inclusive = true
//                            }
//                        }
//                    }
//                }
//            }
//        }

        GoogleButton(
            modifier = Modifier.padding(horizontal = 30.dp)
        )
    }
}
