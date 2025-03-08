package com.example.ecommerce_app.feature_cart.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_table")
data class CartItem(
    @PrimaryKey val id: Int,
    val title: String,
    val price: Double,
    val image: String
)

