package com.example.gizzhq.mocks

import com.ah.gizzhq.data.AppPreferences
import com.ah.gizzhq.domain.models.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AppPreferencesMock: AppPreferences {
    private var email: String = ""
    override val userData: Flow<UserData>
        get() = flow { UserData(email) }

    override suspend fun setEmail(email: String) {
        this.email = email
    }

    override suspend fun clearUserSession() {
    }
}