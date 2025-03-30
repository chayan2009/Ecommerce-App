package com.example.ecommerce_app.di

import android.content.Context
import androidx.room.Room
import com.example.ecommerce_app.core.common.Constants
import com.example.ecommerce_app.core.database.AppDatabase
import com.example.ecommerce_app.core.database.MIGRATION_1_2
import com.example.ecommerce_app.core.datastore.UserPreferences
import com.example.ecommerce_app.core.datastore.UserPreferencesRepository
import com.example.ecommerce_app.data.db.CartDao
import com.example.ecommerce_app.data.db.FavouriteDao
import com.example.ecommerce_app.data.db.ProductDao
import com.example.ecommerce_app.data.repository.CartRepositoryImpl
import com.example.ecommerce_app.data.repository.FavouritesRepositoryImpl
import com.example.ecommerce_app.data.repository.ProductRepositoryImpl
import com.example.ecommerce_app.data.source.api.ProductApi
import com.example.ecommerce_app.domain.repository.CartRepository
import com.example.ecommerce_app.domain.repository.FavouriteRepository
import com.example.ecommerce_app.domain.repository.ProductRepository
import com.example.ecommerce_app.domain.usecase.GetCartsUseCase
import com.example.ecommerce_app.domain.usecase.GetFavouritesUseCase
import com.example.ecommerce_app.domain.usecase.GetProductsUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ProductApi =
        retrofit.create(ProductApi::class.java)


    @Provides
    @Singleton
    fun provideProductRepository(api: ProductApi,productDao: ProductDao): ProductRepository =
        ProductRepositoryImpl(api,productDao)


    @Provides
    @Singleton
    fun provideCartRepository(cartDao: CartDao): CartRepository {
        return CartRepositoryImpl(cartDao)
    }

    @Provides
    @Singleton
    fun provideFavouriteRepository(favouriteDao: FavouriteDao): FavouriteRepository {
        return FavouritesRepositoryImpl(favouriteDao)
    }

    @Provides
    @Singleton
    fun provideProductUseCase(repository: ProductRepository): GetProductsUseCase =
        GetProductsUseCase(repository)

    @Provides
    @Singleton
    fun provideCartUseCase(repository: CartRepository): GetCartsUseCase =
        GetCartsUseCase(repository)

    @Provides
    @Singleton
    fun provideFavouriteUseCase(repository: FavouriteRepository): GetFavouritesUseCase =
        GetFavouritesUseCase(repository)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "ecommerced"
        ) .addMigrations(MIGRATION_1_2)
            .build()
    }
    @Provides
    @Singleton
    fun provideProductDao(db: AppDatabase): ProductDao = db.productDao()

    @Provides
    @Singleton
    fun provideCartDao(database: AppDatabase): CartDao {
        return database.cartDao()
    }

    @Provides
    @Singleton
    fun provideFavouriteDao(database: AppDatabase): FavouriteDao {
        return database.favouriteDao()
    }

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(@ApplicationContext context: Context): UserPreferencesRepository {
        return UserPreferencesRepository(context)
    }
}
