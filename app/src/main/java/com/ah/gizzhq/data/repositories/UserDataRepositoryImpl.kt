package com.ah.gizzhq.data.repositories

import com.ah.gizzhq.data.AppPreferences
import com.ah.gizzhq.domain.models.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val preferencesDataSource: AppPreferences
) { // todo: write interface

    val userData: Flow<UserData>
        get() = preferencesDataSource.userData

}