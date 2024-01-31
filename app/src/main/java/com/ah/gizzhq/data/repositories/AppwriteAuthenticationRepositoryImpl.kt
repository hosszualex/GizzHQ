package com.ah.gizzhq.data.repositories

import com.ah.gizzhq.data.services.Appwrite
import com.ah.gizzhq.domain.responses.RegisterResponse
import javax.inject.Inject

class AppwriteAuthenticationRepositoryImpl @Inject constructor(
    private val appwrite: Appwrite
) : AuthenticationRepository {
    override suspend fun login(email: String, password: String): RegisterResponse {
        appwrite.onLogin(email, password)
        return RegisterResponse.OnSuccess
    }

    override suspend fun registerAccount(email: String, password: String): RegisterResponse {
        runCatching {
            appwrite.onRegister(email, password)
        }.onSuccess {
                return RegisterResponse.OnSuccess
            }.onFailure { throwable ->
                return handleRegisterError(throwable)
            }
            .also { // this is the default use case for return
                return RegisterResponse.OnErrorGeneric("unhandled-error")
            }
    }

    override suspend fun logout() {
        //Appwrite.onLogout()
    }

    private fun handleRegisterError(exception: Throwable): RegisterResponse {
        // todo: create register error handler
        return when (exception.message) {
            "user-exists" -> RegisterResponse.OnErrorUserAlreadyExists
            "timeout" -> RegisterResponse.OnErrorTimeout
            "internet-failed" -> RegisterResponse.OnErrorNoInternet
            else -> RegisterResponse.OnErrorGeneric("unh") // todo extract error show it here
        }
    }
}