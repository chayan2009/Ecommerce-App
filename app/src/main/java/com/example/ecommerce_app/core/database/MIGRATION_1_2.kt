package com.example.ecommerce_app.core.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(9, 10) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE Product ADD COLUMN sync INTEGER DEFAULT 0 NOT NULL")
    }
}
