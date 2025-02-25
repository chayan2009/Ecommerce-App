package com.example.ecommerce_app.data.source.api

import com.example.ecommerce_app.data.source.dto.ProductDto
import retrofit2.http.GET

interface ProductApi {
    @GET("products")
    suspend fun getProducts(): List<ProductDto>
}
