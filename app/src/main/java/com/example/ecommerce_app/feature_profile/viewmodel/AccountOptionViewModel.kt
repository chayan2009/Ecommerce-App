package com.example.ecommerce_app.feature_profile.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.lifecycle.ViewModel
import com.example.ecommerce_app.domain.usecase.GetProductsUseCase
import com.example.ecommerce_app.feature_profile.model.AccountItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AccountOptionViewModel@Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _accountItems = MutableStateFlow(
        listOf(
            AccountItems("Profile", Icons.Filled.Person) {  },
            AccountItems("Settings", Icons.Filled.Settings) { },
            AccountItems("Notifications", Icons.Default.Notifications) {},
            AccountItems("Orders", Icons.Filled.ShoppingCart) {},
            AccountItems("Logout", Icons.Filled.ExitToApp) {}
        )
    )

    val accountItems: StateFlow<List<AccountItems>> = _accountItems
}