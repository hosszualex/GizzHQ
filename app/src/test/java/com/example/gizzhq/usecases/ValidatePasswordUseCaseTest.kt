package com.example.gizzhq.usecases

import com.ah.gizzhq.domain.usecases.ValidateEmailUseCase
import com.ah.gizzhq.domain.usecases.ValidatePasswordUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ValidatePasswordUseCaseTest {
    private lateinit var validatePasswordUseCase: ValidatePasswordUseCase

    @Before
    fun setup() {
        validatePasswordUseCase = ValidatePasswordUseCase()
    }

    @Test
    fun `Correct password format`() = runBlocking {
        Assert.assertTrue(validatePasswordUseCase("testPassword1"))
    }

    @Test
    fun `Password is too short`() = runBlocking {
        Assert.assertFalse(validatePasswordUseCase("name1A"))
    }

    @Test
    fun `Password does not contain a number`() = runBlocking {
        Assert.assertFalse(validatePasswordUseCase("mybeautifulpasswordA"))
    }

    @Test
    fun `Password does not contain an uppercase letter`() = runBlocking {
        Assert.assertFalse(validatePasswordUseCase("mybeautifulpassword1"))
    }

    @Test
    fun `Password does not contain a lowercase letter`() = runBlocking {
        Assert.assertFalse(validatePasswordUseCase("MYBEAUTIFULPASSWORD1"))
    }
}