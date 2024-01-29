package com.ah.gizzhq.domain

interface AuthenticationRepository {

    suspend fun login(email: String, password: String)

    suspend fun registerAccount(email: String, password: String)

    suspend fun logout()

}