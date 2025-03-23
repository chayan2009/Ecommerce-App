package com.example.ecommerce_app.feature_login.viewmodel

import app.cash.turbine.test
import com.example.ecommerce_app.core.datastore.UserPreferences
import com.example.ecommerce_app.feature_splash.MainDispatcherRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule() // Custom rule for coroutines

    private lateinit var viewModel: LoginViewModel
    private val userPreferences: UserPreferences = mockk(relaxed = true) // Mock UserPreferences

    @Before
    fun setup() {
        viewModel = LoginViewModel(userPreferences)
    }

    @Test
    fun `onUsernameChange updates username state`() = runTest {
        viewModel.onUsernameChange("testUser")

        viewModel.username.test {
            assertEquals("testUser", awaitItem())
        }
    }

    @Test
    fun `onPasswordChange updates password state`() = runTest {
        viewModel.onPasswordChange("testPass")

        viewModel.password.test {
            assertEquals("testPass", awaitItem())
        }
    }

    @Test
    fun `login with correct credentials updates loginState to true`() = runTest {
        // Arrange: Mock suspend functions properly
        coEvery { userPreferences.saveUserCredentials(any(), any()) } just runs
        coEvery { userPreferences.setAutoLoginEnabled(any()) } just runs

        // Act: Call login
        viewModel.login(username = "chayan", password = "chayan123")

        // Assert: Check if loginState is updated
        viewModel.loginState.test {
            assertEquals(true, awaitItem())
        }

        // Verify that user credentials are saved
        coVerify { userPreferences.saveUserCredentials("chayan", "chayan123") }
        coVerify { userPreferences.setAutoLoginEnabled(true) }
    }

    @Test
    fun `login with incorrect credentials updates loginState to false`() = runTest {
        viewModel.login(username = "wrongUser", password = "wrongPass")

        viewModel.loginState.test {
            assertEquals(false, awaitItem()) // Login should fail
        }
    }

    @Test
    fun `logout clears credentials and updates loginState to false`() = runTest {
        // Arrange: Mock suspend function
        coEvery { userPreferences.clearCredentials() } just runs

        // Act: Call logout
        viewModel.logout()

        // Assert: Check login state
        viewModel.loginState.test {
            assertEquals(false, awaitItem()) // Login state should be false
        }

        // Verify clearing credentials
        coVerify { userPreferences.clearCredentials() }
    }
}
