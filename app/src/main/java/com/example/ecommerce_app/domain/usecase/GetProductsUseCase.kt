package com.example.ecommerce_app.domain.usecase

import com.example.ecommerce_app.domain.model.Product
import com.example.ecommerce_app.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class GetProductsUseCase(private val repository: ProductRepository) {
    suspend operator fun invoke(): Flow<List<Product>> = repository.getProducts()
}
