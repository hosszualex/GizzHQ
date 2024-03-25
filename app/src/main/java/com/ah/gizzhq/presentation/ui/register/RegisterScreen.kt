package com.ah.gizzhq.presentation.ui.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ah.gizzhq.presentation.theme.GizzHQTheme
import com.ah.gizzhq.presentation.ui.EmailTextField
import com.ah.gizzhq.presentation.ui.ErrorText
import com.ah.gizzhq.presentation.ui.PasswordTextField
import com.ah.gizzhq.presentation.ui.ProgressIndicator

@Composable
fun RegisterRoute(viewModel: RegisterViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    RegisterScreen(
        viewModel.email,
        viewModel.password,
        viewModel.retypedPassword,
        uiState,
        viewModel::onEvent,
    )
}

@Composable
internal fun RegisterScreen(
    email: String,
    password: String,
    retypedPassword: String,
    uiState: RegisterUiState,
    onEvent: (RegisterUiEvent) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    // call the viewModel variable and call the next screen
    // should be in UI state and call the on navigate to profile
    val emailError by remember(uiState.isEmailValid) {
        mutableStateOf(
            if (uiState.isEmailValid) "" else "Email does not have the correct format",
        )
    }
    val passwordError by remember(uiState.isPasswordValid) {
        mutableStateOf(
            if (uiState.isPasswordValid) "" else "Password should have: \n- at least 8 characters\n- a lower case letter\n- an upper case letter\n - a number",
        )
    }

    val retypedPasswordError by remember(uiState.isRetypedPasswordValid) {
        mutableStateOf(
            if (uiState.isRetypedPasswordValid) "" else "Passwords are not matching",
        )
    }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        EmailTextField(
            label = "Email",
            valueEmail = email,
            onValueChange = { email ->
                onEvent(RegisterUiEvent.EmailChanged(email))
            },
            isEmailValid = uiState.isEmailValid,
        )
        ErrorText(text = emailError)

        Spacer(modifier = Modifier.height(16.dp))
        PasswordTextField(
            label = "Password",
            valuePassword = password,
            onValueChange = { password -> onEvent(RegisterUiEvent.PasswordChanged(password)) },
            isPasswordValid = uiState.isPasswordValid,
        )
        ErrorText(text = passwordError)

        Spacer(modifier = Modifier.height(16.dp))
        PasswordTextField(
            label = "Retyped Password",
            valuePassword = retypedPassword,
            onValueChange = { retypedPassword ->
                onEvent(
                    RegisterUiEvent.RetypedPasswordChanged(
                        retypedPassword,
                    ),
                )
            },
            isPasswordValid = uiState.isRetypedPasswordValid,
        )

        ErrorText(text = retypedPasswordError)
        Spacer(modifier = Modifier.height(36.dp))
        Button(
            onClick = {
                keyboardController?.hide()
                onEvent(RegisterUiEvent.Register(email, password))
            },
            enabled = uiState.isRegisterButtonEnabled,
        ) {
            Text(text = "Register Account")
        }
    }

    AnimatedVisibility(
        visible = uiState.isLoading,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        ProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    GizzHQTheme {
        RegisterScreen(
            email = "cassie.emerson@example.com",
            password = "sagittis",
            retypedPassword = "ornare",
            uiState = RegisterUiState(),
            onEvent = {},
        )
    }
}
