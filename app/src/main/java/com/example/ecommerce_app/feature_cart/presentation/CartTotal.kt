package com.example.ecommerce_app.feature_cart.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ecommerce_app.core.utils.Customizedbutton
import com.example.ecommerce_app.feature_cart.viewmodel.CartViewmodel

@Composable
fun CartTotal(navController: NavController, cartViewModel: CartViewmodel) {
    val totalItems by cartViewModel.totalItems.collectAsState()
    val totalCounts by cartViewModel.totalCount.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Total Amount: $totalItems",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Customizedbutton(
            text = "Check Out",
            onClick = {
                val price = totalItems ?: 0.0
                val count = totalCounts ?: 0
                navController.navigate("checkout/$totalItems/$totalCounts")
            }
        )


    }
}