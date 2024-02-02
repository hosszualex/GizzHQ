package com.example.gizzhq.usecases

import com.ah.gizzhq.data.repositories.AppwriteAuthenticationRepositoryImpl
import com.ah.gizzhq.data.repositories.AuthenticationRepository
import com.ah.gizzhq.domain.responses.RegisterResponse
import com.ah.gizzhq.domain.usecases.RegisterUserUseCase
import com.example.gizzhq.mocks.AppwriteMockService
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

class RegisterUserUseCaseTest {
    
    private lateinit var registerUserUseCase: RegisterUserUseCase

    @Before
    fun setup(){
        registerUserUseCase = RegisterUserUseCase(AppwriteAuthenticationRepositoryImpl(AppwriteMockService()))
    }
    @Test
    fun `Register is successful`() {
        runTest {
            registerUserUseCase(
                "newuser@email.com", "beautifulPassw0rd"
            ).let { response ->
                Assert.assertEquals(RegisterResponse.OnSuccess, response)
            }
        }
    }

    @Test
    fun `Register has failed, user already exists`() {
        runTest {
            registerUserUseCase(
                "user@exists.com", "beautifulPassw0rd"
            ).let { response ->
                Assert.assertEquals(RegisterResponse.OnErrorUserAlreadyExists, response)
            }
        }
    }

    @Test
    fun `Register has failed, socket has timed out`() {
        runTest {
            registerUserUseCase(
                "user@sockettimedout.com", "beautifulPassw0rd"
            ).let { response ->
                Assert.assertEquals(RegisterResponse.OnErrorNoInternet, response)
            }
        }
    }

    @Test
    fun `Register has failed, unknown host`() {
        runTest {
            registerUserUseCase(
                "user@unknownhost.com", "beautifulPassw0rd"
            ).let { response ->
                Assert.assertEquals(RegisterResponse.OnErrorNoInternet, response)
            }
        }
    }

    @Test
    fun `Register has failed, connection failed`() {
        runTest {
            registerUserUseCase(
                "user@conectionfailed.com", "beautifulPassw0rd"
            ).let { response ->
                Assert.assertEquals(RegisterResponse.OnErrorNoInternet, response)
            }
        }
    }

    @Test
    fun `Register has failed, rate limit reached`() {
        runTest {
            registerUserUseCase(
                "user@ratelimit.com", "beautifulPassw0rd"
            ).let { response ->
                Assert.assertEquals(
                    RegisterResponse.OnErrorGeneric(
                        errorCode = 429,
                        errorKey = "general_rate_limit_exceeded"
                    ), response
                )
            }
        }
    }
}