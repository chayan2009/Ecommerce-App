package com.example.ecommerce_app.di

import com.example.ecommerce_app.core.datastore.UserPreferences
import com.example.ecommerce_app.core.datastore.UserPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesModule {

    @Binds
    @Singleton
    abstract fun bindUserPreferences(
        userPreferencesRepository: UserPreferencesRepository
    ): UserPreferences
}
