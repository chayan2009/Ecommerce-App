package com.example.ecommerce_app

import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.ecommerce_app.core.common.ProductSyncWorker
import com.stripe.android.PaymentConfiguration
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class MyApp : Application(){
    override fun onCreate() {
        super.onCreate()
        scheduleProductSync(this)
    }

    private fun scheduleProductSync(myApp: MyApp) {
        val workRequest = PeriodicWorkRequestBuilder<ProductSyncWorker>(
            15, TimeUnit.MINUTES
        ).setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "ProductSyncWork",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}
