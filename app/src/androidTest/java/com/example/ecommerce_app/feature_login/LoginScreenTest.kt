package com.example.ecommerce_app.feature_login

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ecommerce_app.MainActivity
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

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
    fun loginScreen_displaysUIElements() {
        composeTestRule.waitForIdle()

        println(composeTestRule.onRoot().printToString())

        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithTag("UsernameField").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithTag("UsernameField").assertExists()
        composeTestRule.onNodeWithTag("PasswordField").assertExists()
        composeTestRule.onNodeWithTag("Login").assertExists()
    }

    @Test
    fun loginScreen_displaysLoginFailedMessage() {
        composeTestRule.waitForIdle()

        println(composeTestRule.onRoot().printToString())

        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithTag("UsernameField").fetchSemanticsNodes().isNotEmpty()
        }
        // Enter valid username and password
        composeTestRule.onNodeWithTag("UsernameField").performTextInput("testuser")
        composeTestRule.onNodeWithTag("PasswordField").performTextInput("wrongpassword")

        // Click the login button
        composeTestRule.onNodeWithTag("Login").performClick()

        // Verify the "Login Failed!" message is displayed
        composeTestRule.onNodeWithTag("LoginFailedMessage").assertExists()
    }
}