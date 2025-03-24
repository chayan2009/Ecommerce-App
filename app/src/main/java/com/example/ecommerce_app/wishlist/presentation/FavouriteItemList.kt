package com.example.ecommerce_app.wishlist.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ecommerce_app.domain.model.Cart
import com.example.ecommerce_app.domain.model.Favourite
import com.example.ecommerce_app.wishlist.viewmodel.FavouriteScreenViewModel
@Composable
fun FavouriteItemList(
    navController: NavController,
    favItems: List<Favourite>,
    favouriteScreenViewModel: FavouriteScreenViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        favItems.forEach { favItem ->
            FavouriteItemRow(
                favourite = favItem,
                isFavorite = favItems.contains(favItem),
                onProductClick = {
                     navController.navigate("product_details/${favItem.id}")
                },
                onFavoriteClick = { item, isFav ->
                    favouriteScreenViewModel.toggleFavorite(item, isFav)
                },
                onCartClick = {
                    var updatedCart=Cart(favItem.id,favItem.title,favItem.price,favItem.description,favItem.category,favItem.image,1)
                    favouriteScreenViewModel.addToCart(updatedCart)
                    navController.navigate("cart")
                }
            )
        }
    }
}

