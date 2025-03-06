package com.example.ecommerce_app.feature_profile

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ecommerce_app.core.common.Appbar
import com.example.ecommerce_app.feature_profile.presentation.ProfileHeader
import com.example.ecommerce_app.feature_profile.presentation.ProfileOptionsList
import com.example.ecommerce_app.feature_profile.viewmodel.AccountOptionViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AccountScreen(
    navController: NavController,
    accountOptionViewModel: AccountOptionViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Appbar("My Profile")
        ProfileHeader()
        Spacer(modifier = Modifier.height(8.dp))
        ProfileOptionsList(accountOptionViewModel.profileOptions)
    }
}



