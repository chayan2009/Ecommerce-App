package com.example.ecommerce_app.feature_splash


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ecommerce_app.R
import com.example.ecommerce_app.feature_splash.viewmodel.SplashScreenViewModel
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SplashScreen(navController: NavController,splashScreenViewModel: SplashScreenViewModel) {
    val loginState by splashScreenViewModel.loginState.collectAsState()


    LaunchedEffect(loginState) {
        delay(2000)
        if (loginState == true) {
            navController.navigate("main") {
                popUpTo("auth") { inclusive = true }
            }
        } else {
            navController.navigate(Screen.LoginScreen.route) {
                popUpTo(Screen.SplashScreen.route) { inclusive = true }
            }
        }
    }


//    LaunchedEffect(loginState) {
//
//        delay(2000)
//        if (loginState==true){
//            navController.navigate(Screen.MainScreen.route) {
//                popUpTo(Screen.MainScreen.route) { inclusive = true }
//            }
//        }else{
//            navController.navigate(Screen.LoginScreen.route) {
//                popUpTo(Screen.LoginScreen.route) { inclusive = true }
//            }
//        }
//
//    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(R.drawable.image),
            contentDescription = "Splash background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
        ){

            Text(
                text = "Welcome to Ecommerce App",
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }

    }
}
