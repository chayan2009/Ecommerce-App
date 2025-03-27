package com.example.ecommerce_app.feature_product.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ecommerce_app.core.common.Appbar
import com.example.ecommerce_app.domain.model.Cart
import com.example.ecommerce_app.domain.model.Favourite
import com.example.ecommerce_app.feature_product.presentation.viewmodel.ProductViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProductListScreen(navController: NavController, viewModel: ProductViewModel = hiltViewModel()) {
    val products by viewModel.products.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var selectedCategory by remember { mutableStateOf("All") }

    val filteredProducts =
        if (selectedCategory == "All") products else products.filter { it.category == selectedCategory }

    Scaffold(topBar = { Appbar("Products", navController = navController) }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
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
                    item {
                        BannerSection(filteredProducts.map { it.image })
                    }
                    item {
                        RecommendedProductsSection(filteredProducts, navController, viewModel)
                    }
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
                            onProductClick = {
                                navController.navigate("product_details/${product.id}")
                            },
                            onCartClick = {
                                viewModel.addToCart(
                                    Cart(
                                        product.id,
                                        product.title,
                                        product.price,
                                        product.description,
                                        product.category,
                                        product.image,
                                        1
                                    )
                                )
                                navController.navigate("cart")
                            },
                            onFavoriteClick = {
                                viewModel.addToFavourite(
                                    Favourite(
                                        product.id,
                                        product.title,
                                        product.price,
                                        product.description,
                                        product.category,
                                        product.image
                                    )
                                )
                                navController.navigate("wishlist/${product.id}")
                            },
                        )
                    }
                }
            }
        }
    }
}
