package io.github.gustavobarbosab.waterReminder.data.sync.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import io.github.gustavobarbosab.waterReminder.data.notification.WaterReminederNotificationUtil
import io.github.gustavobarbosab.waterReminder.data.storage.local.WaterAppPreference
import io.github.gustavobarbosab.waterReminder.data.storage.local.WaterAppPreferenceImpl

class WaterReminderWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    private val waterPreferences: WaterAppPreference = WaterAppPreferenceImpl()

    override fun doWork(): Result {
        val totalWaterCups = waterPreferences.getTotalWaterCups(applicationContext)
        WaterReminederNotificationUtil.remindUser(applicationContext, totalWaterCups)
        return Result.success()
    }
}