package com.example.ecommerce_app.feature_account.myorder.presentation

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ecommerce_app.MainActivity
import com.example.ecommerce_app.feature_account.history.OrderStatus
import com.example.ecommerce_app.feature_account.history.model.Order
import com.example.ecommerce_app.feature_account.history.viewmodel.OrdersScreenViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when` as whenever

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class OrdersScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

//    private lateinit var ordersScreenViewModel: OrdersScreenViewModel
//    private lateinit var navController: NavController

    @Before
    fun setup() {
        hiltRule.inject()

        // Mock ViewModel and NavController
     //   ordersScreenViewModel = mock()
       // navController = mock()

        // Provide Fake Order Data
        val fakeOrders = listOf(
            Order(id = "101", date = "2025-03-23", amount = "$50", status = OrderStatus.DELIVERED),
            Order(id = "102", date = "2025-03-22", amount = "$30", status = OrderStatus.PROCESSING),
            Order(id = "103", date = "2025-03-21", amount = "$70", status = OrderStatus.COMPLETED)
        )

        // Stub the ViewModel's behavior
       // whenever(ordersScreenViewModel.myOrderList).thenReturn(MutableStateFlow(fakeOrders))
    }

    @Test
    fun ordersScreen_displaysTabsAndOrders() {
        // Set the content of the Compose test rule
//        composeTestRule.setContent {
//            OrdersScreen(navController, ordersScreenViewModel)
//        }

        // Wait for the UI to load
        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithText("Delivered").fetchSemanticsNodes().isNotEmpty()
        }

        // Check if Tabs Exist
        composeTestRule.onNodeWithText("Delivered").assertExists()
        composeTestRule.onNodeWithText("Processing").assertExists()
        composeTestRule.onNodeWithText("Completed").assertExists()

        // Verify Delivered Orders are Shown Initially
        composeTestRule.onNodeWithText("Order ID: 101").assertExists()
        composeTestRule.onNodeWithText("Date: 2025-03-23").assertExists()
        composeTestRule.onNodeWithText("Amount: $50").assertExists()

        // Switch to Processing Tab
        composeTestRule.onNodeWithText("Processing").performClick()
        composeTestRule.onNodeWithText("Order ID: 102").assertExists()

        // Switch to Completed Tab
        composeTestRule.onNodeWithText("Completed").performClick()
        composeTestRule.onNodeWithText("Order ID: 103").assertExists()
    }
}