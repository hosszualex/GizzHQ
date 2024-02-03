package com.ah.gizzhq.domain.models.responses

sealed class RegisterResponse {
    data object OnSuccess: RegisterResponse()
    data object OnErrorTimeout: RegisterResponse()
    data object OnErrorNoInternet: RegisterResponse()
    data object OnErrorUserAlreadyExists: RegisterResponse()
    data class OnErrorGeneric(val errorCode: Int = 600, val errorKey: String = "unknown-error"): RegisterResponse()

}