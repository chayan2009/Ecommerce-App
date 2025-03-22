package com.example.ecommerce_app.feature_splash

import androidx.activity.ComponentActivity
import androidx.compose.runtime.*
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class SplashScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testSplashScreenNavigatesToLogin() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            var loginState by remember { mutableStateOf(false) } // Simulate UI state

            LaunchedEffect(Unit) {
                // Simulate a delay or condition to navigate to LoginScreen
                delay(2000) // Simulate a 2-second delay
                loginState = false // Simulate user is not logged in
                navController.navigate("login") // Navigate to LoginScreen
            }

           // SplashScreen(navController = navController, loginState = loginState)
        }

        // Assert splash screen initially exists
        composeTestRule.onNodeWithText("Welcome to Ecommerce App").assertExists()

        // Wait for navigation to LoginScreen
        composeTestRule.waitUntil(timeoutMillis = 3000) {
            composeTestRule.onAllNodesWithText("Login Screen").fetchSemanticsNodes().isNotEmpty()
        }

        // Verify Login Screen appears
        composeTestRule.onNodeWithText("Login Screen").assertExists()
    }
}