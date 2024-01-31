package com.ah.gizzhq.data.repositories

import com.ah.gizzhq.domain.responses.RegisterResponse

interface AuthenticationRepository {

    suspend fun login(email: String, password: String): RegisterResponse

    suspend fun registerAccount(email: String, password: String): RegisterResponse

    suspend fun logout()

}
