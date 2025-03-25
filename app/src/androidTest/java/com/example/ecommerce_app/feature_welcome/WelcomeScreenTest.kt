package com.example.ecommerce_app.feature_welcome

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.testing.TestNavHostController
import com.example.ecommerce_app.MainActivity
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
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun welcomeScreen_displaysTextAndButtons() {
        composeTestRule.onNodeWithText("Welcome to Ecommerce App").assertIsDisplayed()
        composeTestRule.onNodeWithTag("SignInButton").assertIsDisplayed()
        composeTestRule.onNodeWithTag("SignUpButton").assertIsDisplayed()
    }

    @Test
    fun clickingSignInButton_navigatesToLoginScreen() {
        composeTestRule.onNodeWithTag("SignInButton").performClick()
    }

    @Test
    fun clickingSignUpButton_navigatesToSignUpScreen() {
        composeTestRule.onNodeWithTag("SignUpButton").performClick()
    }
}
