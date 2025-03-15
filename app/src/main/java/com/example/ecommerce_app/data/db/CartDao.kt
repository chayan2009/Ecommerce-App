package com.example.ecommerce_app.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.ecommerce_app.data.source.dto.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM cart")
    fun getCarts(): Flow<List<CartEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItems(cartItems: List<CartEntity>)

    @Delete
    suspend fun deleteCartItem(cartItem: CartEntity)

    @Query("DELETE FROM cart WHERE id = :cartItemId")
    suspend fun deleteCartItemById(cartItemId: Int)

    @Query("DELETE FROM cart")
    suspend fun clearCart()
}
