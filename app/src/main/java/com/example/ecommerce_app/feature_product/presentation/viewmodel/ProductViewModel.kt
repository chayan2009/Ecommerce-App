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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getCartsUseCase: GetCartsUseCase,
    private val getFavouritesUseCase: GetFavouritesUseCase
) : ViewModel() {

    private val _carts = MutableStateFlow<List<Cart>>(emptyList())
    val carts: StateFlow<List<Cart>> = _carts.asStateFlow()

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> = _categories

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    val totalCount: StateFlow<Int> = _carts.map { carts ->
        carts.sumOf { it.quantity }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)


    init {
        fetchData()
    }
    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                val productsDeferred = async { fetchProducts() }
                val categoriesDeferred = async { fetchCategories() }

                productsDeferred.await()
                categoriesDeferred.await()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun fetchProducts() {
        try {
            getProductsUseCase().collect { productList ->
                _products.value = productList
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun fetchCategories() {
        try {
            getProductsUseCase().collect { productList ->
                val categoryList = productList.map { it.category }.distinct()
                _categories.value = categoryList
            }
        } catch (e: Exception) {
            e.printStackTrace()
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
