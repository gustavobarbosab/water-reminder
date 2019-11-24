package io.github.gustavobarbosab.waterReminder.data.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import io.github.gustavobarbosab.waterReminder.R
import io.github.gustavobarbosab.waterReminder.data.sync.service.WaterReminderService
import io.github.gustavobarbosab.waterReminder.ui.MainActivity


class WaterReminderNotificationUtil {
    companion object {
        private const val WATER_REMINDER_NOTIFICATION_ID = 1138
        private const val WATER_REMINDER_PENDING_INTENT_ID = 3417
        private const val WATER_REMINDER_NOTIFICATION_CHANNEL_ID = "reminder_notification_channel"
        private const val ACTION_DRINK_PENDING_INTENT_ID = 1
        private const val ACTION_IGNORE_PENDING_INTENT_ID = 14

        fun clearAllNotifications(context: Context) {
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancelAll()
        }

        fun remindUser(context: Context, totalCups: Int) {
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val mChannel = NotificationChannel(
                    WATER_REMINDER_NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.main_notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH
                )
                notificationManager.createNotificationChannel(mChannel)
            }

            val notificationBuilder =
                NotificationCompat.Builder(context, WATER_REMINDER_NOTIFICATION_CHANNEL_ID)
                    .setColor(ContextCompat.getColor(context, R.color.colorAccent))
                    .setSmallIcon(R.drawable.ic_stat_drop)
                    .setLargeIcon(largeIcon(context))
                    .setContentTitle(context.getString(R.string.charging_reminder_notification_title))
                    .setContentText(
                        context.resources.getQuantityString(
                            R.plurals.charging_reminder_notification_body,
                            totalCups,
                            totalCups
                        )
                    )
                    .setStyle(
                        NotificationCompat.BigTextStyle().bigText(
                            context.resources.getQuantityString(
                                R.plurals.charging_reminder_notification_body,
                                totalCups,
                                totalCups
                            )
                        )
                    )
                    .addAction(drinkWaterAction(context))
                    .addAction(ignoreReminderAction(context))
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setContentIntent(contentIntent(context))
                    .setAutoCancel(true)

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                notificationBuilder.priority = NotificationCompat.PRIORITY_HIGH
            }

            notificationManager.notify(WATER_REMINDER_NOTIFICATION_ID, notificationBuilder.build())
        }

        private fun drinkWaterAction(context: Context): NotificationCompat.Action {
            val incrementWaterCountIntent = WaterReminderService.newIntentActionWaterCount(context)

            val incrementWaterPendingIntent = PendingIntent.getService(
                context,
                ACTION_DRINK_PENDING_INTENT_ID,
                incrementWaterCountIntent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )

            return NotificationCompat.Action(
                R.drawable.ic_check,
                context.getString(R.string.did_it),
                incrementWaterPendingIntent
            )
        }

        private fun ignoreReminderAction(context: Context): NotificationCompat.Action {
            val ignoreReminderIntent = WaterReminderService.newIntentActionDismiss(context)

            val ignoreReminderPendingIntent = PendingIntent.getService(
                context,
                ACTION_IGNORE_PENDING_INTENT_ID,
                ignoreReminderIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            return NotificationCompat.Action(
                R.drawable.ic_close,
                context.getString(R.string.no_thanks),
                ignoreReminderPendingIntent
            )
        }

        private fun contentIntent(context: Context): PendingIntent =
            PendingIntent.getActivity(
                context,
                WATER_REMINDER_PENDING_INTENT_ID,
                Intent(context, MainActivity::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT
            )


        private fun largeIcon(context: Context): Bitmap {
            val res = context.resources
            return BitmapFactory.decodeResource(res, R.drawable.ic_stat_drop)
        }
    }
}