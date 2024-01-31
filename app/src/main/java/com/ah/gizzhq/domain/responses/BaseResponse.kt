package com.ah.gizzhq.domain.responses

sealed class BaseResponse {
    data object OnSuccess: BaseResponse()
    data class OnError(val errorKey: String): BaseResponse()
}