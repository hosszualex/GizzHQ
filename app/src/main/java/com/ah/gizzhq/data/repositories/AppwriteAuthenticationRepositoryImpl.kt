package com.ah.gizzhq.data.repositories

import com.ah.gizzhq.data.AppPreferences
import com.ah.gizzhq.data.services.Appwrite
import com.ah.gizzhq.domain.models.responses.RegisterResponse
import io.appwrite.exceptions.AppwriteException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class AppwriteAuthenticationRepositoryImpl
    @Inject
    constructor(
        private val appwrite: Appwrite,
        private val preferencesDataSource: AppPreferences,
    ) : AuthenticationRepository {
        override suspend fun login(
            email: String,
            password: String,
        ): RegisterResponse {
            runCatching {
                appwrite.onLogin(email, password)
            }.onSuccess {
                return RegisterResponse.OnSuccess
            }.onFailure { throwable ->
                return handleRegisterError(throwable)
            }
                .also { // this is the default use case for return
                    return RegisterResponse.OnErrorGeneric()
                }
        }

        override suspend fun sendPhoneNumberSecret(phoneNumber: String) {
            appwrite.sendPhoneNumberSecret(phoneNumber)
        }

        override suspend fun confirmPhoneNumberSecret(secret: String): Result<Boolean> {
            runCatching {
                appwrite.confirmPhoneNumberSecret(secret)
            }.onSuccess {
                return Result.success(true)
            }
                .onFailure {
                    return Result.failure(it)
                }.also {
                    return Result.failure(Throwable("unknown-error"))
                }
        }

        override suspend fun confirmPhoneSecret(secret: String) {
            appwrite.confirmPhoneNumberSecret(secret)
        }

        override suspend fun registerAccount(
            email: String,
            password: String,
        ): RegisterResponse {
            runCatching {
                appwrite.onRegister(email, password)
            }.onSuccess {
                preferencesDataSource.setEmail(email)
                return RegisterResponse.OnSuccess
            }.onFailure { throwable ->
                return handleRegisterError(throwable)
            }
                .also { // this is the default use case for return
                    return RegisterResponse.OnErrorGeneric()
                }
        }

        override suspend fun logout() { // wip
            runCatching {
                appwrite.onLogout()
            }.onSuccess {
                preferencesDataSource.clearUserSession()
            }.onFailure {
            }
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
