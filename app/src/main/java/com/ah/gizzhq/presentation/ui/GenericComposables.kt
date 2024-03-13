package com.ah.gizzhq.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import com.ah.gizzhq.R

@Composable
fun PasswordTextField(
    label: String,
    valuePassword: String,
    onValueChange: (String) -> Unit,
    isPasswordValid: Boolean,
) {
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
        isError = !isPasswordValid,
        trailingIcon = {
            IconButton(onClick = {
                isVisible = !isVisible
            }) {
                Icon(
                    painterResource(id = if (isVisible) R.drawable.ic_visibility_on else R.drawable.ic_visibility_off),
                    contentDescription = "Visibility",
                )
            }
        },
    )
}

@Composable
fun EmailTextField(
    label: String,
    valueEmail: String,
    onValueChange: (String) -> Unit,
    isEmailValid: Boolean,
) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = valueEmail,
        onValueChange = onValueChange,
        enabled = true,
        singleLine = true,
        label = { Text(text = label) },
        isError = !isEmailValid,
    )
}

@Composable
fun PhoneNumberTextField(
    label: String,
    valuePhoneNumber: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = valuePhoneNumber,
        onValueChange = onValueChange,
        enabled = true,
        singleLine = true,
        label = { Text(text = label) }
    )
}

@Composable
fun ErrorText(text: String) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = text,
        textAlign = TextAlign.Start,
        color = Color.Red,
    )
}

@Composable
fun ProgressIndicator(modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .fillMaxSize()
                .pointerInput(Unit) {},
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}
