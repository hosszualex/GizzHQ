package com.ah.gizzhq.domain

import android.util.Log
import javax.inject.Inject

class MockAuthenticationRepositoryImpl @Inject constructor(): AuthenticationRepository {
    override suspend fun login(email: String, password: String) {
        Log.i("===>", "MOCK_LOGIN")
    }

    override suspend fun registerAccount(email: String, password: String) {
        Log.i("===>", "MOCK_REGISTER")
    }

    override suspend fun logout() {
        Log.i("===>", "MOCK_LOGOUT")
    }
}