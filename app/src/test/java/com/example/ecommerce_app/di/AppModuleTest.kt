package com.example.ecommerce_app.di

import com.example.ecommerce_app.data.source.dto.Rating
import com.example.ecommerce_app.domain.model.Product
import com.example.ecommerce_app.domain.repository.ProductRepository
import com.example.ecommerce_app.domain.usecase.GetProductsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
object TestAppModule {

    @Provides
    @Singleton
    fun provideMockProductRepository(): ProductRepository {
        return mockk<ProductRepository> {
            coEvery { getProducts() } returns flowOf(emptyList())
            coEvery { getProducts() } returns flow {
                throw RuntimeException("Test error")
            }
            coEvery { getProducts() } returns flowOf(provideMockProducts())
        }
    }

    @Provides
    @Singleton
    fun provideGetProductsUseCase(repository: ProductRepository): GetProductsUseCase {
        return GetProductsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideMockProducts(): List<Product> {
        return listOf(
            Product(
                id = 10,
                title = "SanDisk SSD 1TB",
                price = 109.0,
                description = "High-speed 1TB SSD",
                category = "electronics",
                image = "sandisk-ssd.jpg",
                rating = Rating(rate = 4.0, count = 200)
            ),
            Product(
                id = 11,
                title = "SanDisk SSD 2TB",
                price = 199.0,
                description = "High-speed 2TB SSD",
                category = "electronics",
                image = "sandisk-ssd.jpg",
                rating = Rating(rate = 4.0, count = 200)
            )
        )
    }
}