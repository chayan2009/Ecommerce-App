package com.example.ecommerce_app.navigation


import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(rootNavController: NavHostController) {
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(bottomNavController) }
    ) {
        BottomNavGraph(navController = bottomNavController, rootNavController = rootNavController)
    }
}