package com.ah.gizzhq.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ah.gizzhq.domain.models.UserData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "app_preferences")

class AppPreferencesDataSource
    @Inject
    constructor(
        @ApplicationContext context: Context,
    ) : AppPreferences {
        private val dataStore = context.dataStore

        override val userData =
            dataStore.data.map { preferences ->
                val email = preferences[emailKey]
                UserData(email = email)
            }

        override suspend fun setEmail(email: String) {
            dataStore.edit { preferences ->
                preferences[emailKey] = email
            }
        }

        override suspend fun setUserId(userId: String) {
            dataStore.edit { preferences ->
                preferences[userIdKey]
            }
        }

        override suspend fun clearUserSession() {
            dataStore.edit { preferences ->
                preferences.remove(emailKey)
                preferences.remove(userIdKey)
            }
        }

        companion object {
            private const val EMAIL = "email"
            private const val USER_ID = "user_id"

            private val emailKey = stringPreferencesKey(EMAIL)
            private val userIdKey = stringPreferencesKey(USER_ID)
        }
    }
