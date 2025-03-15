package com.example.ecommerce_app.domain.repository

import com.example.ecommerce_app.domain.model.Cart
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun getCarts(): Flow<List<Cart>>
    suspend fun insertCartItem(cart: Cart)
    suspend fun deleteCartItem(cartId: Int)
}
