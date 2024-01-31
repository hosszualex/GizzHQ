package com.ah.gizzhq.data

import io.appwrite.models.Session
import io.appwrite.models.User

interface Appwrite {
    suspend fun onLogin(email: String, password: String): Session
    suspend fun onRegister(email: String, password: String): User<Map<String, Any>>
    suspend fun onLogout()
}