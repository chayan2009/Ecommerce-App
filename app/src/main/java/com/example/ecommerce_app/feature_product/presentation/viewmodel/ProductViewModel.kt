package com.example.ecommerce_app.feature_product.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce_app.domain.model.Cart
import com.example.ecommerce_app.domain.model.Favourite
import com.example.ecommerce_app.domain.model.Product
import com.example.ecommerce_app.domain.usecase.GetCartsUseCase
import com.example.ecommerce_app.domain.usecase.GetFavouritesUseCase
import com.example.ecommerce_app.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getCartsUseCase: GetCartsUseCase,
    private val getFavouritesUseCase: GetFavouritesUseCase
) : ViewModel() {

    private val _carts = MutableStateFlow<List<Cart>>(emptyList())
    val carts: StateFlow<List<Cart>> = _carts.asStateFlow()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> = _categories.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val totalCount: StateFlow<Int> = _carts
        .map { carts -> carts.sumOf { it.quantity } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                getProductsUseCase().collect { productList ->
                    _products.value = productList
                    _categories.value = productList.map { it.category }.distinct()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
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

    fun addToFavourite(favourite: Favourite) {
        viewModelScope.launch {
            runCatching { getFavouritesUseCase.addFavItem(favourite) }
                .onFailure { it.printStackTrace() }
        }
    }
}
