package com.ah.gizzhq.domain

import android.util.Log
import javax.inject.Inject

class AppwriteAuthenticationRepositoryImpl @Inject constructor(): AuthenticationRepository {
    override suspend fun login(email: String, password: String) {
        Log.i("===>", "APP_LOGIN")

        //Appwrite.onLogin(email, password)
    }

    override suspend fun registerAccount(email: String, password: String) {
        Log.i("===>", "APP_LOGIN")

        //Appwrite.onRegister(email, password)
    }

    override suspend fun logout() {
        Log.i("===>", "APP_LOGIN")

        //Appwrite.onLogout()
    }
}