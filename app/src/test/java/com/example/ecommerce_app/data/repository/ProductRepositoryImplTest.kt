package com.example.ecommerce_app.data.repository

import com.example.ecommerce_app.core.common.ProductMapper
import com.example.ecommerce_app.data.db.ProductDao
import com.example.ecommerce_app.data.source.api.ProductApi
import com.example.ecommerce_app.data.source.dto.Rating
import com.example.ecommerce_app.domain.model.Product
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ProductRepositoryImplTest {

    private val api = mock<ProductApi>()
    private val productDao = mock<ProductDao>()
    private val repository = ProductRepositoryImpl(api, productDao)

    private val sampleProducts = listOf(
        Product(
            id = 1,
            title = "Fjallraven Backpack",
            price = 109.95,
            description = "Durable backpack for laptops",
            category = "men's clothing",
            image = "backpack.jpg",
            rating = Rating(12, 4.0)
        ),
        Product(
            id = 2,
            title = "Casual T-Shirt",
            price = 22.3,
            description = "Comfortable slim-fit t-shirt",
            category = "men's clothing",
            image = "tshirt.jpg",
            rating = Rating(13, 5.0)
        ),
        Product(
            id = 5,
            title = "Gold Bracelet",
            price = 695.0,
            description = "Elegant dragon design bracelet",
            category = "jewelery",
            image = "bracelet.jpg",
            rating = Rating(16, 2.0)
        ),
        Product(
            id = 9,
            title = "WD 2TB Hard Drive",
            price = 64.0,
            description = "Portable external storage",
            category = "electronics",
            image = "wd-elements.jpg",
            rating = Rating(120, 1.0)
        )
    )

    @Test
    fun getProducts_emitsCachedProductsWhenAvailable() = runTest {
        val cachedEntities = ProductMapper.domainListToEntityList(sampleProducts)
        whenever(productDao.getProducts()).thenReturn(flowOf(cachedEntities))
        val result = repository.getProducts().toList()
        assertEquals(1, result.size)
        assertEquals(sampleProducts, result.first())
    }

    @Test
    fun getProducts_fetchesFromApiWhenNoCache() = runTest {
        val apiProduct = sampleProducts.last()
        whenever(productDao.getProducts()).thenReturn(flowOf(emptyList()))
        whenever(api.getProducts()).thenReturn(listOf(apiProduct))
        val result = repository.getProducts().toList()
        assertEquals(1, result.size)
        assertEquals(listOf(apiProduct), result.first())
    }

    @Test
    fun getProducts_emitsEmptyListOnApiError() = runTest {
        whenever(productDao.getProducts()).thenReturn(flowOf(emptyList()))
        whenever(api.getProducts()).thenThrow(RuntimeException("API Error"))
        val result = repository.getProducts().toList()
        assertEquals(1, result.size)
        assertTrue(result.first().isEmpty())
    }
}