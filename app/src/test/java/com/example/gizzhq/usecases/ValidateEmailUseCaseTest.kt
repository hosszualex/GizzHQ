package com.example.gizzhq.usecases

import com.ah.gizzhq.domain.usecases.ValidateEmailUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ValidateEmailUseCaseTest {
    private lateinit var validateEmailUseCase: ValidateEmailUseCase

    @Before
    fun setup() {
        validateEmailUseCase = ValidateEmailUseCase()
    }

    @Test
    fun `Email format is correct`() = runBlocking {
        Assert.assertTrue(validateEmailUseCase("name@gmail.com"))
    }

    @Test
    fun `Email does not have the correct format`() = runBlocking {
        Assert.assertFalse(validateEmailUseCase("namegmailcom"))
    }

}