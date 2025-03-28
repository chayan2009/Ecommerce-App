package com.example.ecommerce_app.feature_login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce_app.core.common.ValidationUtil
import com.example.ecommerce_app.core.datastore.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _usernameError = MutableStateFlow<String?>(null)
    val usernameError: StateFlow<String?> = _usernameError

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError

    private val _loginState = MutableStateFlow<Boolean?>(null)
    val loginState: StateFlow<Boolean?> = _loginState

    fun onUsernameChange(newUsername: String) {
        _username.value = newUsername
        _usernameError.value = null
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        _passwordError.value = null
    }

    fun login(username: String = _username.value, password: String = _password.value, autoLogin: Boolean = false) {
        viewModelScope.launch {
            val usernameValue = username.trim()
            val passwordValue = password.trim()
            val isSuccess = performLogin(usernameValue, passwordValue)
            _loginState.value = isSuccess
            if (isSuccess) {
                saveUserCredentials(usernameValue, passwordValue)
            }
        }
    }

    private suspend fun performLogin(username: String, password: String): Boolean {
        val savedUsername = userPreferences.getSavedUsername().firstOrNull()
        val savedPassword = userPreferences.getSavedPassword().firstOrNull()

        return if (savedUsername != null && savedPassword != null) {
            username == savedUsername && password == savedPassword
        } else {
            username == "chayan" && password == "chayan123"
        }
    }

    private fun saveUserCredentials(username: String, password: String) {
        viewModelScope.launch {
            userPreferences.saveUserCredentials(username, password)
            userPreferences.setAutoLoginEnabled(true)
        }
    }

    fun logout() {
        viewModelScope.launch {
            userPreferences.clearCredentials()
            _loginState.value = false
        }
    }
}
