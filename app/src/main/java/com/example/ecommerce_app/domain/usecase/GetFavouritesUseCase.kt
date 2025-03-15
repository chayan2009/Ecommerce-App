package com.example.ecommerce_app.domain.usecase

import com.example.ecommerce_app.domain.model.Cart
import com.example.ecommerce_app.domain.model.Favourite
import com.example.ecommerce_app.domain.repository.CartRepository
import com.example.ecommerce_app.domain.repository.FavouriteRepository
import kotlinx.coroutines.flow.Flow

class GetFavouritesUseCase(
    private val repository: FavouriteRepository
) {
    suspend fun getFavourites(): Flow<List<Favourite>> = repository.getFavourites()

    suspend fun addFavItem(favourite: Favourite) {
        repository.insertFavouritesItem(favourite)
    }

    suspend fun removeFavItem(favId: Int) {
        repository.deleteFavouritesItem(favId)
    }
}