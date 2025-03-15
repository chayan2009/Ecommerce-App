package com.example.ecommerce_app.feature_product.presentation.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp


@Composable
fun CategoryFilter(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    LazyRow(contentPadding = PaddingValues(16.dp)) {
        items(listOf("All") + categories) { category ->
            CategoryChip(
                category,
                selectedCategory == category,
                onClick = { onCategorySelected(category) }
            )
        }
    }
}