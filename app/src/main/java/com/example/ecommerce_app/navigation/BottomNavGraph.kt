package com.example.ecommerce_app.navigation
import CategoryScreen
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ecommerce_app.feature_cart.CartListScreen
import com.example.ecommerce_app.feature_checkout.CheckoutScreen
import com.example.ecommerce_app.feature_product.presentation.screen.ProductDetailsScreen
import com.example.ecommerce_app.feature_product.presentation.screen.ProductListScreen
import com.example.ecommerce_app.feature_profile.AccountScreen
import com.example.ecommerce_app.wishlist.presentation.FavouriteScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) { ProductListScreen(navController, hiltViewModel()) }
        composable(BottomNavItem.shop.route) { CategoryScreen(navController) }
        composable(BottomNavItem.Cart.route) { CartListScreen(navController) }
        composable(BottomNavItem.wishlist.route) { FavouriteScreen(navController) }
        composable(BottomNavItem.Profile.route) { AccountScreen(navController) }

        composable("wishlist/{id}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("id") ?: "Unknown"
            FavouriteScreen(navController)
        }

        composable("product_details/{id}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("id") ?: "Unknown"
            ProductDetailsScreen(navController, productId.toInt(), hiltViewModel())
        }
        composable("checkout/{price}/{count}") { backStackEntry ->
            val price = backStackEntry.arguments?.getString("price")?.toDoubleOrNull() ?: 0.0
            val count = backStackEntry.arguments?.getString("count")?.toIntOrNull() ?: 0
            CheckoutScreen(navController, price, count)
        }

    }
}


