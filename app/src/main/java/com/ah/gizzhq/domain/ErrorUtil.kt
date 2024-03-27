package com.ah.gizzhq.domain

import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ErrorUtil {
    private const val ERROR_UNKNOWN_CODE = 600
    private const val ERROR_NO_INTERNET_CODE = 700
    private val ERROR_NO_INTERNET_EXCEPTION =
        Throwable("No internet connection.", Throwable(ERROR_NO_INTERNET_CODE.toString()))
    fun parsedErrorThrowable(exception: Throwable): Throwable {
        return when (exception) {
            is UnknownHostException,
            is SocketTimeoutException,
            is ConnectException,
            is SocketException,
            ->
                ERROR_NO_INTERNET_EXCEPTION
            else -> Throwable(exception.message, Throwable(ERROR_UNKNOWN_CODE.toString()))
        }
    }
}
