package com.example.ecommerce_app.feature_category.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.ecommerce_app.data.source.dto.Rating
import com.example.ecommerce_app.domain.model.Cart
import com.example.ecommerce_app.domain.model.Favourite
import com.example.ecommerce_app.domain.model.Product
import com.example.ecommerce_app.domain.usecase.GetCartsUseCase
import com.example.ecommerce_app.domain.usecase.GetFavouritesUseCase
import com.example.ecommerce_app.domain.usecase.GetProductsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.reset
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class CategoryScreenViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CategoryScreenViewModel
    private lateinit var mockProductsUseCase: GetProductsUseCase
    private lateinit var mockCartsUseCase: GetCartsUseCase
    private lateinit var mockFavouritesUseCase: GetFavouritesUseCase
    private val testDispatcher = StandardTestDispatcher()

    private val testProducts = listOf(
        Product(1, "Backpack", 109.95, "Great backpack", "men's clothing", "image1.jpg", Rating(5, 1.0)),
        Product(2, "T-Shirt", 22.3, "Comfortable shirt", "men's clothing", "image2.jpg",Rating(5, 1.0)),
        Product(3, "Laptop", 699.99, "High performance", "electronics", "image3.jpg", Rating(5, 1.0))
    )

    private val testCategories = listOf("men's clothing", "electronics")

    @Before
    fun setUp() = runTest {
        Dispatchers.setMain(testDispatcher)

        mockProductsUseCase = mock(GetProductsUseCase::class.java)
        mockCartsUseCase = mock(GetCartsUseCase::class.java)
        mockFavouritesUseCase = mock(GetFavouritesUseCase::class.java)

        `when`(mockProductsUseCase()).thenReturn(flowOf(testProducts))

        viewModel = CategoryScreenViewModel(mockProductsUseCase, mockCartsUseCase, mockFavouritesUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        reset(mockProductsUseCase, mockCartsUseCase, mockFavouritesUseCase)
    }

    @Test
    fun testAddToCart_CallsUseCaseWithCorrectCartItem() = runTest(testDispatcher) {
        val testCart = Cart(1, "Backpack", 109.95, "test", "men's clothing", "image1.jpg", quantity = 1)

        viewModel.addToCart(testCart)
        testDispatcher.scheduler.advanceUntilIdle()

        verify(mockCartsUseCase).addCartItem(testCart)
    }

    @Test
    fun testAddToFavourite_CallsUseCaseWithCorrectFavouriteItem() = runTest(testDispatcher) {
        val testFavourite = Favourite(1, "Backpack", 109.95, "test", "men's clothing", "image1.jpg")

        viewModel.addToFavourite(testFavourite)
        testDispatcher.scheduler.advanceUntilIdle()

        verify(mockFavouritesUseCase).addFavItem(testFavourite)
    }

    @Test
    fun testAddToCart_HandlesFailureGracefully() = runTest(testDispatcher) {
        val testCart = Cart(1, "Backpack", 109.95, "test", "men's clothing", "image1.jpg", quantity = 1)

        `when`(mockCartsUseCase.addCartItem(testCart)).thenThrow(RuntimeException("Error adding to cart"))

        try {
            viewModel.addToCart(testCart)
            testDispatcher.scheduler.advanceUntilIdle()
        } catch (e: RuntimeException) {
            assertEquals("Error adding to cart", e.message)
        }
    }

    @Test
    fun testAddToFavourite_HandlesFailureGracefully() = runTest(testDispatcher) {
        val testFavourite = Favourite(1, "Backpack", 109.95, "test", "men's clothing", "image1.jpg")

        `when`(mockFavouritesUseCase.addFavItem(testFavourite)).thenThrow(RuntimeException("Error adding to favourites"))

        try {
            viewModel.addToFavourite(testFavourite)
            testDispatcher.scheduler.advanceUntilIdle()
        } catch (e: RuntimeException) {
            assertEquals("Error adding to favourites", e.message)
        }
    }
}
