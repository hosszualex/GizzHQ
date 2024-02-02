package com.ah.gizzhq.data.repositories

import com.ah.gizzhq.data.services.Appwrite
import com.ah.gizzhq.domain.responses.RegisterResponse
import io.appwrite.exceptions.AppwriteException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
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
                return RegisterResponse.OnErrorGeneric()
            }
    }

    override suspend fun logout() {
        //Appwrite.onLogout()
    }

    private fun handleRegisterError(exception: Throwable): RegisterResponse {
        return when (exception) {
            is AppwriteException -> {
                when (exception.code) {
                    409 -> RegisterResponse.OnErrorUserAlreadyExists
                    else -> RegisterResponse.OnErrorGeneric(errorCode = exception.code ?: 0, errorKey = exception.type.toString())
                }
            }
            is UnknownHostException, is SocketTimeoutException, is ConnectException -> RegisterResponse.OnErrorNoInternet
            else -> RegisterResponse.OnErrorGeneric(errorKey = exception.message.toString())
        }
    }

}