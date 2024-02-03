package com.ah.gizzhq.domain.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ah.gizzhq.domain.models.UserData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.map


private val Context.dataStore by preferencesDataStore(name = "app_preferences")
class AppPreferencesDataSource @Inject constructor(
    @ApplicationContext context: Context
) {

    private val dataStore = context.dataStore

    val userData = dataStore.data.map { preferences ->
        val email = preferences[emailKey]
        UserData(email = email)
    }

    suspend fun setEmail(email: String) {
        dataStore.edit { preferences ->
            preferences[emailKey] = email
        }
    }

    companion object {
        private const val EMAIL = "email"
        private val emailKey = stringPreferencesKey(EMAIL)
    }
}