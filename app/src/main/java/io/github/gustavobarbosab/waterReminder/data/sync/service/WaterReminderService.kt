package io.github.gustavobarbosab.waterReminder.data.sync.service

import android.app.IntentService
import android.content.Intent
import io.github.gustavobarbosab.waterReminder.data.notification.WaterReminederNotificationUtil
import io.github.gustavobarbosab.waterReminder.data.storage.local.WaterAppPreference
import io.github.gustavobarbosab.waterReminder.data.storage.local.WaterAppPreferenceImpl
import io.github.gustavobarbosab.waterReminder.ui.MainActivity

class WaterReminderService : IntentService(WATER_SERVICE) {

    private val presenter = WaterServicePresenter(this)
    private val preferences: WaterAppPreference = WaterAppPreferenceImpl()

    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_REMINDER_USER -> presenter.rememberUser(preferences.getTotalWaterCups(this))
            ACTION_DISMISS_NOTIFICATION -> presenter.clearAllNotifications()
            ACTION_INCREMENT_WATER_COUNT -> presenter.incrementWaterCup(
                preferences.getTotalWaterCups(
                    this
                )
            )
        }
    }

    fun reminderUser(totalWaterCups: Int) {
        WaterReminederNotificationUtil.remindUser(this, totalWaterCups)
    }

    fun clearAllNotifications() {
        WaterReminederNotificationUtil.clearAllNotifications(this)
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    fun saveNewTotalWaterCups(totalCups: Int) {
        preferences.saveNewTotalWaterCups(this, totalCups)
        startActivity(MainActivity.newIntentUpdateTotal(this))
    }

    companion object {
        private const val WATER_SERVICE = "WATER_SERVICE"
        const val ACTION_REMINDER_USER: String = "ACTION_REMINDER_USER"
        const val ACTION_DISMISS_NOTIFICATION: String = "ACTION_DISMISS_NOTIFICATION"
        const val ACTION_INCREMENT_WATER_COUNT: String = "ACTION_INCREMENT_WATER_COUNT"
    }
}