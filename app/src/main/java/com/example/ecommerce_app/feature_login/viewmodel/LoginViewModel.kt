package com.example.ecommerce_app.feature_login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce_app.core.common.ValidationUtil
import com.example.ecommerce_app.domain.model.User
import com.example.ecommerce_app.domain.usecase.LoginUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase
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
        _usernameError.value = ValidationUtil.validateName(newUsername)
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        _passwordError.value = ValidationUtil.validatePassword(newPassword)
    }

    fun login() {

        _usernameError.value = ValidationUtil.validateName(_username.value)
        _passwordError.value = ValidationUtil.validatePassword(_password.value)

        if (_usernameError.value == null && _passwordError.value == null) {
            viewModelScope.launch {
                val result = loginUserUseCase(User(_username.value, _password.value))
                _loginState.value = result
            }
        }
    }

    fun clearErrors() {
        _usernameError.value = null
        _passwordError.value = null
        _loginState.value = null
    }
}
