package com.example.gizzhq.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ah.gizzhq.data.repositories.AppwriteAuthenticationRepositoryImpl
import com.ah.gizzhq.data.repositories.UserDataRepositoryImpl
import com.ah.gizzhq.domain.usecases.RegisterUserUseCase
import com.ah.gizzhq.domain.usecases.ValidateEmailUseCase
import com.ah.gizzhq.domain.usecases.ValidatePasswordUseCase
import com.ah.gizzhq.presentation.ui.register.RegisterUiEvent
import com.ah.gizzhq.presentation.ui.register.RegisterViewModel
import com.example.gizzhq.mocks.AppPreferencesMock
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
        val mockPreferences = AppPreferencesMock()
        registerViewModel = RegisterViewModel(
            registerUserUseCase = RegisterUserUseCase(AppwriteAuthenticationRepositoryImpl(AppwriteMockService(), mockPreferences)),
            validatePasswordUseCase = ValidatePasswordUseCase(),
            validateEmailUseCase = ValidateEmailUseCase(),
            userDataRepository = UserDataRepositoryImpl(mockPreferences)
        )
    }

    @Test
    fun `Successful email validation`() {
        registerViewModel.onEvent(RegisterUiEvent.EmailChanged("user@email.com"))
        Assert.assertTrue(registerViewModel.uiState.value.isEmailValid)
    }

    @Test
    fun `Failed email validation, not correct format`() {
        registerViewModel.onEvent(RegisterUiEvent.EmailChanged("useremail.com"))
        Assert.assertFalse(registerViewModel.uiState.value.isEmailValid)
    }

    @Test
    fun `Failed email validation, empty email`() {
        registerViewModel.onEvent(RegisterUiEvent.EmailChanged(""))
        Assert.assertFalse(registerViewModel.uiState.value.isEmailValid)
    }

    @Test
    fun `Successful password validation`() {
        registerViewModel.onEvent(RegisterUiEvent.PasswordChanged("bigpassword1A"))
        Assert.assertTrue(registerViewModel.uiState.value.isPasswordValid)
    }

    @Test
    fun `Failed password validation, password too short`() {
        registerViewModel.onEvent(RegisterUiEvent.PasswordChanged("bigd1A"))
        Assert.assertFalse(registerViewModel.uiState.value.isPasswordValid)
    }

    @Test
    fun `Failed password validation, password does not contain a uppercase letter`() {
        registerViewModel.onEvent(RegisterUiEvent.PasswordChanged("bigpassword1"))
        Assert.assertFalse(registerViewModel.uiState.value.isPasswordValid)
    }

    @Test
    fun `Failed password validation, password does not contain a number`() {
        registerViewModel.onEvent(RegisterUiEvent.PasswordChanged("bigpasswordA"))
        Assert.assertFalse(registerViewModel.uiState.value.isPasswordValid)
    }

    @Test
    fun `Failed password validation, password does not contain a lowercase letter`() {
        registerViewModel.onEvent(RegisterUiEvent.PasswordChanged("BIGPASSWORD1"))
        Assert.assertFalse(registerViewModel.uiState.value.isPasswordValid)
    }

    @Test
    fun `Failed password validation, empty password`() {
        registerViewModel.onEvent(RegisterUiEvent.PasswordChanged(""))
        Assert.assertFalse(registerViewModel.uiState.value.isPasswordValid)
    }

    @Test
    fun `Successful retyped password validation`() {
        registerViewModel.onEvent(RegisterUiEvent.PasswordChanged("bigpassword1A"))
        registerViewModel.onEvent(RegisterUiEvent.RetypedPasswordChanged("bigpassword1A"))
        Assert.assertTrue(registerViewModel.uiState.value.isRetypedPasswordValid)
    }

    @Test
    fun `Failed retyped password validation, retyped password does not match`() {
        registerViewModel.onEvent(RegisterUiEvent.PasswordChanged("bigpassword1A"))
        registerViewModel.onEvent(RegisterUiEvent.RetypedPasswordChanged("bigpassword2B"))
        Assert.assertFalse(registerViewModel.uiState.value.isRetypedPasswordValid)
    }

    @Test
    fun `Failed retyped password validation, empty retyped password`() {
        registerViewModel.onEvent(RegisterUiEvent.RetypedPasswordChanged(""))
        Assert.assertFalse(registerViewModel.uiState.value.isRetypedPasswordValid)
    }

    @Test
    fun `Register button enabled`() {
        registerViewModel.onEvent(RegisterUiEvent.EmailChanged("newuser@email.com"))
        registerViewModel.onEvent(RegisterUiEvent.PasswordChanged("bigpassword1A"))
        registerViewModel.onEvent(RegisterUiEvent.RetypedPasswordChanged("bigpassword1A"))

        Assert.assertTrue(registerViewModel.uiState.value.isRegisterButtonEnabled)
    }

    @Test
    fun `Register button disabled`() {
        registerViewModel.onEvent(RegisterUiEvent.EmailChanged("newuser@email.com"))
        registerViewModel.onEvent(RegisterUiEvent.PasswordChanged("bigpassword1"))
        registerViewModel.onEvent(RegisterUiEvent.RetypedPasswordChanged("bigpassword1A"))

        Assert.assertFalse(registerViewModel.uiState.value.isRegisterButtonEnabled)
    }

}