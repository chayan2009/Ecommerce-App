package com.example.ecommerce_app.feature_product.presentation.screen
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerce_app.domain.model.Product
import com.example.ecommerce_app.feature_product.presentation.viewmodel.ProductViewModel
import com.google.accompanist.pager.*
import kotlinx.coroutines.delay
import kotlin.math.max
import kotlin.math.min

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProductListScreen(viewModel: ProductViewModel = hiltViewModel()) {
    val products by viewModel.products.collectAsState()
    val scrollState = rememberLazyListState()

    // Parallax Effect Calculation
    val maxOffset = 200f
    val scrollOffset = min(scrollState.firstVisibleItemScrollOffset.toFloat(), maxOffset)
    val alpha = max(1f - (scrollOffset / maxOffset), 0f)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Products") },
                backgroundColor = Color.White.copy(alpha = alpha)
            )
        }
    ) {
        LazyColumn(
            state = scrollState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            // Banner Section
            item {
                Card(
                    elevation = 6.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BannerPager(products.map { it.image })
                }
            }

            // Recommended Products Section
            item {
                Card(
                    elevation = 6.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Recommended for You",
                            style = MaterialTheme.typography.h6
                        )
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(vertical = 8.dp)
                        ) {
                            items(products.take(5)) { product ->
                                RecommendedProductCard(product)
                            }
                        }
                    }
                }
            }

            // Product Grid Section (Fixed Issue ✅)
            items(products.chunked(2)) { rowProducts ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    rowProducts.forEach { product ->
                        ProductGridCard(product)
                    }
                }
            }
        }
    }
}

// Banner Pager with Auto Scroll & Dots
@OptIn(ExperimentalPagerApi::class)
@Composable
fun BannerPager(images: List<String>) {
    val pagerState = rememberPagerState()

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000) // Auto-scroll every 3 seconds
//            val nextPage = (pagerState.currentPage + 1) % images.size
         //   pagerState.animateScrollToPage(nextPage)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        HorizontalPager(
            count = images.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) { page ->
            Image(
                painter = rememberAsyncImagePainter(model = images[page]),
                contentDescription = "Banner Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Pager Dots Indicator
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier.padding(8.dp),
            activeColor = Color.Black,
            inactiveColor = Color.Gray
        )
    }
}

// Recommended Product Card
@Composable
fun RecommendedProductCard(product: Product) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .padding(8.dp),
        elevation = 6.dp,
        backgroundColor = Color(0xFFFFC107)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = product.image),
                contentDescription = product.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .padding(4.dp)
            )
            Text(text = product.title, style = MaterialTheme.typography.body2, color = Color.Black)
            Text(text = "$${product.price}", style = MaterialTheme.typography.subtitle1, color = Color.Black)
        }
    }
}

// Product Grid Card
@Composable
fun ProductGridCard(product: Product) {
    Card(
        modifier = Modifier
            .width(170.dp)
            .padding(8.dp),
        elevation = 6.dp,
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = product.image),
                contentDescription = product.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            )
            Text(text = product.title, style = MaterialTheme.typography.body2)
            Text(text = "$${product.price}", style = MaterialTheme.typography.subtitle1)
            Button(
                onClick = { /* Add to Cart Action */ },
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Text("Add to Cart")
            }
        }
    }
}
