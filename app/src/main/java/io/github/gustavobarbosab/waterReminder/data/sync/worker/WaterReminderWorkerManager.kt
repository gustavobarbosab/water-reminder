package io.github.gustavobarbosab.waterReminder.data.sync.worker

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class WaterReminderWorkerManager {

    private val minutes = TimeUnit.MINUTES

    fun startWorker(
        context: Context,
        periodic: ExistingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.REPLACE
    ) {
        val constraints = constraints()
        val reminderUser = periodicWorkRequest(constraints)

        WorkManager
            .getInstance(context)
            .enqueueUniquePeriodicWork(
                WORKER_NAME,
                periodic,
                reminderUser
            )
    }

    fun stopWorker(context: Context) {
        WorkManager
            .getInstance(context)
            .cancelAllWorkByTag(WORKER_NAME)
    }

    private fun constraints(): Constraints =
        Constraints
            .Builder()
            .setRequiresBatteryNotLow(true)
            .build()

    private fun periodicWorkRequest(constraints: Constraints): PeriodicWorkRequest =
        PeriodicWorkRequest
            .Builder(WaterReminderWorker::class.java, SIXTY, minutes)
           // .setInitialDelay(SIXTY, minutes)
            .setConstraints(constraints)
            .build()


    companion object {
        private const val WORKER_NAME = "WaterReminderWorker"
        private const val SIXTY = 60L
    }
}