package com.example.ecommerce_app.feature_product.presentation.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.navigation.NavHostController

@Composable
fun ProductDetailsScreen(productId: NavHostController, productId1: Int) {
    Text(text = "Product ID: $productId")

}
