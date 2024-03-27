package com.ah.gizzhq.data.repositories.data

import com.ah.gizzhq.data.services.Appwrite
import com.ah.gizzhq.data.services.AppwriteService
import com.ah.gizzhq.domain.models.PhoneNumberCountryCode
import io.appwrite.extensions.toJson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import javax.inject.Inject

class AppwriteDataRepositoryImpl
@Inject
constructor(
    private val appwrite: Appwrite,
    private val json: Json
) : DataRepository {
    override suspend fun getAlbums(): Flow<String> =
        flow {
            val documentList = appwrite.onFetchCollection(AppwriteService.ALBUM_COLLECTION_ID)

            // todo: send this back with flow
            // todo: can do an offline database system here
        }

    override suspend fun getPhoneNumberCountryCode(): Flow<List<PhoneNumberCountryCode>> = flow {
        val phoneNumberCountryCodesCollection =
            appwrite.onFetchCollection(
                collectionId = AppwriteService.PHONE_NUMBER_COUNTRY_CODES_ID,
                queryLimit = 200
            )
        val mappedPhoneNumberCountryCodes: List<PhoneNumberCountryCode> = phoneNumberCountryCodesCollection.documents.map {
            json.decodeFromString<PhoneNumberCountryCode>(it.data.toJson())
        }
        emit(mappedPhoneNumberCountryCodes)
    }
}
