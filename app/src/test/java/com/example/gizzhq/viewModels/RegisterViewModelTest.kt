package com.example.gizzhq.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ah.gizzhq.data.repositories.AppwriteAuthenticationRepositoryImpl
import com.ah.gizzhq.domain.usecases.RegisterUserUseCase
import com.ah.gizzhq.domain.usecases.ValidateEmailUseCase
import com.ah.gizzhq.domain.usecases.ValidatePasswordUseCase
import com.ah.gizzhq.presentation.ui.register.RegisterUiEvent
import com.ah.gizzhq.presentation.ui.register.RegisterViewModel
import com.example.gizzhq.mocks.AppwriteMockService
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RegisterViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var registerViewModel: RegisterViewModel
    @Before
    fun setup() {
        registerViewModel = RegisterViewModel(
            registerUserUseCase = RegisterUserUseCase(AppwriteAuthenticationRepositoryImpl(AppwriteMockService())),
            validatePasswordUseCase = ValidatePasswordUseCase(),
            validateEmailUseCase = ValidateEmailUseCase()
        )
    }

    @Test
    fun `Successful email validation`() {
        registerViewModel.onEvent(RegisterUiEvent.EmailChanged("user@email.com"))
        Assert.assertTrue(registerViewModel.uiState.value.isEmailValid)
    }
}