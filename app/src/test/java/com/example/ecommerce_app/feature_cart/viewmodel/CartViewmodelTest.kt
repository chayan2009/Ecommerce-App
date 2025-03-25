package com.example.ecommerce_app.feature_cart.viewmodel

import app.cash.turbine.test
import com.example.ecommerce_app.domain.model.Cart
import com.example.ecommerce_app.domain.usecase.GetCartsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
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
import org.junit.Test

@ExperimentalCoroutinesApi
class CartViewModelTest {

    private lateinit var viewModel: CartViewmodel
    private val getCartsUseCase: GetCartsUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()

    // Mock data
    private val mockCartItems = listOf(
        Cart(
            id = 1,
            title = "Smartphone",
            price = 999.99,
            description = "Latest model",
            category = "Electronics",
            image = "smartphone.jpg",
            quantity = 1
        ),
        Cart(
            id = 2,
            title = "Laptop",
            price = 1299.99,
            description = "Powerful gaming laptop",
            category = "Electronics",
            image = "laptop.jpg",
            quantity = 2
        )
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        coEvery { getCartsUseCase.getCarts() } returns flowOf(mockCartItems)
        coEvery { getCartsUseCase.addCartItem(any()) } returns Unit
        coEvery { getCartsUseCase.removeCartItem(any()) } returns Unit

        viewModel = CartViewmodel(getCartsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `addCartItem should increase quantity for existing item`() = runTest {
        testDispatcher.scheduler.advanceUntilIdle() // Load initial data

        val existingItem = mockCartItems[0].copy(quantity = 1)
        viewModel.addCartItem(existingItem)
        testDispatcher.scheduler.advanceUntilIdle()

        // Verify quantity increased
        assertEquals(2, viewModel.carts.value.first { it.id == existingItem.id }.quantity)
        coVerify { getCartsUseCase.addCartItem(existingItem) }
    }

    @Test
    fun `addCartItem should add new item with quantity 1`() = runTest {
        testDispatcher.scheduler.advanceUntilIdle()

        val newItem = Cart(
            id = 3,
            title = "Headphones",
            price = 199.99,
            description = "Noise cancelling",
            category = "Electronics",
            image = "headphones.jpg",
            quantity = 1
        )

        viewModel.addCartItem(newItem)
        testDispatcher.scheduler.advanceUntilIdle()

        // Verify new item added
        assertEquals(3, viewModel.carts.value.size)
        assertEquals(1, viewModel.carts.value.first { it.id == 3 }.quantity)
        coVerify { getCartsUseCase.addCartItem(newItem) }
    }


    @Test
    fun `removeCartItem should remove item when quantity is 1`() = runTest {
        testDispatcher.scheduler.advanceUntilIdle()

        val itemId = 1 // Smartphone with quantity 1 initially
        viewModel.removeCartItem(itemId)
        testDispatcher.scheduler.advanceUntilIdle()

        // Verify item removed
        assertEquals(1, viewModel.carts.value.size)
        assertEquals(false, viewModel.carts.value.any { it.id == itemId })
        coVerify { getCartsUseCase.removeCartItem(itemId) }
    }

    @Test
    fun `removeCartItem should do nothing when item not found`() = runTest {
        testDispatcher.scheduler.advanceUntilIdle()

        val initialSize = viewModel.carts.value.size
        viewModel.removeCartItem(99) // Non-existent ID
        testDispatcher.scheduler.advanceUntilIdle()

        // Verify no changes
        assertEquals(initialSize, viewModel.carts.value.size)
        coVerify(exactly = 0) { getCartsUseCase.removeCartItem(any()) }
    }
}