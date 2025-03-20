package com.example.ecommerce_app.feature_checkout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun ShippingAddress() {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text("Chayan Chowdhury", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text("Hyderabad", fontSize = 14.sp, color = Color.Gray)
                Text("India", fontSize = 14.sp, color = Color.Gray)
            }

            IconButton(
                onClick = { /* Handle edit address */ },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(Icons.Filled.Edit, contentDescription = "Edit Address", tint = Color.Red)
            }
        }
    }
}

