package com.ah.gizzhq.data.repositories.data

import com.ah.gizzhq.domain.models.PhoneNumberCountryCode
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    suspend fun getAlbums(): Flow<String>

    suspend fun getPhoneNumberCountryCode(): Flow<List<PhoneNumberCountryCode>>
}
