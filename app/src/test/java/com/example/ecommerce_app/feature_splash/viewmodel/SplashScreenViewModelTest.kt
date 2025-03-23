package com.example.ecommerce_app.feature_splash.viewmodel
import app.cash.turbine.test
import com.example.ecommerce_app.core.datastore.UserPreferences
import com.example.ecommerce_app.feature_splash.MainDispatcherRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SplashScreenViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule() // Custom rule to handle coroutines

    private lateinit var viewModel: SplashScreenViewModel
    private val userPreferences: UserPreferences = mockk(relaxed = true) // Mock UserPreferences

    @Before
    fun setup() {
        viewModel = SplashScreenViewModel(userPreferences)
    }

    @Test
    fun `checkAutoLogin should update loginState when auto-login is enabled`() = runTest {
        // Arrange: Mock Flow emission from UserPreferences
        val autoLoginFlow = flowOf(true)
        every { userPreferences.isAutoLoginEnabled() } returns autoLoginFlow

        // Act: Initialize ViewModel
        viewModel = SplashScreenViewModel(userPreferences)

        // Assert: Verify state update using Turbine
        viewModel.loginState.test {
            assertEquals(true, awaitItem())
        }
    }

    @Test
    fun `checkAutoLogin should update loginState when auto-login is disabled`() = runTest {
        // Arrange: Mock Flow emission from UserPreferences
        val autoLoginFlow = flowOf(false)
        every { userPreferences.isAutoLoginEnabled() } returns autoLoginFlow

        // Act: Initialize ViewModel
        viewModel = SplashScreenViewModel(userPreferences)

        // Assert: Verify state update
        viewModel.loginState.test {
            assertEquals(false, awaitItem())
        }
    }
}
