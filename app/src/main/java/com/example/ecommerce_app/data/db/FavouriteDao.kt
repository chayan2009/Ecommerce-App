package com.example.ecommerce_app.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.ecommerce_app.data.source.dto.CartEntity
import com.example.ecommerce_app.data.source.dto.FavouriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {

    @Query("SELECT * FROM favourite")
    fun getFavourites(): Flow<List<FavouriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteItem(cartItem: FavouriteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteItems(cartItems: List<FavouriteEntity>)

    @Delete
    suspend fun deleteFavouriteItem(cartItem: FavouriteEntity)

    @Query("DELETE FROM favourite WHERE id = :favItemId")
    suspend fun deleteFavouriteItemById(favItemId: Int)

    @Query("DELETE FROM favourite")
    suspend fun clearFavourite()
}
