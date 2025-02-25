package com.example.ecommerce_app.navigation

sealed class Screen(val route:String){

    object SplashScreen: Screen("splash_screen")
    object LoginScreen: Screen("login_screen")
    object ProductListScreen: Screen("product_list_screen")

}