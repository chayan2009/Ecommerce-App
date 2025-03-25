package com.example.ecommerce_app.feature_search.viewmodel

import app.cash.turbine.test
import com.example.ecommerce_app.data.source.dto.Rating
import com.example.ecommerce_app.domain.model.Product
import com.example.ecommerce_app.domain.usecase.GetProductsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel
    private val getProductsUseCase: GetProductsUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()

    private val mockProducts = listOf(
        Product(
            id = 1,
            title = "Smartphone",
            price = 999.99,
            description = "Latest model with high-speed performance",
            category = "Electronics",
            image = "https://example.com/smartphone.jpg",
            rating = Rating(rate = 4.5, count = 150)
        ),
        Product(
            id = 2,
            title = "T-shirt",
            price = 19.99,
            description = "Comfortable cotton T-shirt",
            category = "Clothing",
            image = "https://example.com/tshirt.jpg",
            rating = Rating(rate = 4.0, count = 200)
        ),
        Product(
            id = 3,
            title = "Smart Watch",
            price = 299.99,
            description = "Fitness tracking smart watch",
            category = "Electronics",
            image = "https://example.com/watch.jpg",
            rating = Rating(rate = 4.7, count = 300)
        )
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        coEvery { getProductsUseCase() } returns flowOf(mockProducts)
        viewModel = SearchViewModel(getProductsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should have empty products and loading true`() = runTest {
        assertEquals(emptyList<Product>(), viewModel.products.value)
        assertEquals(true, viewModel.isLoading.value)
        assertEquals("", viewModel.query.value)
        assertEquals(emptyList<Product>(), viewModel.filteredItems.value)
    }

    @Test
    fun `fetchData should load products and update loading state`() = runTest {
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(mockProducts, viewModel.products.value)
        assertEquals(false, viewModel.isLoading.value)
        assertEquals(mockProducts, viewModel.filteredItems.value) // Initially shows all products
    }

    @Test
    fun `search query should filter products correctly`() = runTest {
        testDispatcher.scheduler.advanceUntilIdle() // Wait for initial load

        viewModel.onSearchQueryChanged("smart")
        testDispatcher.scheduler.advanceUntilIdle() // Wait for debounce

        val expectedSmartProducts = mockProducts.filter { it.title.contains("smart", ignoreCase = true) }
        assertEquals(expectedSmartProducts, viewModel.filteredItems.value)

        // Test empty query shows all products
        viewModel.onSearchQueryChanged("")
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(mockProducts, viewModel.filteredItems.value)

        // Test no matches
        viewModel.onSearchQueryChanged("xyz")
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(emptyList<Product>(), viewModel.filteredItems.value)
    }

    @Test
    fun `search should debounce input`() = runTest {
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onSearchQueryChanged("s")
        viewModel.onSearchQueryChanged("sm")
        viewModel.onSearchQueryChanged("sma")
        viewModel.onSearchQueryChanged("smart")

        assertEquals(mockProducts, viewModel.filteredItems.value)

        testDispatcher.scheduler.advanceTimeBy(500)

        val expected = mockProducts.filter { it.title.contains("smart", ignoreCase = true) }
        assertEquals(expected, viewModel.filteredItems.value)
    }

    @Test
    fun `when products fetch fails, state should be handled gracefully`() = runTest {
        coEvery { getProductsUseCase() } returns flow { throw IOException("Network error") }

        val testViewModel = SearchViewModel(getProductsUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(emptyList<Product>(), testViewModel.products.value)
        assertEquals(false, testViewModel.isLoading.value)
        assertEquals(emptyList<Product>(), testViewModel.filteredItems.value)
    }

    @Test
    fun `search should be case insensitive`() = runTest {
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onSearchQueryChanged("SMART")
        testDispatcher.scheduler.advanceUntilIdle()

        val expected = mockProducts.filter { it.title.contains("smart", ignoreCase = true) }
        assertEquals(expected, viewModel.filteredItems.value)
    }
}