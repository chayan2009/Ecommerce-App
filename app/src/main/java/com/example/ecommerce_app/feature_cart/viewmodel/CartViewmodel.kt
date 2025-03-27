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

    val totalAmount: StateFlow<Any> = _carts.map { carts ->
        carts.sumOf { it.price }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)

    val totalCount: StateFlow<Any> = _carts.map { carts ->
        carts.sumOf { it.quantity  }
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

    fun addCartItem(cart: Cart) {
        viewModelScope.launch {
            val updatedList = _carts.value.map {
                if (it.id == cart.id) it.copy(quantity = it.quantity + 1)
                else it
            }.toMutableList()

            if (updatedList.none { it.id == cart.id }) {
                updatedList.add(cart.copy(quantity = 1))
            }

            _carts.value = updatedList.toList()
            getCartsUseCase.addCartItem(cart)
        }
    }

    fun removeCartItem(id: Int) {
        viewModelScope.launch {
            val updatedList = _carts.value.mapNotNull {
                when {
                    it.id == id && it.quantity > 1 -> it.copy(quantity = it.quantity - 1)
                    it.id == id -> null
                    else -> it
                }
            }

            _carts.value = updatedList
            getCartsUseCase.removeCartItem(id)
        }
    }


}


