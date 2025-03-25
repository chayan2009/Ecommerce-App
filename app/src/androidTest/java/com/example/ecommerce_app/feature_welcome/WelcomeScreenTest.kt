package com.example.ecommerce_app.feature_welcome

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.example.clean_architecture_android.ui.theme.CleanArchitectureAndroidTheme
import com.example.ecommerce_app.HiltTestActivity
import com.example.ecommerce_app.feature_login.LoginScreen
import com.example.ecommerce_app.feature_login.viewmodel.LoginViewModel
import com.example.ecommerce_app.feature_signup.SignUpViewModel
import com.example.ecommerce_app.feature_signup.presentation.SignUpScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
@HiltAndroidTest
class WelcomeScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        hiltRule.inject()

        composeTestRule.setContent {
            CleanArchitectureAndroidTheme {
                navController = TestNavHostController(composeTestRule.activity).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }

                NavHost(
                    navController = navController,
                    startDestination = Screen.WelcomeScreen.route
                ) {
                    composable(Screen.WelcomeScreen.route) { WelcomeScreen(navController) }
                    composable(Screen.LoginScreen.route) {
                        val viewModel = hiltViewModel<LoginViewModel>()
                        LoginScreen(navController, viewModel)
                    }
                    composable(Screen.SignUpScreen.route) {
                        val viewModel = hiltViewModel<SignUpViewModel>()
                        SignUpScreen(navController, viewModel)
                    }
                }
            }
        }
    }

    @Test
    fun click_signUpButton_navigatesToSignUpScreen() {
        composeTestRule.onNodeWithTag("SignUpButton").performClick()
        composeTestRule.waitForIdle()
        assert(navController.currentDestination?.route == Screen.SignUpScreen.route)
    }
}
