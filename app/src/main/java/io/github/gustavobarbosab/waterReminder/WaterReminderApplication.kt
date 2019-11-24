package io.github.gustavobarbosab.waterReminder

import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import io.github.gustavobarbosab.waterReminder.data.sync.WaterReminderWorker
import java.util.concurrent.TimeUnit

class WaterReminderApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        configReminderWork()
    }

    private fun configReminderWork() {
        val constraints = Constraints.Builder()
            .setRequiresCharging(false)
            .setRequiresBatteryNotLow(true)
            .build()

        val reminderUser =
            PeriodicWorkRequest.Builder(WaterReminderWorker::class.java, 15, TimeUnit.MINUTES)
                .setInitialDelay(10,TimeUnit.SECONDS)
                .setConstraints(constraints)
                .build()

        WorkManager
            .getInstance(this)
            .enqueueUniquePeriodicWork("nome", ExistingPeriodicWorkPolicy.REPLACE,reminderUser)
    }
}