package com.example.ecommerce_app.data.source.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite")
data class FavouriteEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
)

