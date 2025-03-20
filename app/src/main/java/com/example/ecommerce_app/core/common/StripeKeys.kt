package com.example.ecommerce_app.core.common

import android.content.Context
import com.example.ecommerce_app.R

object StripeKeys {
    fun getPublishableKey(context: Context): String {
        return context.getString(R.string.stripe_publishable_key)
    }

    fun getSecretKey(context: Context): String {
        return context.getString(R.string.stripe_secret_key)
    }
}
