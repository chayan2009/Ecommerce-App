package com.example.ecommerce_app.feature_cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ecommerce_app.core.common.Appbar
import com.example.ecommerce_app.feature_cart.presentation.CartItemList
import com.example.ecommerce_app.feature_cart.presentation.CartTotal
import com.example.ecommerce_app.feature_cart.presentation.EmptyCartMessage
import com.example.ecommerce_app.feature_cart.viewmodel.CartViewmodel

@Composable
fun CartListScreen(
    navController: NavController,
    cartViewModel: CartViewmodel = hiltViewModel()
) {
    val cartItems by cartViewModel.carts.collectAsState()

    Scaffold(
        topBar = { Appbar("My Bag") }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues).verticalScroll(rememberScrollState()),
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (cartItems.isEmpty()) {
                    EmptyCartMessage()
                } else {
                    CartItemList(cartItems, cartViewModel)
                }
            }

            CartTotal(navController, cartViewModel)
        }
    }
}

