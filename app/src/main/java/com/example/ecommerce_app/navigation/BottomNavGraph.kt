package com.example.ecommerce_app.navigation
import CategoryScreen
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ecommerce_app.feature_cart.CartListScreen
import com.example.ecommerce_app.feature_checkout.CheckoutScreen
import com.example.ecommerce_app.feature_payment.presentation.OrderSuccessScreen
import com.example.ecommerce_app.feature_product.presentation.screen.ProductDetailsScreen
import com.example.ecommerce_app.feature_product.presentation.screen.ProductListScreen
import com.example.ecommerce_app.feature_account.AccountScreen
import com.example.ecommerce_app.feature_account.notification.presentation.NotificationsScreen
import com.example.ecommerce_app.feature_account.history.presentation.OrdersScreen
import com.example.ecommerce_app.feature_account.profile.EditProfileScreen
import com.example.ecommerce_app.feature_account.setting.SettingsScreen
import com.example.ecommerce_app.wishlist.presentation.FavouriteScreen

@Composable
fun BottomNavGraph(navController: NavHostController,
                   rootNavController: NavHostController? = null) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) { ProductListScreen(navController, hiltViewModel()) }
        composable(BottomNavItem.shop.route) { CategoryScreen(navController) }
        composable(BottomNavItem.Cart.route) { CartListScreen(navController) }
        composable(BottomNavItem.wishlist.route) { FavouriteScreen(navController) }
        composable(BottomNavItem.Profile.route) { AccountScreen(navController) }
        composable("edit_profile_screen") { EditProfileScreen(navController) }

        composable("wishlist/{id}") { backStackEntry ->
            FavouriteScreen(navController)
        }

        composable("success") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("id") ?: "Unknown"
            OrderSuccessScreen(navController)
        }

        composable("product_details/{id}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("id") ?: "Unknown"
            Log.v(("@@@@"),""+productId)
            ProductDetailsScreen(navController, productId, hiltViewModel())
        }
        composable("checkout/{price}/{count}") { backStackEntry ->
            val price = backStackEntry.arguments?.getString("price")?.toDoubleOrNull() ?: 0.0
            val count = backStackEntry.arguments?.getString("count")?.toIntOrNull() ?: 0
            CheckoutScreen(navController, price, count)
        }

        composable("settings") { backStackEntry ->
            SettingsScreen(navController,rootNavController, hiltViewModel())
        }

        composable("orders") { backStackEntry ->
            OrdersScreen(navController, hiltViewModel())
        }
        composable("notifications") { backStackEntry ->
            NotificationsScreen(navController, hiltViewModel())
        }
    }
}


