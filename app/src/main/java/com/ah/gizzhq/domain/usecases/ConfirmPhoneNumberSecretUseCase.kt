package com.ah.gizzhq.domain.usecases

import com.ah.gizzhq.data.repositories.AuthenticationRepository
import javax.inject.Inject

class ConfirmPhoneNumberSecretUseCase
    @Inject
    constructor(
        private val authenticationRepository: AuthenticationRepository,
    ) {
        suspend operator fun invoke(secret: String) = authenticationRepository.confirmPhoneSecret(secret)
    }
