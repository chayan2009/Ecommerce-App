package com.example.ecommerce_app.feature_account.history.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ecommerce_app.core.datastore.UserPreferences
import com.example.ecommerce_app.feature_account.history.OrderStatus
import com.example.ecommerce_app.feature_account.history.model.Order
import com.example.ecommerce_app.feature_account.notification.model.NotificationItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class OrdersScreenViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {
    private val _myOrderList = MutableStateFlow<List<Order>>(emptyList())
    val myOrderList: MutableStateFlow<List<Order>> = _myOrderList

    init {
        getListOfNotification()
    }

    private fun getListOfNotification() {
        val orders = listOf(
            Order("ORD123", "March 20, 2025", "$50.00", OrderStatus.DELIVERED),
            Order("ORD124", "March 21, 2025", "$30.00", OrderStatus.PROCESSING),
            Order("ORD125", "March 22, 2025", "$100.00", OrderStatus.COMPLETED),
            Order("ORD126", "March 23, 2025", "$75.00", OrderStatus.DELIVERED),
            Order("ORD127", "March 24, 2025", "$60.00", OrderStatus.PROCESSING),
            Order("ORD128", "March 25, 2025", "$90.00", OrderStatus.COMPLETED)
        )
        myOrderList.value = orders
    }
}