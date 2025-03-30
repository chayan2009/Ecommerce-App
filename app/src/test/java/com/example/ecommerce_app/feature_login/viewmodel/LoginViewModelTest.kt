package com.example.ecommerce_app.feature_login.viewmodel

import app.cash.turbine.test
import com.example.ecommerce_app.core.datastore.UserPreferences
import com.example.ecommerce_app.feature_splash.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: LoginViewModel
    private val userPreferences: UserPreferences = mockk()

    @Before
    fun setUp() {
        viewModel = LoginViewModel(userPreferences)
    }

    @Test
    fun onUsernameChange_updatesUsernameAndClearsError() = runTest {
        // Given
        val testUsername = "testuser"

        // When
        viewModel.onUsernameChange(testUsername)

        // Then
        viewModel.username.test {
            assertEquals(testUsername, awaitItem())
        }
        viewModel.usernameError.test {
            assertNull(awaitItem())
        }
    }

    @Test
    fun onPasswordChange_updatesPasswordAndClearsError() = runTest {
        // Given
        val testPassword = "testpass123"

        // When
        viewModel.onPasswordChange(testPassword)

        // Then
        viewModel.password.test {
            assertEquals(testPassword, awaitItem())
        }
        viewModel.passwordError.test {
            assertNull(awaitItem())
        }
    }

    @Test
    fun login_withInvalidCredentials_setsLoginStateToFalse() = runTest {
        // Given
        val username = "wronguser"
        val password = "wrongpass"
        coEvery { userPreferences.getSavedUsername() } returns flowOf(null)
        coEvery { userPreferences.getSavedPassword() } returns flowOf(null)

        // When
        viewModel.login(username, password)

        // Then
        viewModel.loginState.test {
            assertEquals(false, awaitItem())
        }
    }

    @Test
    fun login_success_savesCredentials() = runTest {
        // Given
        val username = "chayan"
        val password = "chayan123"
        coEvery { userPreferences.getSavedUsername() } returns flowOf(null)
        coEvery { userPreferences.getSavedPassword() } returns flowOf(null)
        coEvery { userPreferences.saveUserCredentials(any(), any()) } returns Unit
        coEvery { userPreferences.setAutoLoginEnabled(true) } returns Unit

        // When
        viewModel.login(username, password)

        // Then
        coVerify(exactly = 1) {
            userPreferences.saveUserCredentials(username, password)
            userPreferences.setAutoLoginEnabled(true)
        }
    }

    @Test
    fun logout_clearsCredentialsAndSetsLoginState() = runTest {
        // Given
        coEvery { userPreferences.clearCredentials() } returns Unit

        // When
        viewModel.logout()

        // Then
        viewModel.loginState.test {
            assertEquals(false, awaitItem())
        }
        coVerify(exactly = 1) { userPreferences.clearCredentials() }
    }
}