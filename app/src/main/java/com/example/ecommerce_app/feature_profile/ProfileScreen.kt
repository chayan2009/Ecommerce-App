package com.example.ecommerce_app.feature_profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ecommerce_app.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn

@Composable
fun ProfileScreen(navController: NavController) {
    var username by remember { mutableStateOf("John Doe") }
    var email by remember { mutableStateOf("john.doe@example.com") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Picture
        Image(
            painter = painterResource(id = R.drawable.image),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(100.dp)
                .background(Color.Gray, CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Username & Email
        Text(text = username, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text(text = email, fontSize = 16.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(24.dp))

        val options = listOf(
            ProfileOption("Edit Profile", Icons.Default.Person),
            ProfileOption("Settings", Icons.Default.Settings),
            ProfileOption("Favorites", Icons.Default.Favorite),
            ProfileOption("Address Book", Icons.Default.LocationOn)
        )

        // Profile Options List
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.White)
        ) {
            items(options) { option ->
                ProfileOptionItem(title = option.title, icon = option.icon) {
                    // Handle option click
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Logout Button
        Button(
            onClick = { /* Handle Logout */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(Color.Red)
        ) {
            Text(text = "Logout", fontSize = 18.sp, color = Color.White)
        }
    }
}

// Data class for Profile Options
data class ProfileOption(val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)

@Composable
fun ProfileOptionItem(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .shadow(4.dp, RoundedCornerShape(10.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Profile Icon",
                tint = Color(0xFF6200EA),
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Navigate",
                tint = Color.Gray
            )
        }
    }
}
