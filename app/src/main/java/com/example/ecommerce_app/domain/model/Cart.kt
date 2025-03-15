package com.example.ecommerce_app.domain.model

data class Cart(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val quantity:Int
)

