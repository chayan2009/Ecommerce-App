package com.example.ecommerce_app.feature_product.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerce_app.core.common.RatingBar
import com.example.ecommerce_app.domain.model.Product


@Composable
fun RecommendedProductCard(product: Product) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .padding(8.dp)
            .width(200.dp)
            .height(260.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            Image(
                painter = rememberAsyncImagePainter(product.image),
                contentDescription = product.title,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.6f))
                    .align(Alignment.BottomCenter)
            ) {
                Column(
                    modifier = Modifier.padding(top = 8.dp).padding(10.dp)
                ) {
                    Text(
                        text = product.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        maxLines = 1
                    )

                    RatingBar(
                        rating = product.rating.rate,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Text(
                        text = "(${product.rating.count})",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Text(
                        text = product.category,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        maxLines = 1,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}