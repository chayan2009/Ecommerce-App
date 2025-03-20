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

    val totalCount: StateFlow<Any> = _carts.map { carts ->
        carts.sumOf { it.quantity }
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
            val currentList = _carts.value.toMutableList()
            val index = currentList.indexOfFirst { it.id == cart.id }
            if (index != -1) {
                val updatedItem = currentList[index].copy(quantity = currentList[index].quantity + 1)
                currentList[index] = updatedItem
            } else {
                currentList.add(cart.copy(quantity = 1))
            }
            _carts.value = currentList
        }
    }

    fun removeCartItem(id: Int) {
        viewModelScope.launch {
            val currentList = _carts.value.toMutableList()
            val index = currentList.indexOfFirst { it.id == id }
            if (index != -1) {
                val currentItem = currentList[index]
                if (currentItem.quantity > 1) {
                    currentList[index] = currentItem.copy(quantity = currentItem.quantity - 1)
                } else {
                    currentList.removeAt(index)
                }
            }
            _carts.value = currentList
        }
    }
}


