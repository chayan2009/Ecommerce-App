package com.example.ecommerce_app.feature_product.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerce_app.core.common.Appbar
import com.example.ecommerce_app.core.common.NavigationIconType
import com.example.ecommerce_app.domain.model.Cart
import com.example.ecommerce_app.feature_product.presentation.viewmodel.ProductViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductDetailsScreen(
    navController: NavController,
    productId: String,
    productViewModel: ProductViewModel= hiltViewModel()
) {
    val products by productViewModel.products.collectAsState()
    val product = products.find { it.id == (productId.toIntOrNull() ?: 0) }
    val scrollState = rememberScrollState()
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Appbar(
                title = product?.title ?: "Product Details",
                bgColor = Color(0xFF6200EA),
                navigationIconType = NavigationIconType.BACK,
                onNavigationClick = { navController.popBackStack() },
                navController = navController,
                cartCount = productViewModel.totalCount.collectAsState().value,onSearchQueryChanged = { searchQuery = it }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (product == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Product not found!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Image(
                        painter = rememberAsyncImagePainter(model = product.image),
                        contentDescription = product.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(0.7f)
                    )

                    Card(
                        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(text = product.title, fontSize = 22.sp, fontWeight = FontWeight.Bold)

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Rating",
                                    tint = Color.Black
                                )
                                Text(text = "4.5/5", fontSize = 18.sp, fontWeight = FontWeight.Medium)
                            }

                            Text(
                                text = "Price: $${product.price}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )

                            Text(
                                text = product.description,
                                fontSize = 16.sp,
                                color = Color.Black
                            )

                            Button(
                                onClick = {
                                    productViewModel.addToCart(
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
                                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                                shape = RoundedCornerShape(20.dp)
                            ) {
                                Text(text = "Add to Cart", fontSize = 18.sp, color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }

}
