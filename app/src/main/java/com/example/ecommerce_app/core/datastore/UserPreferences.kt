package com.example.ecommerce_app.core.datastore

import kotlinx.coroutines.flow.Flow

interface UserPreferences {
    suspend fun saveUserCredentials(username: String, password: String)
    suspend fun clearCredentials()
    fun getSavedUsername(): Flow<String?>
    fun getSavedPassword(): Flow<String?>
    fun isAutoLoginEnabled(): Flow<Boolean>
    suspend fun setAutoLoginEnabled(enabled: Boolean)
}
