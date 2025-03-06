package com.example.ecommerce_app.feature_profile.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecommerce_app.feature_profile.model.ProfileOptionItem

@Composable
fun ProfileOptionsList(viewModel: List<ProfileOptionItem> = hiltViewModel()) {
    Column {
        viewModel.forEach { option ->
            ProfileOption(option)
        }
    }
}

@Composable
fun ProfileOption(option: ProfileOptionItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(option.title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(option.subtitle, fontSize = 14.sp, color = Color.Gray)
        }
        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Arrow", tint = Color.Gray)
    }
}
