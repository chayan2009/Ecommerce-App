package com.example.ecommerce_app.feature_cart.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce_app.domain.model.Cart
import com.example.ecommerce_app.domain.usecase.GetCartsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewmodel @Inject constructor(
    private val getCartsUseCase: GetCartsUseCase
) : ViewModel() {

    private val _carts = MutableStateFlow<List<Cart>>(emptyList())
    val carts: StateFlow<List<Cart>> = _carts.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val totalItems: StateFlow<Any> = _carts.map { carts ->
        carts.sumOf { it.price }

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)

    init {
        getCarts()
    }

    fun getCarts() {
        viewModelScope.launch {
            getCartsUseCase.getCarts().collect { carts ->
                _carts.value = carts
                _isLoading.value = false
            }
        }
    }

    fun addCartItem(cartItem: Cart) {
        viewModelScope.launch {
            val existingItem = _carts.value.find { it.id == cartItem.id }
            if (existingItem != null) {
                _carts.value = _carts.value.map {
                    if (it.id == cartItem.id) it.copy(quantity = it.quantity + 1) else it
                }
            } else {
                _carts.value += cartItem.copy(quantity = 1)
            }
        }
    }

    fun removeCartItem(id: Int) {
        viewModelScope.launch {
            _carts.value = _carts.value.mapNotNull {
                when {
                    it.id == id && it.quantity > 1 -> it.copy(quantity = it.quantity - 1)
                    it.id == id -> null
                    else -> it
                }
            }
        }
    }
}


