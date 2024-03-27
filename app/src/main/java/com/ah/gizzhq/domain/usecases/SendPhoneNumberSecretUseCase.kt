package com.ah.gizzhq.domain.usecases

import com.ah.gizzhq.data.repositories.authentication.AuthenticationRepository
import javax.inject.Inject

class SendPhoneNumberSecretUseCase
    @Inject
    constructor(
        private val authenticationRepository: AuthenticationRepository,
    ) {
        suspend operator fun invoke(phoneNumber: String) = authenticationRepository.sendPhoneNumberSecret(phoneNumber)
    }
