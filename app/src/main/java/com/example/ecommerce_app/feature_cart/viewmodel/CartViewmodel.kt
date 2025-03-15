package com.example.ecommerce_app.feature_cart.viewmodel
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce_app.domain.model.Cart
import com.example.ecommerce_app.domain.model.Product
import com.example.ecommerce_app.domain.usecase.GetCartsUseCase
import com.example.ecommerce_app.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewmodel @Inject constructor(
    private val getCartsUseCase: GetCartsUseCase
) : ViewModel() {

    private val _carts = MutableStateFlow<List<Cart>>(emptyList())
    val carts: StateFlow<List<Cart>> = _carts

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading


    init {
        getCarts()
    }

    fun getCarts() {
        viewModelScope.launch {
            getCartsUseCase.getCarts().collect { carts ->
                Log.d("CartDebug", "Retrieved carts: $carts")
                _carts.value = carts
            }
        }
    }

    fun addCartItem(cartItem: Cart) {

    }

    fun removeCartItem(id: Int) {

    }

}

