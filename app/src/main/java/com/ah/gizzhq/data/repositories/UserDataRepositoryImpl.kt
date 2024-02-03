package com.ah.gizzhq.data.repositories

import com.ah.gizzhq.data.services.Appwrite
import com.ah.gizzhq.domain.models.UserData
import com.ah.gizzhq.domain.models.responses.RegisterResponse
import com.ah.gizzhq.domain.utils.AppPreferencesDataSource
import io.appwrite.exceptions.AppwriteException
import kotlinx.coroutines.flow.Flow
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val preferencesDataSource: AppPreferencesDataSource
) {

    val userData: Flow<UserData>
        get() = preferencesDataSource.userData

}