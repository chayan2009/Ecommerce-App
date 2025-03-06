package com.example.ecommerce_app.feature_cart.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerce_app.domain.model.Product
import com.example.ecommerce_app.feature_cart.viewmodel.CartViewmodel

@Composable
fun CartItemRow(cartItem: Product, cartViewModel: CartViewmodel = hiltViewModel()) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image
            Image(
                painter = rememberAsyncImagePainter(model = cartItem.image),
                contentDescription = cartItem.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Product Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = cartItem.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1 // Ensures title is one line
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Price: $${cartItem.price}",
                    fontSize = 14.sp,
                    color = Color.Green,
                    fontWeight = FontWeight.SemiBold
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {

                    IconButton(
                        onClick = { },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Increase",
                            tint = Color.Blue
                        )
                    }

                    IconButton(
                        onClick = { },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Remove",
                            tint = Color.Red
                        )
                    }
                }
            }
        }
    }
}
