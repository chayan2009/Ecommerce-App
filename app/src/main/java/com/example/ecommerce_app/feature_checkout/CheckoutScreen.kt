package com.example.ecommerce_app.feature_checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ecommerce_app.R
import com.example.ecommerce_app.core.common.Appbar
import com.example.ecommerce_app.core.utils.Customizedbutton
import com.example.ecommerce_app.feature_payment.presentation.PaymentBottomSheetContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(navController: NavController, price: Double, count: Int) {
    var showBottomSheet by remember { mutableStateOf(false) }

    val deliveryFee = 5.00
    val totalAmount = price * count + deliveryFee

    Scaffold(
        topBar = { Appbar("Check Out", navController = navController) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            SectionTitle("Shipping Address")
            ShippingAddress()

            SectionTitle("Delivery Method")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                DeliveryOptionCard(R.drawable.image, "FedEx", "2-3 days")
                DeliveryOptionCard(R.drawable.image, "DHL", "2-3 days")
            }

            SectionTitle("Order Summary")
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OrderSummaryRow("Items:", "$count items")
                    OrderSummaryRow("Delivery Fee:", "$$deliveryFee")
                    Divider(thickness = 1.dp, color = Color.LightGray)
                    OrderSummaryRow("Total:", "$$totalAmount", isBold = true)
                }
            }
//
//            Button(
//                onClick = { showBottomSheet = true },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .shadow(8.dp, RoundedCornerShape(25.dp)),
//                shape = RoundedCornerShape(25.dp),
//                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EA))
//            ) {
//                Text("SUBMIT ORDER", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
//            }
            Customizedbutton(
                text = "SUBMIT ORDER".lowercase(),
                onClick = { showBottomSheet = true }
            )

        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false }
        ) {
            PaymentBottomSheetContent(
                totalAmount = totalAmount,
                itemPrice = price,
                quantity = count,
                onPaymentConfirmed = {
                    showBottomSheet = false
                    navController.navigate("success") {
                        popUpTo("checkout") { inclusive = true }
                    }
                }
            )
        }
    }
}

