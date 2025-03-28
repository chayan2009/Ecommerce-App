package com.example.ecommerce_app.feature_account.notification.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ecommerce_app.core.datastore.UserPreferences
import com.example.ecommerce_app.feature_account.history.OrderStatus
import com.example.ecommerce_app.feature_account.history.model.Order
import com.example.ecommerce_app.feature_account.notification.model.NotificationItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class NotificationsScreenViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _notificationList = MutableStateFlow<List<NotificationItem>>(emptyList())
    val notificationList: MutableStateFlow<List<NotificationItem>> = _notificationList


    init {
        getListOfNotification()
    }

    private fun getListOfNotification() {
        val notifications = listOf(
            NotificationItem(
                "1",
                "Order Shipped",
                "Your order #ORD123 has been shipped.",
                "March 22, 2025"
            ),
            NotificationItem(
                "2",
                "Order Delivered",
                "Your order #ORD124 has been delivered.",
                "March 21, 2025"
            ),
            NotificationItem(
                "3",
                "Discount Alert!",
                "Get 20% off on your next purchase.",
                "March 20, 2025"
            ),
            NotificationItem(
                "4",
                "Payment Received",
                "Your payment for #ORD125 is confirmed.",
                "March 19, 2025"
            ),
            NotificationItem(
                "5",
                "Limited Offer",
                "Flash sale starts now! Grab the best deals.",
                "March 18, 2025"
            )
        )
        _notificationList.value = notifications
    }
}