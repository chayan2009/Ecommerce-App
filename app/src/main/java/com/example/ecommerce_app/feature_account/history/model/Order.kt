package com.example.ecommerce_app.feature_account.history.model

import com.example.ecommerce_app.feature_account.history.OrderStatus


data class Order(
    val id: String,
    val date: String,
    val amount: String,
    val status: OrderStatus
)
