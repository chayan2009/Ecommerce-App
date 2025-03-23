package com.example.ecommerce_app.feature_splash

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ecommerce_app.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
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
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun splashScreen_displayedBackgroundImage() {
        composeTestRule.onNodeWithContentDescription("Splash background").assertExists()
    }

    @Test
    fun splashScreen_displaysCorrectly() {
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Welcome to Ecommerce App").assertExists()
    }
}
