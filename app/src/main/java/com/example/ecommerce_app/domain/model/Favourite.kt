package com.example.ecommerce_app.domain.model

data class Favourite(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
)

