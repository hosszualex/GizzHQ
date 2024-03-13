package com.ah.gizzhq.presentation.ui.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ah.gizzhq.data.repositories.UserDataRepository
import com.ah.gizzhq.domain.models.responses.RegisterResponse
import com.ah.gizzhq.domain.usecases.RegisterUserUseCase
import com.ah.gizzhq.domain.usecases.ValidateEmailUseCase
import com.ah.gizzhq.domain.usecases.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
    @Inject
    constructor(
        private val registerUserUseCase: RegisterUserUseCase,
        private val validatePasswordUseCase: ValidatePasswordUseCase,
        private val validateEmailUseCase: ValidateEmailUseCase,
        private val userDataRepository: UserDataRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(RegisterUiState())
        val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()
        var email by mutableStateOf("dragana@asd.com") // todo: delete test values
            private set
        var password by mutableStateOf("predator1A") // todo: delete test values
            private set
        var retypedPassword by mutableStateOf("predator1A") // todo: delete test values
            private set

        fun onEvent(event: RegisterUiEvent) {
            when (event) {
                is RegisterUiEvent.EmailChanged -> onEmailChanged(event.email)
                is RegisterUiEvent.PasswordChanged -> onPasswordChanged(event.password)
                is RegisterUiEvent.RetypedPasswordChanged -> onRetypedPasswordChanged(event.retypedPassword)
                is RegisterUiEvent.Register -> registerUser(event.email, event.password)
                // is RegisterUiEvent.Error -> onError()
                else -> Unit
            }
        }

        private fun registerUser(
            email: String,
            password: String,
        ) {
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true) }

                registerUserUseCase(email, password).let { result ->
                    when (result) {
                        is RegisterResponse.OnSuccess -> {
                        }
                        is RegisterResponse.OnErrorGeneric -> {
                        }
                        is RegisterResponse.OnErrorUserAlreadyExists -> {
                        }
                        is RegisterResponse.OnErrorTimeout -> {
                        }
                        is RegisterResponse.OnErrorNoInternet -> {
                        }
                    }
                }.also {
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }

        private fun onEmailChanged(email: String) {
            this.email = email
            val updatedIsEmailValid = validateEmailUseCase(email)
            _uiState.update {
                it.copy(
                    isEmailValid = updatedIsEmailValid,
                    isRegisterButtonEnabled =
                        updatedIsEmailValid &&
                            it.isPasswordValid &&
                            it.isRetypedPasswordValid &&
                            email.isNotEmpty() &&
                            password.isNotEmpty() &&
                            retypedPassword.isNotEmpty(),
                )
            }
        }

        private fun onPasswordChanged(password: String) {
            this.password = password
            val updatedIsPasswordValid = validatePasswordUseCase(password)
            _uiState.update {
                it.copy(
                    isPasswordValid = updatedIsPasswordValid,
                    isRegisterButtonEnabled =
                        updatedIsPasswordValid &&
                            it.isEmailValid &&
                            it.isRetypedPasswordValid &&
                            email.isNotEmpty() &&
                            password.isNotEmpty() &&
                            retypedPassword.isNotEmpty(),
                )
            }
        }

        private fun onRetypedPasswordChanged(password: String) {
            this.retypedPassword = password
            val updatedIsRetypedPasswordValid = this.retypedPassword == this.password && this.retypedPassword.isNotEmpty()
            _uiState.update {
                it.copy(
                    isRetypedPasswordValid = updatedIsRetypedPasswordValid, // vefifies if they are the same
                    isRegisterButtonEnabled =
                        updatedIsRetypedPasswordValid &&
                            it.isEmailValid &&
                            it.isPasswordValid &&
                            email.isNotEmpty() &&
                            password.isNotEmpty() &&
                            retypedPassword.isNotEmpty(),
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
    val isRegisterButtonEnabled: Boolean = true, // todo: change it to false
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true,
    val isRetypedPasswordValid: Boolean = true,
    val error: Error? = null,
)
