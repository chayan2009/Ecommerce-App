package com.example.ecommerce_app.feature_cart.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ecommerce_app.domain.model.Cart
import com.example.ecommerce_app.feature_cart.viewmodel.CartViewmodel

@Composable
fun CartItemList(cartItems: List<Cart>, cartViewModel: CartViewmodel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        cartItems.forEach { cartItem ->
            CartItemRow(cartItem, cartViewModel)
        }
    }
}