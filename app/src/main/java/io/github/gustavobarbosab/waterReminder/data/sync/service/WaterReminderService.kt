package io.github.gustavobarbosab.waterReminder.data.sync.service

import android.app.IntentService
import android.content.Context
import android.content.Intent
import io.github.gustavobarbosab.waterReminder.data.notification.WaterReminderNotificationUtil
import io.github.gustavobarbosab.waterReminder.data.repository.WaterReminderRepositoryImpl
import io.github.gustavobarbosab.waterReminder.data.storage.local.WaterAppPreferenceImpl
import io.github.gustavobarbosab.waterReminder.ui.MainActivity

class WaterReminderService : IntentService(WATER_SERVICE), WaterReminderServiceContract.Service {

    private val presenter: WaterReminderServiceContract.Presenter by lazy {
        WaterServicePresenter(this, WaterReminderRepositoryImpl(WaterAppPreferenceImpl))
    }

    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_INCREMENT_WATER_COUNT -> presenter.incrementWaterCup()
            ACTION_DISMISS_NOTIFICATION -> presenter.clearAllNotifications()
        }
    }

    override fun clearAllNotifications() {
        WaterReminderNotificationUtil.clearAllNotifications(this)
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    override fun notifyWaterCupUpdate() {
        sendBroadcast(Intent(MainActivity.UPDATE_TOTAL))
    }

    companion object {
        private const val WATER_SERVICE = "WATER_SERVICE"
        const val ACTION_DISMISS_NOTIFICATION: String = "ACTION_DISMISS_NOTIFICATION"
        const val ACTION_INCREMENT_WATER_COUNT: String = "ACTION_INCREMENT_WATER_COUNT"

        fun newIntentActionDismiss(context: Context) =
            Intent(context, WaterReminderService::class.java)
                .apply {
                    action = ACTION_DISMISS_NOTIFICATION
                }

        fun newIntentActionWaterCount(context: Context) =
            Intent(context, WaterReminderService::class.java)
                .apply {
                    action = ACTION_INCREMENT_WATER_COUNT
                }
    }
}