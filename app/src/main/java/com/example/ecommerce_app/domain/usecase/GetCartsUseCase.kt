package com.example.ecommerce_app.domain.usecase

import com.example.ecommerce_app.domain.model.Cart
import com.example.ecommerce_app.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow

class GetCartsUseCase(
    private val repository: CartRepository
) {
    suspend fun getCarts(): Flow<List<Cart>> = repository.getCarts()

    suspend fun addCartItem(cart: Cart) {
        repository.insertCartItem(cart)
    }

    suspend fun removeCartItem(cartId: Int) {
        repository.deleteCartItem(cartId)
    }
}