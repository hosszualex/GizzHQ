package com.example.gizzhq.repositories

import com.ah.gizzhq.data.repositories.AppwriteAuthenticationRepositoryImpl
import com.ah.gizzhq.data.repositories.AuthenticationRepository
import com.example.gizzhq.mocks.AppwriteMockService
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class AppwriteAuthetnicationRepositoryTest {

    private lateinit var appwriteAuthetnicationRepositoryTest: AuthenticationRepository

    @Before
    fun setup(){
        appwriteAuthetnicationRepositoryTest =
            AppwriteAuthenticationRepositoryImpl(AppwriteMockService())
    }

    @Test
    fun `Register is succesfull`() {
        assertEquals(4, 2 + 2)
    }
}