package com.ah.gizzhq.data.repositories

import kotlinx.coroutines.flow.Flow

interface MusicDataRepository {
    suspend fun getAlbums(): Flow<String>
}
