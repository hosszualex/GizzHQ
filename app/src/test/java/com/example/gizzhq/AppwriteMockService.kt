package com.example.gizzhq

import com.ah.gizzhq.data.services.Appwrite
import io.appwrite.models.Preferences
import io.appwrite.models.Session
import io.appwrite.models.User

class AppwriteMockService: Appwrite {
    override suspend fun onLogin(email: String, password: String): Session {
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
            current = false
        )
    }

    override suspend fun onRegister(email: String, password: String): User<Map<String, Any>> {
        //TODO("Not yet implemented")
        val preferences = HashMap<String, String>(5)
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
            accessedAt = ""
        )
    }

    override suspend fun onLogout() {
        //TODO("Not yet implemented")
    }
}