package com.ah.gizzhq.domain

import com.google.gson.annotations.SerializedName

open class BaseResponse(@SerializedName("error") val error: BaseError? = null)

class BaseError(
    @SerializedName("message") val message: String,
    @SerializedName("code") val internalCode: String,
    var serverCode: Int
)