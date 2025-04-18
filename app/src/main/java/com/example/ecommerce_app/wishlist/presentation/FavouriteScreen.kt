package com.example.ecommerce_app.wishlist.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ecommerce_app.core.common.Appbar
import com.example.ecommerce_app.core.common.EmptyMessage
import com.example.ecommerce_app.wishlist.viewmodel.FavouriteScreenViewModel

@Composable
fun FavouriteScreen(
    navController: NavController,
    favouriteScreenViewModel: FavouriteScreenViewModel = hiltViewModel()
) {
    val favItems by favouriteScreenViewModel.favourites.collectAsState()

    Scaffold(
        topBar = {
            Appbar(
                "My WishList",
                navController = navController,
                onSearchQueryChanged = { })
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (favItems.isEmpty()) {
                EmptyMessage(
                    message = "No Favourites are available!",
                    fontSize = 18,
                    fontWeight = FontWeight.Medium,
                    color = Color.Red
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    FavouriteItemList(navController, favItems, favouriteScreenViewModel)
                }
            }
        }
    }
}
