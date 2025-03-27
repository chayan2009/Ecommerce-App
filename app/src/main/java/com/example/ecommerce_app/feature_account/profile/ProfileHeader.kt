package com.example.ecommerce_app.feature_account.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerce_app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileHeader() {
    var showEditSheet by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberAsyncImagePainter("s"),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text("Chayan Chwodhury", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text("chayanchwodhury@mail.com", fontSize = 14.sp, color = Color.Gray)
            }

            IconButton(onClick = { showEditSheet = true }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Profile"
                )
            }
        }
    }

    if (showEditSheet) {
       // ModalBottomSheet(onDismissRequest = { showEditSheet = false }) {
            EditProfileScreen(onDismiss = { showEditSheet = false })
        //}
    }
}

@Composable
fun EditProfileScreen(onDismiss: () -> Unit) {
    var name by remember { mutableStateOf("Chayan Chwodhury") }
    var email by remember { mutableStateOf("chayanchwodhury@mail.com") }
    var phone by remember { mutableStateOf("+1234567890") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Edit Profile", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Phone Number") })
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {
            Text("Save")
        }
    }
}
