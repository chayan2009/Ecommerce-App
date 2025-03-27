package com.example.ecommerce_app.feature_payment.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecommerce_app.core.utils.Customizedbutton
import com.example.ecommerce_app.core.notification.RequestNotificationPermission
import com.example.ecommerce_app.core.notification.createNotificationChannel
import com.example.ecommerce_app.core.notification.showOrderNotification

@Composable
fun PaymentBottomSheetContent(
    totalAmount: Double,
    itemPrice: Double,
    quantity: Int,
    onPaymentConfirmed: () -> Unit
) {
    val paymentMethods = listOf("Credit Card", "Google Pay", "PayPal")
    var selectedMethod by remember { mutableStateOf(paymentMethods[0]) }

    val context = LocalContext.current
    var requestPermission by remember { mutableStateOf(false) }

    if (requestPermission) {
        RequestNotificationPermission()
        requestPermission = false
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Select Payment Method", fontWeight = FontWeight.Bold, fontSize = 18.sp)

        LazyColumn {
            items(paymentMethods) { method ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedMethod = method }
                        .padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedMethod == method,
                        onClick = { selectedMethod = method }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(method, fontSize = 16.sp)
                }
            }
        }

        Divider(color = Color.LightGray, thickness = 1.dp)

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Payment Summary", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            OrderSummaryRow("Item Price:", "$$itemPrice")
            OrderSummaryRow("Quantity:", "$quantity")
            OrderSummaryRow("Delivery Fee:", "$5.00")
            Divider(color = Color.LightGray, thickness = 1.dp)
            OrderSummaryRow("Total:", "$$totalAmount", isBold = true)
        }

        Customizedbutton(
            text = "Pay Now".lowercase(),
            onClick = {
                createNotificationChannel(context)
                requestPermission = true
                showOrderNotification(context)
                onPaymentConfirmed()
            }
        )
    }
}

