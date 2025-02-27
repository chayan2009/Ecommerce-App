package com.example.ecommerce_app.core.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ecommerce_app.data.db.ProductDao
import com.example.ecommerce_app.domain.model.Product

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}
