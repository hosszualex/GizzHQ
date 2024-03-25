package com.ah.gizzhq.presentation.ui.phoneRegister

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ah.gizzhq.data.repositories.UserDataRepository
import com.ah.gizzhq.domain.usecases.SendPhoneNumberSecretUseCase
import com.google.i18n.phonenumbers.Phonenumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhoneRegisterViewModel
    @Inject
    constructor(
        private val sendPhoneNumberSecretUseCase: SendPhoneNumberSecretUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(PhoneNumberRegisterUiState())
        val uiState: StateFlow<PhoneNumberRegisterUiState> = _uiState.asStateFlow()

        fun onEvent(event: PhoneNumberRegisterUiEvent) {
            when (event) {
                is PhoneNumberRegisterUiEvent.SendPhoneNumberSecret -> onSendPhoneNumberSecret(event.phoneNumber)
                is PhoneNumberRegisterUiEvent.Error -> {
                    // todo alex:

                }

                else -> Unit
            }
        }


        private fun onSendPhoneNumberSecret(phoneNumber: String) {
            Phonenumber.PhoneNumber()
            viewModelScope.launch {
                runCatching {
                    sendPhoneNumberSecretUseCase(phoneNumber)
                }
                    .onSuccess {

                    }
                    .onFailure {

                    }
            }
        }
    }

sealed interface PhoneNumberRegisterUiEvent {
    data class PhoneNumberChanged(val phoneNumber: String) : PhoneNumberRegisterUiEvent
    data class SendPhoneNumberSecret(val phoneNumber: String) : PhoneNumberRegisterUiEvent
    data class Error(val errorMessage: String) : PhoneNumberRegisterUiEvent
}

data class PhoneNumberRegisterUiState(
    val isLoading: Boolean = false,
    val isNextButtonEnabled: Boolean = true, // todo: change it to false
    val isPhoneNumberValid: Boolean = true,
    val error: Error? = null,
)
