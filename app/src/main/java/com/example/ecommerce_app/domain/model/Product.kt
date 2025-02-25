package com.example.ecommerce_app.domain.model

data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val image: String
)
