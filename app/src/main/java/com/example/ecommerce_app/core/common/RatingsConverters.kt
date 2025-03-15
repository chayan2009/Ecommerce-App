package com.example.ecommerce_app.core.common

import androidx.room.TypeConverter
import com.example.ecommerce_app.data.source.dto.Rating
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RatingsConverters {

    private val gson = Gson()

    @TypeConverter
    fun fromRating(rating: Rating): String {
        return gson.toJson(rating)
    }

    @TypeConverter
    fun toRating(value: String): Rating {
        val type = object : TypeToken<Rating>() {}.type
        return gson.fromJson(value, type)
    }
}
