package com.example.ecommerce_app.domain.usecase

import com.example.ecommerce_app.domain.model.Favourite
import com.example.ecommerce_app.domain.repository.FavouriteRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class GetFavouritesUseCaseTest {

    private lateinit var useCase: GetFavouritesUseCase
    private lateinit var mockRepository: FavouriteRepository

    @Before
    fun setUp() {
        mockRepository = mock(FavouriteRepository::class.java)
        useCase = GetFavouritesUseCase(mockRepository)
    }

    @Test
    fun getFavourites_ReturnsFavouritesFromRepository() = runTest {
        val testFavourites = listOf(
            Favourite(1, "Backpack", 109.95, "Great backpack", "men's clothing", "image1.jpg"),
            Favourite(2, "Backpack", 109.95, "Great backpack", "men's clothing", "image1.jpg")
        )
        `when`(mockRepository.getFavourites()).thenReturn(flowOf(testFavourites))

        val result = useCase.getFavourites().firstOrNull()
        assertEquals(testFavourites, result)
    }

    @Test
    fun addFavItem_DelegatesToRepositoryWithCorrectItem() = runTest {
        val newFavourite = Favourite(1, "Backpack", 109.95, "Great backpack", "men's clothing", "image1.jpg")
        useCase.addFavItem(newFavourite)
        verify(mockRepository).insertFavouritesItem(newFavourite)
    }

    @Test
    fun removeFavItem_DelegatesToRepositoryWithCorrectId() = runTest {
        val favIdToRemove = 4
        useCase.removeFavItem(favIdToRemove)
        verify(mockRepository).deleteFavouritesItem(favIdToRemove)
    }

    @Test
    fun getFavourites_ReturnsEmptyListWhenRepositoryIsEmpty() = runTest {
        `when`(mockRepository.getFavourites()).thenReturn(flowOf(emptyList()))
        val result = useCase.getFavourites().firstOrNull()
        assertEquals(emptyList<Favourite>(), result)
    }
}
