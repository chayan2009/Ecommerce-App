package com.example.ecommerce_app.feature_checkout

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ecommerce_app.R

@Composable
fun CheckoutScreen(navController: NavController, toInt: Double) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Top App Bar
        TopAppBar(
            title = { Text("Checkout", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            backgroundColor = Color.White,
            elevation = 2.dp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Shipping Address Section
        Text("Shipping address", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Jane Doe", fontWeight = FontWeight.Bold)
                    Text("3 Newbridge Court")
                    Text("Chino Hills, CA 91709, United States")
                }
                Text("Change", color = Color.Red, fontWeight = FontWeight.Bold, modifier = Modifier.clickable { /* Change Address */ })
            }
        }

        // Payment Section
        Text("Payment", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.image), // Replace with actual image
                        contentDescription = "MasterCard",
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("**** **** **** 3947")
                }
                Text("Change", color = Color.Red, fontWeight = FontWeight.Bold, modifier = Modifier.clickable { /* Change Payment */ })
            }
        }

        // Delivery Options
        Text("Delivery method", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DeliveryOptionCard(R.drawable.image, "FedEx", "2-3 days")
            DeliveryOptionCard(R.drawable.image, "USPS", "2-3 days")
            DeliveryOptionCard(R.drawable.image, "DHL", "2-3 days")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Order Summary
        Column(modifier = Modifier.fillMaxWidth()) {
            OrderSummaryRow("Order:", toInt.toString())
            OrderSummaryRow("Delivery:", "5$")
            OrderSummaryRow("Summary:", toInt.toString(), isBold = true)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Submit Button
        Button(
            onClick = { /* Handle Submit */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
        ) {
            Text("SUBMIT ORDER", fontSize = 16.sp, color = Color.White)
        }
    }
}

// Order Summary Row
@Composable
fun OrderSummaryRow(label: String, value: String, isBold: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal)
        Text(value, fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal)
    }
}

// Delivery Option Card
@Composable
fun DeliveryOptionCard(imageRes: Int, name: String, duration: String) {
    Card(
        modifier = Modifier
            .size(100.dp)
            .clickable { /* Select Delivery Method */ },
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = name,
                modifier = Modifier.size(40.dp)
            )
            Text(name, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            Text(duration, fontSize = 10.sp)
        }
    }
}
