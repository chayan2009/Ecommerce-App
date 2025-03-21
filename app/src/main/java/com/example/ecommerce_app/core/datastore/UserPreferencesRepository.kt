package com.example.ecommerce_app.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "ecommerce-pref")

class UserPreferencesRepository(private val context: Context) : UserPreferences {

    companion object {
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val PASSWORD_KEY = stringPreferencesKey("password")
        private val AUTO_LOGIN_KEY = booleanPreferencesKey("auto_login")
    }

    override suspend fun saveUserCredentials(username: String, password: String) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = username
            preferences[PASSWORD_KEY] = password
            preferences[AUTO_LOGIN_KEY] = true
        }
    }

    override suspend fun clearCredentials() {
        context.dataStore.edit { preferences ->
            preferences.remove(USERNAME_KEY)
            preferences.remove(PASSWORD_KEY)
            preferences[AUTO_LOGIN_KEY] = false
        }
    }

    override fun getSavedUsername(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[USERNAME_KEY]
        }
    }

    override fun getSavedPassword(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[PASSWORD_KEY]
        }
    }

    override fun isAutoLoginEnabled(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[AUTO_LOGIN_KEY] ?: false
        }
    }

    override suspend fun setAutoLoginEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[AUTO_LOGIN_KEY] = enabled
        }
    }
}
