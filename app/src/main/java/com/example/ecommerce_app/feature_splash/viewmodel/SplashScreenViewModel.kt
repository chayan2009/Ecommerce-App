package com.example.ecommerce_app.feature_splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce_app.core.datastore.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _loginState = MutableStateFlow<Boolean?>(false)
    val loginState: StateFlow<Boolean?> = _loginState

    init {
        viewModelScope.launch {
            checkAutoLogin()
        }
    }

    private suspend fun checkAutoLogin() {
        userPreferences.isAutoLoginEnabled().collect { isEnabled ->
            _loginState.value = isEnabled
        }
    }
}