import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.ecommerce_app.core.common.Appbar
import com.example.ecommerce_app.core.common.EmptyMessage
import com.example.ecommerce_app.domain.model.Cart
import com.example.ecommerce_app.domain.model.Favourite
import com.example.ecommerce_app.feature_category.viewmodel.CategoryScreenViewModel

@Composable
fun CategoryScreen(
    navController: NavHostController,
    categoryScreenViewModel: CategoryScreenViewModel = hiltViewModel()
) {
    val categoryList by categoryScreenViewModel.categories.collectAsState()
    val productList by categoryScreenViewModel.products.collectAsState()
    val isLoading by categoryScreenViewModel.isLoading.collectAsState()

    var selectedCategory by remember { mutableStateOf(categoryList.firstOrNull() ?: "All") }

    Scaffold(
        topBar = { Appbar("Categories", navController = navController) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val filteredProducts =
                if (selectedCategory == "All") productList else productList.filter { it.category == selectedCategory }

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (categoryList.isEmpty()) {
                EmptyMessage(
                    message = "No categories available!",
                    fontWeight = FontWeight.Medium,
                    color = Color.Red
                )
            } else {
                Column(modifier = Modifier.fillMaxSize()) {

                    ScrollableTabRow(
                        selectedTabIndex = categoryList.indexOf(selectedCategory).takeIf { it >= 0 } ?: 0,
                        backgroundColor = Color.Transparent,
                        edgePadding = 8.dp,
                        divider = {},
                        modifier = Modifier.height(55.dp)
                    ) {
                        categoryList.forEachIndexed { index, category ->
                            Tab(
                                selected = selectedCategory == category,
                                onClick = { selectedCategory = category },
                                modifier = Modifier
                                    .padding(horizontal = 4.dp, vertical = 6.dp)
                                    .background(
                                        if (selectedCategory == category) Color(0xFF4CAF50) else Color.LightGray.copy(0.2f),
                                        shape = RoundedCornerShape(50.dp)
                                    )
                                    .border(
                                        1.dp,
                                        if (selectedCategory == category) Color(0xFF4CAF50) else Color.Gray,
                                        shape = RoundedCornerShape(50.dp)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 6.dp),
                                text = {
                                    Text(
                                        text = category,
                                        textAlign = TextAlign.Center,
                                        fontWeight = if (selectedCategory == category) FontWeight.SemiBold else FontWeight.Normal,
                                        fontSize = 14.sp,
                                        color = if (selectedCategory == category) Color.White else Color.Black
                                    )
                                }
                            )
                        }
                    }

                    if (filteredProducts.isEmpty()) {
                        EmptyMessage(
                            message = "No products available for $selectedCategory!",
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray
                        )
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(1),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(filteredProducts) { product ->
                                com.example.ecommerce_app.feature_product.presentation.screen.ProductCard(
                                    product = product,
                                    onProductClick = { navController.navigate("product_details/${product.id}") },
                                    onCartClick = {
                                        categoryScreenViewModel.addToCart(
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
                                        categoryScreenViewModel.addToFavourite(
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
    }
}
