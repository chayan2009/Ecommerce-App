package com.example.ecommerce_app.navigation

import androidx.navigation.compose.NavHost

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.ecommerce_app.feature_login.LoginScreen
import com.example.ecommerce_app.feature_login.SignUpScreen
import com.example.ecommerce_app.feature_splash.SplashScreen
import com.example.ecommerce_app.feature_welcome.WelcomeScreen

@Composable
fun AppNavigation(navController: NavHostController){

    NavHost(navController=navController,startDestination= Screen.SplashScreen.route){
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(Screen.WelcomeScreen.route) {
            WelcomeScreen(navController)
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(Screen.SignUpScreen.route) {
            SignUpScreen(navController)
        }
    }
}