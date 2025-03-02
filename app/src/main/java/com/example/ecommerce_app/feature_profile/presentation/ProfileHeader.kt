package com.example.ecommerce_app.feature_profile.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecommerce_app.R

@Composable
fun ProfileHeader(username: String, email: String, phoneNumber: String) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.image),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(100.dp)
                .background(Color.Gray, CircleShape)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = username, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text(text = email, fontSize = 16.sp, color = Color.Gray)
        Text(text = phoneNumber, fontSize = 16.sp, color = Color.Gray)
    }
}