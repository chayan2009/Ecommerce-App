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

    init {
        checkAutoLogin()
    }

    private fun checkAutoLogin() {
        viewModelScope.launch {
            userPreferences.isAutoLoginEnabled()
                .collect { isAutoLogin ->
                    if (isAutoLogin) {
                        userPreferences.getSavedUsername().collect { savedUsername ->
                            userPreferences.getSavedPassword().collect { savedPassword ->
                                if (!savedUsername.isNullOrEmpty() && !savedPassword.isNullOrEmpty()) {
                                    login(savedUsername, savedPassword, autoLogin = true)
                                }
                            }
                        }
                    }
                }
        }
    }

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

            if (!autoLogin) {
                _usernameError.value = ValidationUtil.validateUsername(usernameValue)
                _passwordError.value = ValidationUtil.validatePassword(passwordValue)

                if (_usernameError.value != null || _passwordError.value != null) {
                    _loginState.value = false
                    return@launch
                }
            }

            val isSuccess = performLogin(usernameValue, passwordValue)
            _loginState.value = isSuccess
            if (isSuccess) {
                saveUserCredentials(usernameValue, passwordValue)
            }
        }
    }

    private suspend fun performLogin(username: String, password: String): Boolean {
        return username == "chayan" && password == "chayan123"
    }

    private fun saveUserCredentials(username: String, password: String) {
        viewModelScope.launch {
            userPreferences.saveUserCredentials(username, password)
        }
    }

    fun logout() {
        viewModelScope.launch {
            userPreferences.clearCredentials()
            _loginState.value = false
        }
    }
}
