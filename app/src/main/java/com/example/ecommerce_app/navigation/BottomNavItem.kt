package com.example.ecommerce_app.navigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val title: String) {
    object Home : BottomNavItem("home", Icons.Filled.Home, "Home")
    object Search : BottomNavItem("search", Icons.Filled.Search, "Search")
    object favourite : BottomNavItem("Favourite", Icons.Filled.FavoriteBorder, "Favourite")
    object Cart : BottomNavItem("cart", Icons.Filled.ShoppingCart, "Cart")
    object Profile : BottomNavItem("profile", Icons.Filled.Person, "Profile")
    object checkout : BottomNavItem("profile", Icons.Filled.Person, "Profile")
}
