package com.ah.gizzhq.presentation.ui.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ah.gizzhq.R
import com.ah.gizzhq.presentation.theme.GizzHQTheme

@Composable
fun RegisterRoute(
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    RegisterScreen(
        viewModel.email,
        viewModel.password,
        viewModel.retypedPassword,
        uiState,
        viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RegisterScreen (
    email: String,
    password: String,
    retypedPassword: String,
    uiState: RegisterUiState,
    onEvent: (RegisterUiEvent) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val emailError by remember(uiState.isEmailValid) {
        mutableStateOf(
            if (uiState.isEmailValid) "" else "Email does not have the correct format"
        )
    }
    val passwordError by remember(uiState.isPasswordValid) {
        mutableStateOf(
            if (uiState.isPasswordValid) "" else "Password should have: \n- at least 8 characters\n- a lower case letter\n- an upper case letter\n - a number"
        )
    }

    val retypedPasswordError by remember(uiState.isRetypedPasswordValid) {
        mutableStateOf(
            if (uiState.isRetypedPasswordValid) "" else "Passwords are not matching"
        )
    }


    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { email ->
                onEvent(RegisterUiEvent.EmailChanged(email))
            },
            enabled = true,
            label = { Text(text = "Email") },
            isError = !uiState.isEmailValid
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = emailError,
            textAlign = TextAlign.Start,
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(16.dp))
        PasswordTextField(label = "Password",
            valuePassword = password,
            onValueChange = { password -> onEvent(RegisterUiEvent.PasswordChanged(password)) },
            isValid = uiState.isPasswordValid
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = passwordError,
            textAlign = TextAlign.Start,
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(16.dp))
        PasswordTextField(label = "Retyped Password",
            valuePassword = retypedPassword,
            onValueChange = { retypedPassword -> onEvent(RegisterUiEvent.RetypedPasswordChanged(retypedPassword)) },
            isValid = uiState.isRetypedPasswordValid
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = retypedPasswordError,
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
            Text(text = "Register Account")
        }
    }
}

@Composable
private fun PasswordTextField(label: String, valuePassword: String, onValueChange: (text: String) -> Unit, isValid: Boolean) {
    var isVisible by remember { mutableStateOf(false) }

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = valuePassword,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        enabled = true,
        singleLine = true,
        label = { Text(text = label) },
        isError = !isValid,
        trailingIcon = {
            IconButton(onClick = {
                isVisible = !isVisible
            }) {
                Icon(
                    painterResource(id = if (isVisible) R.drawable.ic_visibility_on else R.drawable.ic_visibility_off),
                    contentDescription = "Visibility",
                )
            }
        }
    )
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
            onEvent = {}
        )
    }
}