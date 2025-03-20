package com.example.ecommerce_app.data.repository

import android.content.Context
import android.util.Log
import javax.inject.Inject

class StripeRepository @Inject constructor(context: Context) {

    fun createPaymentIntent(): String {
        // Simulate API request (Replace with actual backend call)
        val clientSecret = "sk_test_IqP2vxUHKFk5pUEleObrXd6H"
        Log.d("StripeRepository", "Client Secret: $clientSecret")
        return clientSecret
    }
}
