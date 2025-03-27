package com.example.ecommerce_app.feature_account.notification.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ecommerce_app.core.common.Appbar
import com.example.ecommerce_app.feature_account.notification.viewmodel.NotificationsScreenViewModel


@Composable
fun NotificationsScreen(navController: NavController,notificationsScreenViewModel: NotificationsScreenViewModel) {

    val notificationsList by notificationsScreenViewModel.notificationList.collectAsState()

    Scaffold(
        topBar = {
            Appbar("Notifications", navController = navController,onSearchQueryChanged = { })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(notificationsList) { notification ->
                    NotificationItemCard(notification)
                    Spacer(modifier = Modifier.height(12.dp)) // Space between notifications
                }
            }
        }
    }
}


