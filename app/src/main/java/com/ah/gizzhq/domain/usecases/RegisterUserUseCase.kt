package com.ah.gizzhq.domain.usecases

import com.ah.gizzhq.data.repositories.AuthenticationRepository
import com.ah.gizzhq.domain.responses.RegisterResponse
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(email: String, password: String): RegisterResponse =
        authenticationRepository.registerAccount(email, password)
}