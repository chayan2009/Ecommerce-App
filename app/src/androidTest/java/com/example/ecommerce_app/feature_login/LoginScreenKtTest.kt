import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ecommerce_app.feature_login.LoginScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)  // ✅ Required for Hilt Tests

    @get:Rule(order = 0)
    val composeTestRule = createAndroidComposeRule<androidx.activity.ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        hiltRule.inject()  // ✅ Inject Dependencies before running tests

        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        navController = TestNavHostController(context)

        composeTestRule.setContent {
            LoginScreen(navController)
        }
    }

    @Test
    fun enterUsernameAndPassword() {
        composeTestRule.onNodeWithText("Enter your username").performTextInput("TestUser")
        composeTestRule.onNodeWithText("Enter your Password").performTextInput("TestPass123")

        composeTestRule.onNodeWithText("Login").assertIsDisplayed()
    }

    @Test
    fun clickingLoginNavigatesToMainScreen() {
        composeTestRule.onNodeWithText("Enter your username").performTextInput("TestUser")
        composeTestRule.onNodeWithText("Enter your Password").performTextInput("TestPass123")

        composeTestRule.onNodeWithText("Login").performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Welcome, TestUser!").assertIsDisplayed()
    }
}
