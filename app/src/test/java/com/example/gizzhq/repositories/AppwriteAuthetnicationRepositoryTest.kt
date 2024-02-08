package com.example.gizzhq.repositories

import android.content.Context
import com.ah.gizzhq.data.AppPreferencesDataSource
import com.ah.gizzhq.data.repositories.AppwriteAuthenticationRepositoryImpl
import com.ah.gizzhq.data.repositories.AuthenticationRepository
import com.ah.gizzhq.domain.models.responses.RegisterResponse
import com.example.gizzhq.mocks.AppPreferencesMock
import com.example.gizzhq.mocks.AppwriteMockService
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class AppwriteAuthetnicationRepositoryTest {

    private lateinit var mockAppwriteAuthenticationRepository: AuthenticationRepository

    @Before
    fun setup(){
        mockAppwriteAuthenticationRepository =
            AppwriteAuthenticationRepositoryImpl(AppwriteMockService(), AppPreferencesMock())
    }

    @Test
    fun `Register is successful`() {
        runTest {
            mockAppwriteAuthenticationRepository.registerAccount(
                "newuser@email.com", "beautifulPassw0rd"
            ).let { response ->
              assertEquals(RegisterResponse.OnSuccess, response)
            }
        }
    }

    @Test
    fun `Register has failed, user already exists`() {
        runTest {
            mockAppwriteAuthenticationRepository.registerAccount(
                "user@exists.com", "beautifulPassw0rd"
            ).let { response ->
                assertEquals(RegisterResponse.OnErrorUserAlreadyExists, response)
            }
        }
    }

    @Test
    fun `Register has failed, socket has timed out`() {
        runTest {
            mockAppwriteAuthenticationRepository.registerAccount(
                "user@sockettimedout.com", "beautifulPassw0rd"
            ).let { response ->
                assertEquals(RegisterResponse.OnErrorNoInternet, response)
            }
        }
    }

    @Test
    fun `Register has failed, unknown host`() {
        runTest {
            mockAppwriteAuthenticationRepository.registerAccount(
                "user@unknownhost.com", "beautifulPassw0rd"
            ).let { response ->
                assertEquals(RegisterResponse.OnErrorNoInternet, response)
            }
        }
    }

    @Test
    fun `Register has failed, connection failed`() {
        runTest {
            mockAppwriteAuthenticationRepository.registerAccount(
                "user@conectionfailed.com", "beautifulPassw0rd"
            ).let { response ->
                assertEquals(RegisterResponse.OnErrorNoInternet, response)
            }
        }
    }

    @Test
    fun `Register has failed, rate limit reached`() {
        runTest {
            mockAppwriteAuthenticationRepository.registerAccount(
                "user@ratelimit.com", "beautifulPassw0rd"
            ).let { response ->
                assertEquals(
                    RegisterResponse.OnErrorGeneric(
                    errorCode = 429,
                    errorKey = "general_rate_limit_exceeded"
                ), response)
            }
        }
    }
}