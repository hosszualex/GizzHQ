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

        companion object {
            const val CURRENT_SESSION = "current"
        }

        init { // todo: need context from the DI module
            val client =
                Client(context)
                    .setEndpoint("https://cloud.appwrite.io/v1")
                    .setProject("65b7ba629b1fd5538239")

            account = Account(client)
        }

        override suspend fun onLogin(
            email: String,
            password: String,
        ): Session {
            return account.createEmailSession(email = email, password = password)
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
    }
