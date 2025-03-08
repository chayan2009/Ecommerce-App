package com.example.ecommerce_app.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ecommerce_app.feature_cart.model.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(cartItem: CartItem)

    @Delete
    suspend fun removeFromCart(cartItem: CartItem)

    @Query("SELECT * FROM cart_table")
    fun getCartItems(): Flow<List<CartItem>>

    @Query("DELETE FROM cart_table")
    suspend fun clearCart()
}
