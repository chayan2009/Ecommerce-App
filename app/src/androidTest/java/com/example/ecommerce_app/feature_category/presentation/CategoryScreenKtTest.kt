package com.example.ecommerce_app.feature_category.presentation

import CategoryScreen
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.example.ecommerce_app.data.source.dto.Rating
import com.example.ecommerce_app.domain.model.Product
import com.example.ecommerce_app.feature_category.viewmodel.CategoryScreenViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
class CategoryScreenTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController

    // Use RelaxedMockK annotation for Android tests
    @RelaxedMockK
    private lateinit var mockViewModel: CategoryScreenViewModel

    @Before
    fun setUp() {
        // Initialize mocks
       // mockk() // This initializes all @RelaxedMockK annotated properties

        hiltRule.inject()
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        // Mock ViewModel behavior
        coEvery { mockViewModel.categories } returns MutableStateFlow(listOf("Electronics", "Clothing"))
        coEvery { mockViewModel.products } returns MutableStateFlow(emptyList())
        coEvery { mockViewModel.isLoading } returns MutableStateFlow(false)
    }

    @Test
    fun categoryScreen_LoadingState_ShowsProgressIndicator() {
        // Arrange
        coEvery { mockViewModel.isLoading } returns MutableStateFlow(true)

        // Act
        composeTestRule.setContent {
            CategoryScreen(navController = navController, categoryScreenViewModel = mockViewModel)
        }

        // Assert
        composeTestRule.onNodeWithTag("LoadingIndicator").assertExists()
    }

    @Test
    fun categoryScreen_EmptyCategories_ShowsEmptyMessage() {
        // Arrange
        coEvery { mockViewModel.categories } returns MutableStateFlow(emptyList())

        // Act
        composeTestRule.setContent {
            CategoryScreen(navController = navController, categoryScreenViewModel = mockViewModel)
        }

        // Assert
        composeTestRule.onNodeWithText("No categories available!").assertExists()
    }

    @Test
    fun categoryScreen_ProductClick_NavigatesToDetails() {
        // Arrange
        val testProduct = Product(
            id = 1,
            title = "Test Product",
            price = 10.0,
            description = "Test",
            category = "Electronics",
            image = "",
            rating = Rating(5, 1.0)
        )
        coEvery { mockViewModel.products } returns MutableStateFlow(listOf(testProduct))

        // Act
        composeTestRule.setContent {
            CategoryScreen(navController = navController, categoryScreenViewModel = mockViewModel)
        }

        // Click on product
        composeTestRule.onNodeWithText("Test Product").performClick()

        // Assert navigation
        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals("product_details/1", route)
    }

    @Test
    fun categoryScreen_AddToCart_CallsViewModel() {
        // Arrange
        val testProduct = Product(
            id = 1,
            title = "Test Product",
            price = 10.0,
            description = "Test",
            category = "Electronics",
            image = "",
            rating = Rating(0, 1.00)
        )
        coEvery { mockViewModel.products } returns MutableStateFlow(listOf(testProduct))

        // Act
        composeTestRule.setContent {
            CategoryScreen(navController = navController, categoryScreenViewModel = mockViewModel)
        }

        // Click add to cart button
        composeTestRule.onNodeWithContentDescription("Add to cart").performClick()

        // Assert ViewModel call
        coVerify(exactly = 1) {
            mockViewModel.addToCart(
                match { cart ->
                    cart.id == 1 &&
                            cart.title == "Test Product" &&
                            cart.quantity == 1
                }
            )
        }
    }
}