package com.example.ecommerce_app.feature_account.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.ecommerce_app.domain.usecase.GetProductsUseCase
import com.example.ecommerce_app.feature_account.profile.model.ProfileOptionItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountOptionViewModel@Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    var profileOptions = listOf(
        ProfileOptionItem("My Orders", "Already have 12 orders"),
        ProfileOptionItem("My Reviews", "Reviews for 0 items"),
        ProfileOptionItem("Notifications", "Notifications for 0 items"),
        ProfileOptionItem("Settings", " password")
    )
}