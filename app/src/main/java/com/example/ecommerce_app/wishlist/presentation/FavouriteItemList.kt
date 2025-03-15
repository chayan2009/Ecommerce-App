package com.example.ecommerce_app.wishlist.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ecommerce_app.domain.model.Favourite
import com.example.ecommerce_app.wishlist.viewmodel.FavouriteScreenViewModel

@Composable
fun FavouriteItemList(
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
            FavouriteItemRow(favItem, onProductClick = {}, onFavoriteClick = {}, onCartClick = {})
        }
    }
}
