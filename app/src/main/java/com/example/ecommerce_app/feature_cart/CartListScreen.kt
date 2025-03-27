package com.example.ecommerce_app.feature_cart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ecommerce_app.core.common.Appbar
import com.example.ecommerce_app.core.common.EmptyMessage
import com.example.ecommerce_app.feature_cart.presentation.CartItemRow
import com.example.ecommerce_app.feature_cart.presentation.CartTotal
import com.example.ecommerce_app.feature_cart.viewmodel.CartViewmodel

@Composable
fun CartListScreen(
    navController: NavController,
    cartViewModel: CartViewmodel = hiltViewModel()
) {
    val cartItems by cartViewModel.carts.collectAsState()
    val isLoading by cartViewModel.isLoading.collectAsState()
    val cartCount by cartViewModel.carts.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        cartViewModel.getCarts()
    }

    Scaffold(
        topBar = { Appbar("My Bag", navController = navController, cartCount = cartCount.size,onSearchQueryChanged = { searchQuery = it }) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.fillMaxSize())
            } else if (cartItems.isEmpty()) {
                EmptyMessage(
                    message = "No Carts are available!",
                    fontSize = 18,
                    fontWeight = FontWeight.Medium,
                    color = Color.Red
                )
            } else {
                LazyColumn {
                    items(cartItems) { cartItem ->
                        CartItemRow(cartItem, cartViewModel)
                    }
                }
                CartTotal(navController, cartViewModel)
            }
        }
    }
}

