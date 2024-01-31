package com.ah.gizzhq.domain.responses

sealed class RegisterResponse {
    data object OnSuccess: RegisterResponse()
    data object OnErrorTimeout: RegisterResponse()
    data object OnErrorNoInternet: RegisterResponse()
    data object OnErrorUserAlreadyExists: RegisterResponse()
    data class OnErrorGeneric(val errorKey: String): RegisterResponse()

}