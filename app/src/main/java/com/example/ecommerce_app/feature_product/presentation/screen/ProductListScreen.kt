package com.example.ecommerce_app.feature_product.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.ecommerce_app.core.common.Appbar
import com.example.ecommerce_app.domain.model.Cart
import com.example.ecommerce_app.domain.model.Favourite
import com.example.ecommerce_app.feature_product.presentation.viewmodel.ProductViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce

@OptIn(FlowPreview::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProductListScreen(navController: NavController, viewModel: ProductViewModel = hiltViewModel()) {
    val products by viewModel.products.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var selectedCategory by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }
    val debouncedSearchQuery = remember { MutableStateFlow("") }

    LaunchedEffect(searchQuery) {
        snapshotFlow { searchQuery }
            .debounce(300)
            .collect { debouncedSearchQuery.emit(it) }
    }

    val debouncedQuery by debouncedSearchQuery.collectAsStateWithLifecycle("")

    val filteredProducts = products.filter { product ->
        (selectedCategory == "All" || product.category.equals(selectedCategory, ignoreCase = true)) &&
                (debouncedQuery.isEmpty() || product.title?.contains(debouncedQuery, ignoreCase = true) == true)
    }

    Scaffold(
        topBar = {
            Appbar(
                title = "Products",
                navController = navController,
                onSearchQueryChanged = { query -> searchQuery = query }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        CategoryFilter(categories, selectedCategory) { selectedCategory = it }
                        Divider(modifier = Modifier.padding(vertical = 2.dp))
                    }
                    if (filteredProducts.isNotEmpty()) {
                        item { BannerSection(filteredProducts.mapNotNull { it.image }) }
                        item { RecommendedProductsSection(filteredProducts, navController, viewModel) }
                        item {
                            Text(
                                "All Products",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                        items(filteredProducts) { product ->
                            ProductCard(
                                product = product,
                                onProductClick = { navController.navigate("product_details/${product.id}") },
                                onCartClick = {
                                    viewModel.addToCart(
                                        Cart(
                                            id = product.id,
                                            title = product.title ?: "Unknown",
                                            price = product.price ?: 0.0,
                                            description = product.description ?: "No Description",
                                            category = product.category ?: "Unknown",
                                            image = product.image ?: "",
                                            quantity = 1
                                        )
                                    )
                                    navController.navigate("cart")
                                },
                                onFavoriteClick = {
                                    viewModel.addToFavourite(
                                        Favourite(
                                            id = product.id,
                                            title = product.title ?: "Unknown",
                                            price = product.price ?: 0.0,
                                            description = product.description ?: "No Description",
                                            category = product.category ?: "Unknown",
                                            image = product.image ?: ""
                                        )
                                    )
                                    navController.navigate("wishlist/${product.id}")
                                },
                            )
                        }
                    } else {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "No products are available",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


