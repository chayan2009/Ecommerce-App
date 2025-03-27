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
import androidx.navigation.NavController
import com.example.ecommerce_app.domain.model.Cart
import com.example.ecommerce_app.domain.model.Favourite
import com.example.ecommerce_app.domain.model.Product
import com.example.ecommerce_app.feature_product.presentation.viewmodel.ProductViewModel

@Composable
fun RecommendedProductsSection(
    products: List<Product>,
    navController: NavController,
    viewModel: ProductViewModel
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            "Recommended for You",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        LazyRow(contentPadding = PaddingValues(16.dp)) {
            items(products) { product ->
                RecommendedProductCard(
                    product = product,
                    onProductClick = {
                        navController.navigate("product_details/${product.id}")                                     },
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