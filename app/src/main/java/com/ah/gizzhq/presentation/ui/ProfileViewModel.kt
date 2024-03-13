package com.ah.gizzhq.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ah.gizzhq.domain.usecases.SendPhoneNumberSecretUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
    @Inject
    constructor(
        private val sendPhoneNumberSecretUseCase: SendPhoneNumberSecretUseCase,
    ) : ViewModel() {
        fun sendPhoneNumber() {
            viewModelScope.launch {
                sendPhoneNumberSecretUseCase("+40752250482")
            }
        }
    }
