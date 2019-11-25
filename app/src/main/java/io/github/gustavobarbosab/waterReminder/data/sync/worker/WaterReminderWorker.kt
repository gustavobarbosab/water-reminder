package io.github.gustavobarbosab.waterReminder.data.sync.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import io.github.gustavobarbosab.waterReminder.data.notification.WaterReminderNotificationUtil
import io.github.gustavobarbosab.waterReminder.data.storage.local.WaterAppPreferenceImpl

class WaterReminderWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val totalWaterCups = WaterAppPreferenceImpl.getTotalWaterCups()
        WaterReminderNotificationUtil.remindUser(applicationContext, totalWaterCups)
        return Result.success()
    }
}