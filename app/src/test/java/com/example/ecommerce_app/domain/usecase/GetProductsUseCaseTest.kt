package com.example.ecommerce_app.domain.usecase

import com.example.ecommerce_app.data.source.dto.Rating
import com.example.ecommerce_app.domain.model.Product
import com.example.ecommerce_app.domain.repository.ProductRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetProductsUseCaseTest {

    private val repository = mock<ProductRepository>()
    private val useCase = GetProductsUseCase(repository)

    @Test
    fun invoke_callsRepositoryGetProducts() = runTest {
        // Given
        val testProduct = Product(
            id = 10,
            title = "SanDisk SSD 1TB",
            price = 109.0,
            description = "High-speed SSD drive",
            category = "electronics",
            image = "sandisk-ssd.jpg",
            rating = Rating(120, 1.0)
        )

        whenever(repository.getProducts()).thenReturn(flowOf(listOf(testProduct)))
        useCase().collect {
            verify(repository).getProducts()
        }
    }
}