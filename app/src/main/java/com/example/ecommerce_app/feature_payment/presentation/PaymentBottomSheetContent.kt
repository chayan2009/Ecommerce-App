package com.example.ecommerce_app.feature_payment.presentation

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.ecommerce_app.core.utils.Customizedbutton
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
    var showRationaleDialog by remember { mutableStateOf(false) }

    // Create notification channel early
    LaunchedEffect(Unit) {
        createNotificationChannel(context)
    }

    // Create the permission launcher
    val notificationLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            showOrderNotification(context)
        }
        onPaymentConfirmed()
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
                        .clickable { selectedMethod = method },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedMethod == method,
                        onClick = { selectedMethod = method }
                    )
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
                handlePayment(
                    context = context,
                    onPermissionGranted = {
                        showOrderNotification(context)
                        onPaymentConfirmed()
                    },
                    onShowRationale = { showRationaleDialog = true },
                    onPermissionDenied = { onPaymentConfirmed() },
                    notificationLauncher = notificationLauncher
                )
            }
        )
    }

    if (showRationaleDialog) {
        AlertDialog(
            onDismissRequest = { showRationaleDialog = false },
            title = { Text("Notifications Needed") },
            text = { Text("We use notifications to keep you updated about your order status.") },
            confirmButton = {
                Button(onClick = {
                    showRationaleDialog = false
                    notificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }) {
                    Text("Allow")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showRationaleDialog = false
                    onPaymentConfirmed()
                }) {
                    Text("Continue without")
                }
            }
        )
    }
}

private fun handlePayment(
    context: android.content.Context,
    onPermissionGranted: () -> Unit,
    onShowRationale: () -> Unit,
    onPermissionDenied: () -> Unit,
    notificationLauncher: androidx.activity.compose.ManagedActivityResultLauncher<String, Boolean>
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        when {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
                onPermissionGranted()
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                (context as Activity),
                Manifest.permission.POST_NOTIFICATIONS
            ) -> {
                onShowRationale()
            }
            else -> {
                notificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                onPermissionDenied()
            }
        }
    } else {
        onPermissionGranted()
    }
}
