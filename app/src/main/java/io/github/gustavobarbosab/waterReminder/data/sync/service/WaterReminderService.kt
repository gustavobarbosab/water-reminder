package io.github.gustavobarbosab.waterReminder.data.sync.service

import android.app.IntentService
import android.content.Intent
import io.github.gustavobarbosab.waterReminder.data.notification.WaterReminederNotificationUtil
import io.github.gustavobarbosab.waterReminder.data.repository.WaterRepository

class WaterReminderService : IntentService(WATER_SERVICE) {

    private val presenter = WaterServicePresenter(this, WaterRepository)

    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_REMINDER_USER -> presenter.rememberUser()
            ACTION_DISMISS_NOTIFICATION -> presenter.clearAllNotifications()
            ACTION_INCREMENT_WATER_COUNT -> presenter.incrementWaterCup()
        }
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    fun reminderUser(totalWaterCups: Int) {
        WaterReminederNotificationUtil.remindUser(this,totalWaterCups)
    }

    fun clearAllNotifications() {
        WaterReminederNotificationUtil.clearAllNotifications(this)
    }

    companion object {
        private const val WATER_SERVICE = "WATER_SERVICE"
        const val ACTION_REMINDER_USER: String = "ACTION_REMINDER_USER"
        const val ACTION_DISMISS_NOTIFICATION: String = "ACTION_DISMISS_NOTIFICATION"
        const val ACTION_INCREMENT_WATER_COUNT: String = "ACTION_INCREMENT_WATER_COUNT"
    }
}