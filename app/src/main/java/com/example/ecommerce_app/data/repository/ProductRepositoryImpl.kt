package com.example.ecommerce_app.data.repository


import com.example.ecommerce_app.data.source.api.ProductApi
import com.example.ecommerce_app.domain.model.Product
import com.example.ecommerce_app.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApi
) : ProductRepository {

    override suspend fun getProducts(): Flow<List<Product>> = flow {
        try {
            val response = api.getProducts()
            emit(response.map { it.toDomain() })
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

}
