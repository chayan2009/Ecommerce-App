package com.example.ecommerce_app.data.repository

import android.util.Log
import com.example.ecommerce_app.core.common.FavouriteMapper
import com.example.ecommerce_app.data.db.FavouriteDao
import com.example.ecommerce_app.domain.model.Favourite
import com.example.ecommerce_app.domain.repository.FavouriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow

class FavouritesRepositoryImpl (
    private val favouriteDao: FavouriteDao
) : FavouriteRepository {

    override suspend fun getFavourites(): Flow<List<Favourite>> = flow {
        val cachedCarts = favouriteDao.getFavourites().firstOrNull()?.let {
            FavouriteMapper.entityListToDomainList(it)
        } ?: emptyList()
        emit(cachedCarts)
    }

    override suspend fun insertFavouritesItem(favourite: Favourite) {
        val cartEntity = FavouriteMapper.domainToEntity(favourite)
        Log.d("CartDebug", "Inserting cart item: $cartEntity")
        favouriteDao.insertFavouriteItem(cartEntity)
    }

    override suspend fun deleteFavouritesItem(favId: Int) {
        favouriteDao.deleteFavouriteItemById(favId)    }

}
