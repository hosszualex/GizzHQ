package com.ah.gizzhq.presentation.ui.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ah.gizzhq.presentation.theme.GizzHQTheme

@Composable
fun RegisterRoute(
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    RegisterScreen(
        uiState,
        viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen (
    uiState: RegisterUiState,
    onEvent: (RegisterUiEvent) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val emailError by remember(uiState.isEmailValid) {
        mutableStateOf(
            if (uiState.isEmailValid) "" else "Email should contain min 5 characters"
        )
    }
    val passwordError by remember(uiState.isPasswordValid) {
        mutableStateOf(
            if (uiState.isPasswordValid) "" else "Password should contain min 5 characters"
        )
    }

    val retypedPasswordError by remember(uiState.isRetypedPasswordValid) {
        mutableStateOf(
            if (uiState.isRetypedPasswordValid) "" else "Passwords should match"
        )
    }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = "email",
            onValueChange = { email ->
                onEvent(RegisterUiEvent.EmailChanged(email))
            },
            enabled = true,
            label = { Text(text = "Email") },
            isError = !uiState.isEmailValid
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "emailError",
            textAlign = TextAlign.Start,
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = "password",
            onValueChange = { password ->
               onEvent(RegisterUiEvent.PasswordChanged(password))
            },
            enabled = true,
            label = { Text(text = "Password") },
            isError = !uiState.isPasswordValid
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "passwordError",
            textAlign = TextAlign.Start,
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = "retypePassword",
            onValueChange = { retypedPassword ->
                onEvent(RegisterUiEvent.RetypedPasswordChanged(retypedPassword))
            },
            enabled = true,
            label = { Text(text = "Password") },
            isError = !uiState.isRetypedPasswordValid
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "passwordError",
            textAlign = TextAlign.Start,
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(36.dp))
        Button(
            onClick = {
                keyboardController?.hide()
                onEvent(RegisterUiEvent.Register("email", "password"))
            },
            enabled = uiState.isRegisterButtonEnabled
        ) {
            Text(text = "Login")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    GizzHQTheme {
        RegisterScreen(
            uiState = RegisterUiState(),
            onEvent = {}
        )
    }
}