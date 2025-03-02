package com.example.ecommerce_app.feature_product.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerce_app.core.common.Appbar
import com.example.ecommerce_app.core.common.NavigationIconType
import com.example.ecommerce_app.domain.model.Product
import com.example.ecommerce_app.feature_product.presentation.viewmodel.ProductViewModel
import com.google.accompanist.pager.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProductListScreen(viewModel: ProductViewModel = hiltViewModel()) {

    val products by viewModel.products.collectAsState()
    val categories by viewModel.selectedCategory.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var selectedCategory by remember { mutableStateOf("All") }

    val filteredProducts = if (selectedCategory == "All") products else products.filter { it.category == selectedCategory }

    Scaffold(topBar = { Appbar("Product", navigationIconType = NavigationIconType.NONE) }) { paddingValues ->
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
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        CategoryFilter(
                            categories = viewModel.categories.collectAsState().value,  // ✅ Now a List<String>
                            selectedCategory = viewModel.selectedCategory.collectAsState().value
                        ) { category ->
                            viewModel.onCategorySelected(category)  // ✅ Update ViewModel state
                        }
                    }
                    item { BannerSection(filteredProducts.map { it.image }) }
                    item { RecommendedProductsSection(filteredProducts) }
                    item {
                        Text(
                            text = "All Products",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    item {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(600.dp)
                        ) {
                            items(filteredProducts) { product ->
                                ProductCard(product)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryFilter(categories: List<String>, selectedCategory: String, onCategorySelected: (String) -> Unit) {
    LazyRow(contentPadding = PaddingValues(16.dp)) {
        items(listOf("All") + categories) { category ->
            CategoryChip(category, selectedCategory == category) { onCategorySelected(category) }
        }
    }
}

@Composable
fun CategoryChip(category: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    val textColor = if (isSelected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface

    Box(
        modifier = Modifier
            .padding(8.dp)
            .background(backgroundColor, shape = MaterialTheme.shapes.medium)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = category, color = textColor)
    }
}

// Banner Section with ViewPager
@OptIn(ExperimentalPagerApi::class)
@Composable
fun BannerSection(images: List<String>) {
    val pagerState = rememberPagerState()

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Featured Banners",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(16.dp)
        )

        HorizontalPager(
            count = images.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) { page ->
            Image(
                painter = rememberAsyncImagePainter(model = images[page]),
                contentDescription = "Banner Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Indicator Dots
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(images.size) { index ->
                val color = if (pagerState.currentPage == index) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface.copy(alpha = 0.3f)
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(8.dp)
                        .background(color, shape = MaterialTheme.shapes.small)
                )
            }
        }
    }
}

// Recommended Products Section with Horizontal List
@Composable
fun RecommendedProductsSection(products: List<Product>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Recommended for You",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(16.dp)
        )

        LazyRow(contentPadding = PaddingValues(16.dp)) {
            items(products.take(5)) { product ->
                RecommendedProductCard(product)
            }
        }
    }
}

// Recommended Product Card
@Composable
fun RecommendedProductCard(product: Product) {
    Card(elevation = 6.dp, modifier = Modifier.padding(8.dp)) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = product.image),
                contentDescription = product.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(80.dp)
            )
            Text(text = product.title, style = MaterialTheme.typography.body2)
            Text(text = "$${product.price}", style = MaterialTheme.typography.subtitle1)
        }
    }
}

// Product Card for Grid Layout
@Composable
fun ProductCard(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = rememberAsyncImagePainter(model = product.image),
                contentDescription = product.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = product.title, style = MaterialTheme.typography.body1)
            Text(text = "$${product.price}", style = MaterialTheme.typography.subtitle1)
            Button(
                onClick = { /* Add to Cart */ },
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Text("Add to Cart")
            }
        }
    }
}
