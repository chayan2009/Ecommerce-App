package com.example.ecommerce_app.feature_profile.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.ecommerce_app.domain.usecase.GetProductsUseCase
import com.example.ecommerce_app.feature_profile.model.ProfileOptionItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountOptionViewModel@Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    var profileOptions = listOf(
        ProfileOptionItem("My Orders", "Already have 12 orders"),
        ProfileOptionItem("Shipping Addresses", "1 addresses"),
        ProfileOptionItem("Payment Methods", "Visa **34"),
        ProfileOptionItem("Promocodes", "You have special prom ocodes"),
        ProfileOptionItem("My Reviews", "Reviews for 0 items"),
        ProfileOptionItem("Settings", "Notifications, password")
    )
}