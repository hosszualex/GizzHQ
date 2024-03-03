package com.example.gizzhq.mocks

import com.ah.gizzhq.data.services.Appwrite
import io.appwrite.exceptions.AppwriteException
import io.appwrite.models.Preferences
import io.appwrite.models.Session
import io.appwrite.models.User
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class AppwriteMockService : Appwrite {
    override suspend fun onLogin(
        email: String,
        password: String,
    ): Session {
        return Session(
            id = "aptent",
            createdAt = "explicari",
            userId = "himenaeos",
            expire = "interdum",
            provider = "vestibulum",
            providerUid = "eos",
            providerAccessToken = "velit",
            providerAccessTokenExpiry = "suscipit",
            providerRefreshToken = "nominavi",
            ip = "petentium",
            osCode = "veritus",
            osName = "Daniel Espinoza",
            osVersion = "vitae",
            clientType = "sapien",
            clientCode = "mus",
            clientName = "Kris Vaughn",
            clientVersion = "velit",
            clientEngine = "ignota",
            clientEngineVersion = "ocurreret",
            deviceName = "Lessie Noble",
            deviceBrand = "interesset",
            deviceModel = "liber",
            countryCode = "Niger",
            countryName = "Zimbabwe",
            current = false,
        )
    }

    override suspend fun onRegister(
        email: String,
        password: String,
    ): User<Map<String, Any>> {
        val preferences = HashMap<String, String>(0)
        // todo: need to diferentiate Appwrite Exceptions vs SocketTimeoutExceptions or others t is UnknownHostException || t is SocketTimeoutException || t is ConnectException
        when (email) {
            "user@exists.com" -> throw AppwriteException(code = 409, type = "user_already_exists")
            "user@ratelimit.com" -> throw AppwriteException(code = 429, type = "general_rate_limit_exceeded")
            "user@sockettimedout.com" -> throw SocketTimeoutException()
            "user@unknownhost.com" -> throw UnknownHostException()
            "user@conectionfailed.com" -> throw ConnectException()
            else -> Unit
        }
        return User.invoke(
            id = "asd",
            createdAt = "",
            updatedAt = "",
            name = "",
            password = password,
            hash = "",
            hashOptions = "",
            registration = "",
            status = true,
            labels = listOf(),
            passwordUpdate = "",
            email = email,
            phone = "",
            emailVerification = false,
            phoneVerification = false,
            prefs = Preferences(preferences),
            accessedAt = "",
        )
    }

    override suspend fun onLogout() {
        // TODO("Not yet implemented")
    }
}
