package com.ah.gizzhq.presentation.ui.register

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ah.gizzhq.domain.usecases.ValidatePasswordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class RegisterViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var retypedPassword by mutableStateOf("")
        private set


    fun onEvent(event: RegisterUiEvent) {
        when (event) {
            is RegisterUiEvent.EmailChanged -> onEmailChanged(event.email)
            is RegisterUiEvent.PasswordChanged -> onPasswordChanged(event.password)
            is RegisterUiEvent.RetypedPasswordChanged -> onRetypedPasswordChanged(event.retypedPassword)
            is RegisterUiEvent.Register -> register(event.email, event.password)
            //is RegisterUiEvent.Error -> onError()
            else -> Unit
        }
    }

    private fun register(email: String, password: String) {

    }

    private fun onEmailChanged(email: String) {
        this.email = email
        val updatedIsEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        _uiState.update {
            it.copy(
                isEmailValid = updatedIsEmailValid,
                isRegisterButtonEnabled = updatedIsEmailValid
                        && it.isPasswordValid
                        && it.isRetypedPasswordValid
                        && email.isNotEmpty()
                        && password.isNotEmpty()
                        && retypedPassword.isNotEmpty()
            )
        }
    }

    private fun onPasswordChanged(password: String) {
        this.password = password
        val updatedIsPasswordValid = ValidatePasswordUseCase.validatePassword(password)
        _uiState.update {
            it.copy(
                isPasswordValid = updatedIsPasswordValid,
                isRegisterButtonEnabled = updatedIsPasswordValid
                        && it.isEmailValid
                        && it.isRetypedPasswordValid
                        && email.isNotEmpty()
                        && password.isNotEmpty()
                        && retypedPassword.isNotEmpty()
            )
        }
    }

    private fun onRetypedPasswordChanged(password: String) {
        this.retypedPassword = password
        val updatedIsRetypedPasswordValid = this.retypedPassword == this.password && this.retypedPassword.isNotEmpty()
        _uiState.update {
            it.copy(
                isRetypedPasswordValid = updatedIsRetypedPasswordValid, // vefifies if they are the same
                isRegisterButtonEnabled = updatedIsRetypedPasswordValid
                        && it.isEmailValid
                        && it.isPasswordValid
                        && email.isNotEmpty()
                        && password.isNotEmpty()
                        && retypedPassword.isNotEmpty()
            )
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