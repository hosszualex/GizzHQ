package com.ah.gizzhq.data.services

import android.content.Context
import io.appwrite.Client
import io.appwrite.ID
import io.appwrite.models.*
import io.appwrite.services.*
import javax.inject.Inject

class AppwriteService
    @Inject
    constructor(
        private val context: Context,
    ) : Appwrite {
        private val account: Account
        private val database: Databases

        private var sessionToken: Token? = null

        companion object {
            const val CURRENT_SESSION = "current"
            const val DATABASE_ID = "65e4b5c6232ea1a830c1"
            const val ALBUM_COLLECTION_ID = "65e4b5dc032ec098b94f"
        }

        init {
            val client =
                Client(context)
                    .setEndpoint("https://cloud.appwrite.io/v1")
                    .setProject("65b7ba629b1fd5538239")

            account = Account(client)
            database = Databases(client)
        }

        override suspend fun onLogin(
            email: String,
            password: String,
        ): Session {
            return account.createEmailSession(email = email, password = password)
        }

        override suspend fun sendPhoneNumberSecret(phoneNumber: String) {
            val sessionToken =
                account.createPhoneSession(
                    userId = ID.unique(),
                    phone = phoneNumber,
                )
            this.sessionToken = sessionToken
        }

        override suspend fun confirmPhoneNumberSecret(secret: String): Session {
            return account.updatePhoneSession(
                userId = this.sessionToken?.userId.toString(),
                secret = secret,
            )
        }

        override suspend fun onRegister(
            email: String,
            password: String,
        ): User<Map<String, Any>> {
            return account.create(
                userId = ID.unique(),
                email = email,
                password = password,
            )
        }

        override suspend fun onLogout() {
            account.deleteSession(CURRENT_SESSION)
        }

        override suspend fun onFetchCollection(collectionId: String): DocumentList<Map<String, Any>> {
            return database.listDocuments(
                databaseId = DATABASE_ID,
                collectionId = collectionId,
            )
        }
    }
