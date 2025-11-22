package com.example.verodigitalsolutionandroidtask

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.verodigitalsolutionandroidtask.ui.worker.RefreshWorker
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class App : Application(){

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }

        val request = PeriodicWorkRequestBuilder<RefreshWorker>(15, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                "refresh_worker",
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
    }

}