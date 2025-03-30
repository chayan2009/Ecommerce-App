package com.example.ecommerce_app.wishlist.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce_app.domain.model.Cart
import com.example.ecommerce_app.domain.model.Favourite
import com.example.ecommerce_app.domain.usecase.GetCartsUseCase
import com.example.ecommerce_app.domain.usecase.GetFavouritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteScreenViewModel @Inject constructor(
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val getCartsUseCase: GetCartsUseCase
) : ViewModel() {

    private val _favourites = MutableStateFlow<List<Favourite>>(emptyList())
    val favourites: StateFlow<List<Favourite>> = _favourites

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        getFavourites()
    }

    private fun getFavourites() {
        viewModelScope.launch {
            getFavouritesUseCase.getFavourites().collect { favs ->
                _favourites.value = favs
                _isLoading.value = false
            }
        }
    }

    fun addToCart(cart: Cart) {
        viewModelScope.launch {
            runCatching { getCartsUseCase.addCartItem(cart) }
                .onFailure { it.printStackTrace() }
        }
    }

    fun toggleFavorite(favourite: Favourite, isFavorite: Boolean) {
        viewModelScope.launch {
            if (isFavorite) {
                getFavouritesUseCase.addFavItem(favourite)
            } else {
                getFavouritesUseCase.removeFavItem(favourite.id)
            }

            _favourites.value = if (isFavorite) {
                _favourites.value + favourite
            } else {
                _favourites.value.filterNot { it.id == favourite.id }
            }
        }
    }
}
