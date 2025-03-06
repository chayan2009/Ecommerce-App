package com.example.ecommerce_app.feature_cart.model

data class CartItem(
    val id: String,
    val title: String,
    val image: String,
    val price: Double,
    var quantity: Int
)
