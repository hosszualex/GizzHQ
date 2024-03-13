package com.ah.gizzhq.data.repositories

import com.ah.gizzhq.data.AppPreferences
import com.ah.gizzhq.data.services.Appwrite
import com.ah.gizzhq.data.services.AppwriteService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AppwriteMusicDataRepositoryImpl
    @Inject
    constructor(
        private val appwrite: Appwrite,
        private val preferencesDataSource: AppPreferences,
    ) : MusicDataRepository {
        override suspend fun getAlbums(): Flow<String> =
            callbackFlow {
                val documentList = appwrite.onFetchCollection(AppwriteService.ALBUM_COLLECTION_ID)

                // todo: send this back with flow
                // todo: can do an offline database system here
            }
    }
