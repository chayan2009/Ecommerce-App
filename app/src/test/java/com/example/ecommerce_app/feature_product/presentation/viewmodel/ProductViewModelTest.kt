package com.example.ecommerce_app.feature_product.presentation.viewmodel

import com.example.ecommerce_app.data.source.dto.Rating
import com.example.ecommerce_app.domain.model.Cart
import com.example.ecommerce_app.domain.model.Favourite
import com.example.ecommerce_app.domain.model.Product
import com.example.ecommerce_app.domain.usecase.GetCartsUseCase
import com.example.ecommerce_app.domain.usecase.GetFavouritesUseCase
import com.example.ecommerce_app.domain.usecase.GetProductsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
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
class ProductViewModelTest {

    private lateinit var viewModel: ProductViewModel
    private val getProductsUseCase: GetProductsUseCase = mockk()
    private val getCartsUseCase: GetCartsUseCase = mockk()
    private val getFavouritesUseCase: GetFavouritesUseCase = mockk()

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
            title = "Laptop",
            price = 1299.99,
            description = "Powerful gaming laptop",
            category = "Electronics",
            image = "https://example.com/laptop.jpg",
            rating = Rating(rate = 4.7, count = 300)
        )
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        coEvery { getProductsUseCase() } returns flowOf(mockProducts)
        coEvery { getCartsUseCase.addCartItem(any()) } returns Unit
        coEvery { getFavouritesUseCase.addFavItem(any()) } returns Unit

        viewModel = ProductViewModel(getProductsUseCase, getCartsUseCase, getFavouritesUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `addToCart should call useCase with correct parameters`() = runTest {
        val cart = Cart(
            id = 1,
            title = "Smartphone",
            price = 999.99,
            description = "Latest model",
            category = "Electronics",
            image = "smartphone.jpg",
            quantity = 1
        )

        viewModel.addToCart(cart)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify(exactly = 1) { getCartsUseCase.addCartItem(cart) }
    }

    @Test
    fun `addToFavourite should call useCase with correct parameters`() = runTest {
        val favourite = Favourite(
            id = 1,
            title = "Smartphone",
            price = 999.99,
            description = "Latest model",
            category = "Electronics",
            image = "smartphone.jpg"
        )

        viewModel.addToFavourite(favourite)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify(exactly = 1) { getFavouritesUseCase.addFavItem(favourite) }
    }

    @Test
    fun `when products fetch fails, products and categories should remain empty`() = runTest {
        coEvery { getProductsUseCase() } returns flow { throw IOException("Network error") }

        val testViewModel = ProductViewModel(getProductsUseCase, getCartsUseCase, getFavouritesUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(emptyList<Product>(), testViewModel.products.value)
        assertEquals(emptyList<String>(), testViewModel.categories.value)
    }

    @Test
    fun `fetchCategories should return distinct categories`() = runTest {
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(listOf("Electronics", "Clothing"), viewModel.categories.value)
    }
}