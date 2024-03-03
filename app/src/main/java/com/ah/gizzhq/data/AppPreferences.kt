package com.ah.gizzhq.data

import com.ah.gizzhq.domain.models.UserData
import kotlinx.coroutines.flow.Flow

interface AppPreferences {
    val userData: Flow<UserData>

    suspend fun setEmail(email: String)

    suspend fun clearUserSession()
}
