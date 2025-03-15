package com.example.ecommerce_app.domain.repository

import com.example.ecommerce_app.domain.model.Cart
import com.example.ecommerce_app.domain.model.Favourite
import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {

    suspend fun getFavourites(): Flow<List<Favourite>>
    suspend fun insertFavouritesItem(favourite: Favourite)
    suspend fun deleteFavouritesItem(favId: Int)

}