package com.example.ecommerce_app.feature_product.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecommerce_app.domain.model.Product

@Composable
fun RecommendedProductsSection(products: List<Product>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            "Recommended for You",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        LazyRow(contentPadding = PaddingValues(16.dp)) {
            items(products.take(5)) { product -> RecommendedProductCard(product) }
        }
    }
}