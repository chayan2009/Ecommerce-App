package com.example.ecommerce_app.feature_welcome

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ecommerce_app.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class WelcomeScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        hiltRule.inject()
        navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
    }

    @Test
    fun welcomeScreen_displaysCorrectly() {
        composeTestRule.onNodeWithText("Welcome to Ecommerce App")
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun welcomeScreen_displaysUIElements() {
        composeTestRule.onNodeWithTag("SignInButton")
            .assertExists()
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag("SignUpButton")
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun clickingSignInButton_navigatesToLoginScreen() {
        // First verify button exists
        composeTestRule.onNodeWithTag("SignInButton")
            .assertExists()
            .assertIsDisplayed()
            .performClick()

        // Verify navigation
        assertEquals(Screen.LoginScreen.route, navController.currentBackStackEntry?.destination?.route)
    }

    @Test
    fun clickingSignUpButton_navigatesToSignUpScreen() {
        // First verify button exists
        composeTestRule.onNodeWithTag("SignUpButton")
            .assertExists()
            .assertIsDisplayed()
            .performClick()

        // Verify navigation
        assertEquals(Screen.SignUpScreen.route, navController.currentBackStackEntry?.destination?.route)
    }
}