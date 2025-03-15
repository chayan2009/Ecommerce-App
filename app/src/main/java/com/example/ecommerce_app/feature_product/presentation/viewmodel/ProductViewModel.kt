package com.example.ecommerce_app.feature_product.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce_app.domain.model.Cart
import com.example.ecommerce_app.domain.model.Product
import com.example.ecommerce_app.domain.usecase.GetCartsUseCase
import com.example.ecommerce_app.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getCartsUseCase: GetCartsUseCase
) : ViewModel() {

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> = _categories

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
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
        getProductsUseCase().collect { productList ->
            _products.value = productList
        }
    }

    private suspend fun fetchCategories() {
        getProductsUseCase().collect { productList ->
            val categoryList = productList.map { it.category }.distinct()
            _categories.value = categoryList
        }
    }

    fun onCategorySelected(category: String) {
        _selectedCategory.value = category
    }

    fun addToCart(cart: Cart) {
        viewModelScope.launch {
            getCartsUseCase.addCartItem(cart)
        }
    }
}
