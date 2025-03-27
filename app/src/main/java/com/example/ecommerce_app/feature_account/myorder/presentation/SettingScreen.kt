package com.example.ecommerce_app.feature_account.myorder.presentation


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ecommerce_app.core.common.Appbar

@Composable
fun SettingsScreen(navController: NavController) {
    var firstName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Appbar(title = "Settings", navController = navController,onSearchQueryChanged = { })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // First Name Field
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("New Password") },
                visualTransformation = if (showPassword) PasswordVisualTransformation() else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            // Save Button
            Button(
                onClick = {
                    // TODO: Implement save functionality
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Changes")
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Additional Settings Options
            SettingsOption("Notification Preferences") { /* TODO: Navigate to Notification Settings */ }
            SettingsOption("Theme") { /* TODO: Navigate to Theme Settings */ }
            SettingsOption("Logout") { /* TODO: Implement Logout */ }
        }
    }
}

@Composable
fun SettingsOption(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
    ) {
        Text(title, fontSize = 16.sp, color = Color.Black)
    }
}
