package com.example.ecommerce_app.feature_splash.viewmodel

import app.cash.turbine.test
import com.example.ecommerce_app.core.datastore.UserPreferences
import com.example.ecommerce_app.feature_splash.MainDispatcherRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SplashScreenViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: SplashScreenViewModel
    private val userPreferences: UserPreferences = mockk(relaxed = true)

    @Before
    fun setUp() {
        viewModel = SplashScreenViewModel(userPreferences)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun checkAutoLogin_whenEnabled_updatesLoginStateToTrue() = runTest {
        every { userPreferences.isAutoLoginEnabled() } returns flowOf(true)

        viewModel = SplashScreenViewModel(userPreferences)

        viewModel.loginState.test {
            assertEquals(true, awaitItem())
        }
    }

    @Test
    fun checkAutoLogin_whenDisabled_updatesLoginStateToFalse() = runTest {
        every { userPreferences.isAutoLoginEnabled() } returns flowOf(false)

        viewModel = SplashScreenViewModel(userPreferences)

        viewModel.loginState.test {
            assertEquals(false, awaitItem())
        }
    }
}