package com.example.ecommerce_app.navigation

import androidx.navigation.compose.NavHost

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.ecommerce_app.feature_login.LoginScreen
import com.example.ecommerce_app.feature_splash.SplashScreen

@Composable
fun AppNavigation(navController: NavHostController){

    NavHost(navController=navController,startDestination= Screen.SplashScreen.route){
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(navController)
        }
    }
}