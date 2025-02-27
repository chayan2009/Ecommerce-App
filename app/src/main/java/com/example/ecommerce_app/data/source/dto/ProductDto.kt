package com.example.ecommerce_app.data.source.dto

import com.example.ecommerce_app.domain.model.Product

data class ProductDto(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String
) {

    fun toDomain(): Product {
        return Product(
            id = id,
            title = title,
            price = price,
            description = description,
            category = category,
            image = image
        )
    }

}


