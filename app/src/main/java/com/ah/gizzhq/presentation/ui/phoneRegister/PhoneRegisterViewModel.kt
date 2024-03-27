package com.ah.gizzhq.presentation.ui.phoneRegister

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ah.gizzhq.domain.ErrorUtil
import com.ah.gizzhq.domain.models.PhoneNumberCountryCode
import com.ah.gizzhq.domain.usecases.GetPhoneNumberCountryCodesUseCase
import com.ah.gizzhq.domain.usecases.SendPhoneNumberSecretUseCase
import com.google.i18n.phonenumbers.Phonenumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhoneRegisterViewModel
    @Inject
    constructor(
        private val sendPhoneNumberSecretUseCase: SendPhoneNumberSecretUseCase,
        private val getPhoneNumberCountryCodesUseCase: GetPhoneNumberCountryCodesUseCase
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(PhoneNumberRegisterUiState())
        val uiState: StateFlow<PhoneNumberRegisterUiState> = _uiState.asStateFlow()

        fun onEvent(event: PhoneNumberRegisterUiEvent) {
            when (event) {
                is PhoneNumberRegisterUiEvent.SendPhoneNumberSecret -> onSendPhoneNumberSecret(event.phoneNumber)
                is PhoneNumberRegisterUiEvent.OnGetPhoneNumberCountryCodes -> getPhoneNumberCountryCodes()
                else -> Unit
            }
        }

        private fun getPhoneNumberCountryCodes() {
            viewModelScope.launch {
                getPhoneNumberCountryCodesUseCase.invoke()
                    .catch { exception ->
                        _uiState.update { it.copy(error = ErrorUtil.parsedErrorThrowable(exception)) }
                    }
                    .collect { codes ->
                        _uiState.update {
                            it.copy(phoneNumberCountryCodes = codes)
                        }
                    }
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
    data class SendPhoneNumberSecret(val phoneNumber: String) : PhoneNumberRegisterUiEvent
    data object OnGetPhoneNumberCountryCodes: PhoneNumberRegisterUiEvent
}

data class PhoneNumberRegisterUiState(
    val isLoading: Boolean = false,
    val isNextButtonEnabled: Boolean = true,
    val isPhoneNumberValid: Boolean = true,
    val phoneNumberCountryCodes: List<PhoneNumberCountryCode> = listOf(),
    val error: Throwable? = null,
)
