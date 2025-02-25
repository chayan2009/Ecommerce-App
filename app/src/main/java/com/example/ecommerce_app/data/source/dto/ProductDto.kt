package com.example.ecommerce_app.data.source.dto

import com.example.ecommerce_app.domain.model.Product

data class ProductDto(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val image: String
) {
    fun toDomain(): Product = Product(id, title, price, description, image)
}
