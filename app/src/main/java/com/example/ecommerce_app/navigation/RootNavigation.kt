package com.example.ecommerce_app.navigation
import Screen
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.ecommerce_app.feature_login.LoginScreen
import com.example.ecommerce_app.feature_signup.presentation.SignUpScreen
import com.example.ecommerce_app.feature_splash.SplashScreen
import com.example.ecommerce_app.feature_welcome.WelcomeScreen

fun isUserLoggedIn(): Boolean {
    return false
}
@Composable
fun RootNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if (isUserLoggedIn()) "main" else "auth"
    ) {
        navigation(startDestination = Screen.SplashScreen.route, route = "auth") {
            composable(Screen.SplashScreen.route) { SplashScreen(navController, hiltViewModel()) }
            composable(Screen.WelcomeScreen.route) { WelcomeScreen(navController) }
            composable(Screen.LoginScreen.route) { LoginScreen(navController) }
            composable(Screen.SignUpScreen.route) { SignUpScreen(navController) }
        }

        navigation(startDestination = "home", route = "main") {
            composable("home") {
                MainScreen(rootNavController = navController)
            }
        }
    }
}