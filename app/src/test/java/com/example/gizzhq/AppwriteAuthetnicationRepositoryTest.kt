package com.example.gizzhq

import com.ah.gizzhq.data.repositories.AppwriteAuthenticationRepositoryImpl
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class AppwriteAuthetnicationRepositoryTest {

    private val repository = AppwriteAuthenticationRepositoryImpl(AppwriteMockService())
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}