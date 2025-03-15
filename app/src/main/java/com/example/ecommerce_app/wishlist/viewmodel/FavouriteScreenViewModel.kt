package com.example.ecommerce_app.wishlist.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce_app.domain.model.Cart
import com.example.ecommerce_app.domain.model.Favourite
import com.example.ecommerce_app.domain.usecase.GetFavouritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteScreenViewModel @Inject constructor(
    private val getFavouritesUseCase: GetFavouritesUseCase
) : ViewModel() {

    private val _favourites = MutableStateFlow<List<Favourite>>(emptyList())
    val favourites: StateFlow<List<Favourite>> = _favourites

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        getFavourites()
    }

    fun getFavourites() {
        viewModelScope.launch {
            getFavouritesUseCase.getFavourites().collect { favourites ->
                Log.d("CartDebug", "Retrieved carts: $favourites")
                _favourites.value = favourites
            }
        }
    }

    fun addFavouritesItem(cartItem: Cart) {

    }

    fun removeFavouritesItem(id: Int) {

    }

}