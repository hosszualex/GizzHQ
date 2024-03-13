package com.ah.gizzhq.presentation.ui.profile.phoneRegister

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ah.gizzhq.presentation.ui.ProgressIndicator

@Composable
fun PhoneRegisterRoute(viewModel: PhoneRegisterViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PhoneRegisterScreen(
        uiState,
        viewModel::onEvent,
    )
}

@Composable
fun PhoneRegisterScreen(
    uiState: PhoneNumberRegisterUiState,
    onEvent: (PhoneNumberRegisterUiEvent) -> Unit,
) {

    var phoneNumber by remember {
        mutableStateOf(
            "+40752254082"
        )
    }


    val keyboardController = LocalSoftwareKeyboardController.current
    // call the viewModel variable and call the next screen
    // should be in UI state and call the on navigate to profile

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
/*        PhoneNumberTextField(
            label = "(123) 123 3213 123",
            valuePhoneNumber = phoneNumber,
            onValueChange = { newPhoneNumber ->
                phoneNumber = newPhoneNumber
            }
        )*/


        Spacer(modifier = Modifier.height(36.dp))
        Button(
            onClick = {
                keyboardController?.hide()
                onEvent(PhoneNumberRegisterUiEvent.SendPhoneNumberSecret(phoneNumber))
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
