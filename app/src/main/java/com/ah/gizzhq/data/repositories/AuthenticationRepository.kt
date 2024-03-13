package com.ah.gizzhq.data.repositories

import com.ah.gizzhq.domain.models.responses.RegisterResponse

interface AuthenticationRepository {
    suspend fun login(
        email: String,
        password: String,
    ): RegisterResponse

    suspend fun registerAccount(
        email: String,
        password: String,
    ): RegisterResponse

    suspend fun sendPhoneNumberSecret(phoneNumber: String)

    suspend fun confirmPhoneNumberSecret(secret: String): Result<Boolean>

    suspend fun confirmPhoneSecret(secret: String)

    suspend fun logout()
}
