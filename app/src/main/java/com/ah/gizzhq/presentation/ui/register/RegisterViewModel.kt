package com.ah.gizzhq.presentation.ui.register

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class RegisterViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onEvent(event: RegisterUiEvent) {
        when (event) {
            else -> Unit
//            is RegisterUiEvent.EmailChanged -> onEmailChanged(event.email)
//            is RegisterUiEvent.PasswordChanged -> onPasswordChanged(event.password)
//            is RegisterUiEvent.Register -> login(event.email, event.password)
//            is RegisterUiEvent.Error -> onError()
        }
    }

}

sealed interface RegisterUiEvent {
    data class EmailChanged(val email: String) : RegisterUiEvent
    data class PasswordChanged(val password: String) : RegisterUiEvent
    data class RetypedPasswordChanged(val retypedPassword: String) : RegisterUiEvent
    data class Register(val email: String, val password: String) : RegisterUiEvent
    data class Error(val errorMessage: String) : RegisterUiEvent
}

data class RegisterUiState(
    val isLoading: Boolean = false,
    val isRegisterButtonEnabled: Boolean = false,
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true,
    val isRetypedPasswordValid: Boolean = true,
    val error: Error? = null
)