package com.example.ecommerce_app.feature_profile

import Screen
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ecommerce_app.core.common.Appbar
import com.example.ecommerce_app.core.common.NavigationIconType
import com.example.ecommerce_app.feature_profile.presentation.AccountOptionItem
import com.example.ecommerce_app.feature_profile.presentation.ProfileHeader
import com.example.ecommerce_app.feature_profile.viewmodel.AccountOptionViewModel
import com.example.ecommerce_app.navigation.BottomNavItem

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AccountScreen(
    navController: NavController,
    accountOptionViewModel: AccountOptionViewModel = hiltViewModel()
) {

    val accountItems by accountOptionViewModel.accountItems.collectAsState()

    var username by remember { mutableStateOf("chayan") }
    var email by remember { mutableStateOf("chayanchowdhury@gmail.com") }
    var phoneNumber by remember { mutableStateOf("96763343") }
    Scaffold(
        topBar = { Appbar(title = "Profile",navigationIconType = NavigationIconType.NONE, bgColor = MaterialTheme.colors.primary, showIcons = false) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ProfileHeader(username, email,phoneNumber)

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.White)
            ) {
                items(accountItems) { option ->
                    AccountOptionItem(option.title, option.icon, option.onClick)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row (modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom){
                Button(
                        onClick = {
                            Log.d("Navigation", "Navigating to: ${Screen.LoginScreen.route}")
                            navController.navigate(Screen.LoginScreen.route) {}                        },
                    modifier = Modifier.padding(bottom = 100.dp)
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(Color.Red)
                ) {
                    Text(text = "Logout", fontSize = 18.sp, color = Color.White)
                }
            }

        }
    }

}

