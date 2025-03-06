package com.example.ecommerce_app.navigation
import Screen
import SearchScreen
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ecommerce_app.feature_login.LoginScreen
import com.example.ecommerce_app.feature_product.presentation.screen.ProductDetailsScreen
import com.example.ecommerce_app.feature_product.presentation.screen.ProductListScreen
import com.example.ecommerce_app.feature_profile.AccountScreen
@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) { ProductListScreen(navController) }
        composable(BottomNavItem.Search.route) { SearchScreen(navController) }
        composable(BottomNavItem.Cart.route) { CartListScreen(navController) }
        composable(BottomNavItem.Profile.route) { AccountScreen(navController) }
        composable("product_details/{id}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("id") ?: "Unknown"
            ProductDetailsScreen(navController, productId.toInt(), hiltViewModel())
        }
    }
}


