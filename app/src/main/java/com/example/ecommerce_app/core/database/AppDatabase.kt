package com.example.ecommerce_app.core.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ecommerce_app.data.db.CartDao
import com.example.ecommerce_app.data.db.ProductDao
import com.example.ecommerce_app.data.source.dto.CartEntity
import com.example.ecommerce_app.data.source.dto.ProductEntity

@Database(entities = [ProductEntity::class,CartEntity::class], version = 6, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
}
