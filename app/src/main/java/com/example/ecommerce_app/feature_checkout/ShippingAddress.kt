package com.example.ecommerce_app.feature_checkout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun ShippingAddress() {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("chayan chowdhury", fontWeight = FontWeight.Bold, modifier = Modifier.padding(2.dp))
                Text("hyderabad",modifier = Modifier.padding(2.dp))
                Text("India",modifier = Modifier.padding(2.dp))
            }
            Text("Change", color = Color.Red, fontWeight = FontWeight.Bold, modifier = Modifier.clickable { /* Change Address */ })
        }
    }
}