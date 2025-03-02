package com.example.ecommerce_app.feature_search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce_app.domain.model.Product
import com.example.ecommerce_app.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _filteredItems = MutableStateFlow<List<Product>>(emptyList())
    val filteredItems: StateFlow<List<Product>> = _filteredItems.asStateFlow()

    init {
        fetchData()
        observeSearchQuery()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val productsDeferred = async { fetchProducts() }
                productsDeferred.await()
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

    private fun observeSearchQuery() {
        viewModelScope.launch {
            _query
                .debounce(500)
                .collectLatest { searchQuery ->
                    _filteredItems.value = if (searchQuery.isBlank()) {
                        _products.value
                    } else {
                        _products.value.filter { it.title.contains(searchQuery, ignoreCase = true) }
                    }
                }
        }
    }

    fun onSearchQueryChanged(newQuery: String) {
        _query.value = newQuery
    }
}
