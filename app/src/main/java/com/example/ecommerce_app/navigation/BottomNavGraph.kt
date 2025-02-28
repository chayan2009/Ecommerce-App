package com.example.ecommerce_app.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ecommerce_app.feature_product.presentation.screen.ProductListScreen
import com.example.ecommerce_app.feature_profile.AccountScreen
import com.example.ecommerce_app.feature_search.SearchScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) { ProductListScreen() }
        composable(BottomNavItem.Search.route) { SearchScreen(navController) }
        composable(BottomNavItem.Cart.route) { ProductListScreen() }
        composable(BottomNavItem.Profile.route) { AccountScreen(navController) }
    }
}
