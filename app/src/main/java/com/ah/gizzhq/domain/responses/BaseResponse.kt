package com.ah.gizzhq.domain.responses

sealed class BaseResponse {
    data class OnSuccess(val data: Any): BaseResponse()
    data object OnErrorTimeout: BaseResponse()
    data object OnErrorNoInternet: BaseResponse()
    data class OnErrorGeneric(val errorKey: String = "unknown-error"): BaseResponse()
}