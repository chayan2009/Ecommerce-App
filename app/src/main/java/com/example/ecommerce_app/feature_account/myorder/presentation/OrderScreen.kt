package com.example.ecommerce_app.feature_account.myorder.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ecommerce_app.feature_account.myorder.OrderStatus
import com.example.ecommerce_app.feature_account.myorder.model.Order
import com.example.ecommerce_app.feature_account.myorder.viewmodel.OrdersScreenViewModel


@Composable
fun OrdersScreen(navController: NavController,ordersScreenViewModel: OrdersScreenViewModel) {
    val orders by ordersScreenViewModel.myOrderList.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }

    val filteredOrders = when (selectedTab) {
        0 -> orders.filter { it.status == OrderStatus.DELIVERED }
        1 -> orders.filter { it.status == OrderStatus.PROCESSING }
        else -> orders.filter { it.status == OrderStatus.COMPLETED }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Orders") },
                backgroundColor = Color.White,
                elevation = 4.dp
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // Tabs with spacing
            TabRow(
                selectedTabIndex = selectedTab,
                backgroundColor = Color.White,
                contentColor = MaterialTheme.colors.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }) {
                    Text("Delivered", modifier = Modifier.padding(vertical = 8.dp))
                }
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) {
                    Text("Processing", modifier = Modifier.padding(vertical = 8.dp))
                }
                Tab(selected = selectedTab == 2, onClick = { selectedTab = 2 }) {
                    Text("Completed", modifier = Modifier.padding(vertical = 8.dp))
                }
            }

            // Order List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(filteredOrders) { order ->
                    OrderItem(order) {
                    }
                    Spacer(modifier = Modifier.height(12.dp)) // Space between orders
                }
            }
        }
    }
}

@Composable
fun OrderItem(order: Order, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = 6.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Order ID: ${order.id}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Date: ${order.date}", fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Amount: ${order.amount}", fontSize = 14.sp, color = Color.Black)
        }
    }
}
