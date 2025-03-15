package com.example.ecommerce_app.data.repository

import android.util.Log
import com.example.ecommerce_app.core.common.CartMapper
import com.example.ecommerce_app.data.db.CartDao
import com.example.ecommerce_app.domain.model.Cart
import com.example.ecommerce_app.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow

class CartRepositoryImpl (
    private val cartDao: CartDao
) : CartRepository {

    override suspend fun getCarts(): Flow<List<Cart>> = flow {
        val cachedCarts = cartDao.getCarts().firstOrNull()?.let {
            CartMapper.entityListToDomainList(it)
        } ?: emptyList()
        emit(cachedCarts)
    }

    override suspend fun insertCartItem(cart: Cart) {
        val cartEntity = CartMapper.domainToEntity(cart)
        Log.d("CartDebug", "Inserting cart item: $cartEntity")
        cartDao.insertCartItem(cartEntity)
    }

    override suspend fun deleteCartItem(cartId: Int) {
        cartDao.deleteCartItemById(cartId)
    }

}
