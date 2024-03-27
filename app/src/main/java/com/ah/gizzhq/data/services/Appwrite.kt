package com.ah.gizzhq.data.services

import io.appwrite.models.DocumentList
import io.appwrite.models.Session
import io.appwrite.models.User

interface Appwrite {
    suspend fun sendPhoneNumberSecret(phoneNumber: String)

    suspend fun confirmPhoneNumberSecret(secret: String): Session

    suspend fun onLogin(
        email: String,
        password: String,
    ): Session

    suspend fun onRegister(
        email: String,
        password: String,
    ): User<Map<String, Any>>

    suspend fun onLogout()

    suspend fun onFetchCollection(collectionId: String, queryLimit: Int = 25, offset: Int = 0): DocumentList<Map<String, Any>>
}
