package com.example.ecommerce_app.wishlist.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.ecommerce_app.domain.model.Cart
import com.example.ecommerce_app.domain.model.Favourite
import com.example.ecommerce_app.domain.usecase.GetCartsUseCase
import com.example.ecommerce_app.domain.usecase.GetFavouritesUseCase
import junit.framework.TestCase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.*
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class FavouriteScreenViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: FavouriteScreenViewModel
    private lateinit var mockFavouritesUseCase: GetFavouritesUseCase
    private lateinit var mockCartsUseCase: GetCartsUseCase
    private val testDispatcher = StandardTestDispatcher()

    private val sampleFavourites = listOf(
        Favourite(1, "Backpack", 109.95, "Great backpack", "men's clothing", "image1.jpg"),
        Favourite(2, "T-Shirt", 22.3, "Comfortable shirt", "men's clothing", "image2.jpg")
    )

    @Before
    fun setupViewModel() = runBlocking {
        Dispatchers.setMain(testDispatcher)
        mockFavouritesUseCase = mock(GetFavouritesUseCase::class.java)
        mockCartsUseCase = mock(GetCartsUseCase::class.java)
        `when`(mockFavouritesUseCase.getFavourites()).thenReturn(flowOf(sampleFavourites))
        viewModel = FavouriteScreenViewModel(mockFavouritesUseCase, mockCartsUseCase)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
        reset(mockFavouritesUseCase, mockCartsUseCase)
    }

    @Test
    fun testLoadFavourites_UpdatesStateCorrectly() = runTest(testDispatcher) {
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(sampleFavourites, viewModel.favourites.value)
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun testFetchFavourites_ShowsLoadingInitially() = runTest(testDispatcher) {
        val loadingFlow = MutableStateFlow<List<Favourite>>(emptyList())
        `when`(mockFavouritesUseCase.getFavourites()).thenReturn(loadingFlow)
        val testViewModel = FavouriteScreenViewModel(mockFavouritesUseCase, mockCartsUseCase)
        assertTrue(testViewModel.isLoading.value)
    }

    @Test
    fun testFetchFavourites_ReturnsEmptyListOnNoData() = runTest(testDispatcher) {
        `when`(mockFavouritesUseCase.getFavourites()).thenReturn(flowOf(emptyList()))
        viewModel = FavouriteScreenViewModel(mockFavouritesUseCase, mockCartsUseCase)
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(viewModel.favourites.value.isEmpty())
    }

    @Test
    fun testFetchFavourites_HandlesFailureGracefully() = runTest(testDispatcher) {
        `when`(mockFavouritesUseCase.getFavourites()).thenReturn(flow { throw RuntimeException("Data fetch error") })
        viewModel = FavouriteScreenViewModel(mockFavouritesUseCase, mockCartsUseCase)
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(viewModel.favourites.value.isEmpty())
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun testAddToCart_CallsUseCaseWithCorrectItem() = runTest(testDispatcher) {
        val cartItem = Cart(1, "Backpack", 109.95, "test", "ss", "image1.jpg", 1)
        viewModel.addToCart(cartItem)
        testDispatcher.scheduler.advanceUntilIdle()
        verify(mockCartsUseCase).addCartItem(cartItem)
    }

    @Test
    fun testToggleFavourite_AddsItemWhenEnabled() = runTest(testDispatcher) {
        val newFavourite = sampleFavourites[0]
        `when`(mockFavouritesUseCase.getFavourites()).thenReturn(flowOf(emptyList()))
        viewModel.toggleFavorite(newFavourite, isFavorite = true)
        testDispatcher.scheduler.advanceUntilIdle()
        verify(mockFavouritesUseCase).addFavItem(newFavourite)
        assertEquals(listOf(newFavourite), viewModel.favourites.value)
    }

    @Test
    fun testToggleFavourite_RemovesItemWhenDisabled() = runTest(testDispatcher) {
        val existingFavourite = sampleFavourites[0]
        `when`(mockFavouritesUseCase.getFavourites()).thenReturn(flowOf(sampleFavourites))
        viewModel.toggleFavorite(existingFavourite, isFavorite = false)
        testDispatcher.scheduler.advanceUntilIdle()
        verify(mockFavouritesUseCase).removeFavItem(existingFavourite.id)
        assertEquals(sampleFavourites.size - 1, viewModel.favourites.value.size)
    }

    @Test
    fun testRemoveFavourite_HandlesFailureGracefully() = runTest(testDispatcher) {
        val existingFavourite = sampleFavourites[0]
        `when`(mockFavouritesUseCase.removeFavItem(existingFavourite.id)).thenThrow(RuntimeException("Remove error"))
        try {
            viewModel.toggleFavorite(existingFavourite, isFavorite = false)
            testDispatcher.scheduler.advanceUntilIdle()
            fail("Expected exception not thrown")
        } catch (e: RuntimeException) {
            assertEquals("Remove error", e.message)
        }
    }

    @Test
    fun testAddToCart_HandlesFailureGracefully() = runTest(testDispatcher) {
        val cartItem = Cart(1, "Backpack", 109.95, "test", "ss", "image1.jpg", 1)
        `when`(mockCartsUseCase.addCartItem(cartItem)).thenThrow(RuntimeException("Cart addition error"))
        try {
            viewModel.addToCart(cartItem)
            testDispatcher.scheduler.advanceUntilIdle()
            fail("Expected exception not thrown")
        } catch (e: RuntimeException) {
            assertEquals("Cart addition error", e.message)
        }
    }
}
