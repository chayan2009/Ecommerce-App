package com.example.ecommerce_app.feature_product.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay


@OptIn(ExperimentalPagerApi::class)
@Composable
fun BannerSection(images: List<String>) {
    val pagerState = rememberPagerState()
    LaunchedEffect(pagerState) {
        while (true) {
            delay(3000)
            pagerState.animateScrollToPage((pagerState.currentPage + 1) % images.size)
        }
    }
    HorizontalPager(
        count = images.size,
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    ) { page ->
        Image(
            painter = rememberAsyncImagePainter(images[page]),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}
