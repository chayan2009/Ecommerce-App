package com.example.ecommerce_app.navigation

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.ecommerce_app.MainActivity
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class AppNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testSplashScreenNavigationToLoginScreen() {
        val navController = TestNavHostController(composeTestRule.activity)
        navController.navigatorProvider.addNavigator(ComposeNavigator())

        composeTestRule.setContent {
            AppNavigation(navController = navController)
        }

        // Verify starting destination is SplashScreen
        composeTestRule.runOnIdle {
            assertEquals(Screen.SplashScreen.route, navController.currentBackStackEntry?.destination?.route)
        }

        // Advance the main clock to simulate the delay in SplashScreen (2000ms)
        composeTestRule.mainClock.advanceTimeBy(2100)
        composeTestRule.waitForIdle()

        // Verify that we navigate to LoginScreen after the delay
        composeTestRule.runOnIdle {
            assertEquals(Screen.LoginScreen.route, navController.currentBackStackEntry?.destination?.route)
        }
    }
}
