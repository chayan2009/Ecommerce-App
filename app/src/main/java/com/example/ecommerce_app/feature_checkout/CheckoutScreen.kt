package com.example.ecommerce_app.feature_checkout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
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
    var promoCode by remember { mutableStateOf(TextFieldValue("")) }
    var discount by remember { mutableStateOf(0.0) }
    var isValidPromo by remember { mutableStateOf(false) }
    var selectedPromo by remember { mutableStateOf<String?>(null) }

    val deliveryFee = 5.00
    val totalBeforeDiscount = price * count + deliveryFee
    val totalAmount = totalBeforeDiscount - discount

    val promoOffers = listOf(
        "DISCOUNT10" to "10% Off on Total",
        "FREESHIP" to "Free Shipping",
        "SAVE5" to "Flat $5 Off"
    )

    Scaffold(
        topBar = { Appbar("Check Out", navController = navController, onSearchQueryChanged = { }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SectionTitle("Shipping Address")
            ShippingAddress()

            SectionTitle("Delivery Method")
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp),
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

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(3.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        BasicTextField(
                            value = promoCode,
                            onValueChange = { promoCode = it },
                            modifier = Modifier.weight(1f).padding(8.dp)
                        )
                        Button(
                            onClick = {
                                applyPromoCode(promoCode.text, totalBeforeDiscount).let {
                                    discount = it.first
                                    isValidPromo = it.second
                                }
                            },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Apply")
                        }
                    }

                    if (isValidPromo) {
                        Text("Promo Applied! Discount: $$discount", color = Color.Green)
                    }

                    SectionTitle("Available Promo Codes")
                    Column {
                        promoOffers.forEach { (code, description) ->
                            PromoCard(
                                code = code,
                                description = description,
                                isSelected = selectedPromo == code,
                                onSelect = {
                                    selectedPromo = code
                                    promoCode = TextFieldValue(code)
                                    applyPromoCode(code, totalBeforeDiscount).let {
                                        discount = it.first
                                        isValidPromo = it.second
                                    }
                                }
                            )
                        }
                    }

                    if (discount > 0) {
                        OrderSummaryRow("Discount:", "-$$discount", isBold = true)
                    }

                    Divider(thickness = 1.dp, color = Color.LightGray)
                    OrderSummaryRow("Total:", "$$totalAmount", isBold = true)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Customizedbutton(
                text = "SUBMIT ORDER".lowercase(),
                onClick = { showBottomSheet = true },
                modifier = Modifier.padding(bottom = 25.dp)
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

fun applyPromoCode(code: String, totalBeforeDiscount: Double): Pair<Double, Boolean> {
    return when (code) {
        "DISCOUNT10" -> totalBeforeDiscount * 0.1 to true
        "FREESHIP" -> 5.00 to true
        "SAVE5" -> 5.00 to true
        else -> 0.0 to false
    }
}

@Composable
fun PromoCard(code: String, description: String, isSelected: Boolean, onSelect: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).clickable { onSelect() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = if (isSelected) Color.LightGray else Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = code, style = MaterialTheme.typography.titleMedium)
                Text(text = description, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }
            if (isSelected) {
                Text("Applied", color = Color.Green)
            }
        }
    }
}
