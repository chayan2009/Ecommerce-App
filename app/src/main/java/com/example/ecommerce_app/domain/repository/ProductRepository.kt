package com.example.ecommerce_app.domain.repository

import com.example.ecommerce_app.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProducts(): Flow<List<Product>>
}
