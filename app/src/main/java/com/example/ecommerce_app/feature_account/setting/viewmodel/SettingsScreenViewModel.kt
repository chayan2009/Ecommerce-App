package com.example.ecommerce_app.feature_account.setting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce_app.core.datastore.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel@Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {

    init {
        logout()
    }

    fun logout() {
        viewModelScope.launch {
            userPreferences.clearCredentials()
        }
    }
}