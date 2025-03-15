package com.example.ecommerce_app.feature_product.presentation.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun CategoryChip(category: String, isSelected: Boolean, onClick: () -> Unit) {
    ElevatedButton(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.padding(horizontal = 5.dp),
        colors = ButtonDefaults.buttonColors(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)
    ) {
        Text(text = category, color = if (isSelected) Color.White else Color.Black)
    }
}
