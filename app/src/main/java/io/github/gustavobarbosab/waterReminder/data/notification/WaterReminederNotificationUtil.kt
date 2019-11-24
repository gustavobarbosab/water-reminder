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


class WaterReminederNotificationUtil {
    companion object {
        /*
     * This notification ID can be used to access our notification after we've displayed it. This
     * can be handy when we need to cancel the notification, or perhaps update it. This number is
     * arbitrary and can be set to whatever you like. 1138 is in no way significant.
     */
        private const val WATER_REMINDER_NOTIFICATION_ID = 1138
        /**
         * This pending intent id is used to uniquely reference the pending intent
         */
        private const val WATER_REMINDER_PENDING_INTENT_ID = 3417
        /**
         * This notification channel id is used to link notifications to this channel
         */
        private const val WATER_REMINDER_NOTIFICATION_CHANNEL_ID = "reminder_notification_channel"
        private val ACTION_DRINK_PENDING_INTENT_ID = 1
        private val ACTION_IGNORE_PENDING_INTENT_ID = 14


        fun clearAllNotifications(context: Context) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancelAll()
        }

        // COMPLETED (7) Create a method called remindUser which takes a Context.
        // This method will create a notification for charging. It might be helpful
        // to take a look at this guide to see an example of what the code in this method will look like:
        // https://developer.android.com/training/notify-user/build-notification.html
        fun remindUser(context: Context, totalCups: Int) {
            // COMPLETED (8) Get the NotificationManager using context.getSystemService
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            // COMPLETED (9) Create a notification channel for Android O devices
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val mChannel = NotificationChannel(
                    WATER_REMINDER_NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.main_notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH
                )
                notificationManager.createNotificationChannel(mChannel)
            }
            // COMPLETED (10) In the remindUser method use NotificationCompat.Builder to create a notification
            // that:
            // - has a color of R.color.colorPrimary - use ContextCompat.getColor to get a compatible color
            // - has ic_drink_notification as the small icon
            // - uses icon returned by the largeIcon helper method as the large icon
            // - sets the title to the charging_reminder_notification_title String resource
            // - sets the text to the charging_reminder_notification_body String resource
            // - sets the style to NotificationCompat.BigTextStyle().bigText(text)
            // - sets the notification defaults to vibrate
            // - uses the content intent returned by the contentIntent helper method for the contentIntent
            // - automatically cancels the notification when the notification is clicked
            val notificationBuilder =
                NotificationCompat.Builder(context, WATER_REMINDER_NOTIFICATION_CHANNEL_ID)
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    //.setLargeIcon(largeIcon(context))
                    .setContentTitle(context.getString(R.string.charging_reminder_notification_title))
                    .setContentText(context.getString(R.string.charging_reminder_notification_body, totalCups))
                    .setStyle(
                        NotificationCompat.BigTextStyle().bigText(
                            context.getString(R.string.charging_reminder_notification_body, totalCups)
                        )
                    )
                    .addAction(drinkWaterAction(context))
                    .addAction(ignoreReminderAction(context))
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setContentIntent(contentIntent(context))
                    .setAutoCancel(true)

            // COMPLETED (11) If the build version is greater than or equal to JELLY_BEAN and less than OREO,
            // set the notification's priority to PRIORITY_HIGH.
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                notificationBuilder.priority = NotificationCompat.PRIORITY_HIGH
            }

            // COMPLETED (12) Trigger the notification by calling notify on the NotificationManager.
            // Pass in a unique ID of your choosing for the notification and notificationBuilder.build()
            notificationManager.notify(WATER_REMINDER_NOTIFICATION_ID, notificationBuilder.build())
        }

        private fun drinkWaterAction(context: Context): NotificationCompat.Action {
            // COMPLETED (12) Create an Intent to launch WaterReminderIntentService
            val incrementWaterCountIntent = Intent(context, WaterReminderService::class.java)
            // COMPLETED (13) Set the action of the intent to designate you want to increment the water count
            incrementWaterCountIntent.action = WaterReminderService.ACTION_INCREMENT_WATER_COUNT
            // COMPLETED (14) Create a PendingIntent from the intent to launch WaterReminderIntentService
            val incrementWaterPendingIntent = PendingIntent.getService(
                context,
                ACTION_DRINK_PENDING_INTENT_ID,
                incrementWaterCountIntent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )
            // COMPLETED (15) Create an Action for the user to tell us they've had a glass of water
            // COMPLETED (16) Return the action
            return NotificationCompat.Action(
                R.drawable.ic_launcher_foreground,
                "I did it!",
                incrementWaterPendingIntent
            )
        }

        private fun ignoreReminderAction(context: Context): NotificationCompat.Action {
            // COMPLETED (6) Create an Intent to launch WaterReminderIntentService
            val ignoreReminderIntent = Intent(context, WaterReminderService::class.java)
            // COMPLETED (7) Set the action of the intent to designate you want to dismiss the notification
            ignoreReminderIntent.action = WaterReminderService.ACTION_DISMISS_NOTIFICATION
            // COMPLETED (8) Create a PendingIntent from the intent to launch WaterReminderIntentService
            val ignoreReminderPendingIntent = PendingIntent.getService(
                context,
                ACTION_IGNORE_PENDING_INTENT_ID,
                ignoreReminderIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            // COMPLETED (9) Create an Action for the user to ignore the notification (and dismiss it)
            // COMPLETED (10) Return the action
            return NotificationCompat.Action(
                R.drawable.ic_launcher_foreground,
                "No, thanks.",
                ignoreReminderPendingIntent
            )
        }

        // COMPLETED (1) Create a helper method called contentIntent with a single parameter for a Context. It
        // should return a PendingIntent. This method will create the pending intent which will trigger when
        // the notification is pressed. This pending intent should open up the MainActivity.
        private fun contentIntent(context: Context): PendingIntent {
            // COMPLETED (2) Create an intent that opens up the MainActivity
            val startActivityIntent = Intent(context, MainActivity::class.java)
            // COMPLETED (3) Create a PendingIntent using getActivity that:
            // - Take the context passed in as a parameter
            // - Takes an unique integer ID for the pending intent (you can create a constant for
            //   this integer above
            // - Takes the intent to open the MainActivity you just created; this is what is triggered
            //   when the notification is triggered
            // - Has the flag FLAG_UPDATE_CURRENT, so that if the intent is created again, keep the
            // intent but update the data
            return PendingIntent.getActivity(
                context,
                WATER_REMINDER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }


        // COMPLETED (4) Create a helper method called largeIcon which takes in a Context as a parameter and
        // returns a Bitmap. This method is necessary to decode a bitmap needed for the notification.
        private fun largeIcon(context: Context): Bitmap {
            // COMPLETED (5) Get a Resources object from the context.
            val res = context.resources
            // COMPLETED (6) Create and return a bitmap using BitmapFactory.decodeResource, passing in the
            // resources object and R.drawable.ic_local_drink_black_24px
            return BitmapFactory.decodeResource(res, R.drawable.ic_launcher_foreground)
        }
    }
}