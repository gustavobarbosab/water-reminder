package io.github.gustavobarbosab.waterReminder.data.sync

import android.content.Context
import android.content.Intent
import androidx.work.Worker
import androidx.work.WorkerParameters
import io.github.gustavobarbosab.waterReminder.data.sync.service.WaterReminderService

class WaterReminderWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        val reminderService = Intent(applicationContext, WaterReminderService::class.java)
        reminderService.action = WaterReminderService.ACTION_REMINDER_USER
        applicationContext.startService(reminderService)
        return Result.success()
    }
}