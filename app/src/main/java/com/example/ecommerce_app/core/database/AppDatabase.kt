package com.example.ecommerce_app.core.database


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ecommerce_app.core.common.RatingsConverters
import com.example.ecommerce_app.data.db.CartDao
import com.example.ecommerce_app.data.db.FavouriteDao
import com.example.ecommerce_app.data.db.ProductDao
import com.example.ecommerce_app.data.source.dto.CartEntity
import com.example.ecommerce_app.data.source.dto.FavouriteEntity
import com.example.ecommerce_app.data.source.dto.ProductEntity

@Database(entities = [ProductEntity::class,CartEntity::class,FavouriteEntity::class], version = 8, exportSchema = false)
@TypeConverters(RatingsConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
    abstract fun favouriteDao():FavouriteDao
}
