package io.github.gustavobarbosab.waterReminder.data.sync.service

interface WaterReminderServiceContract {
    interface Service {
        fun notifyWaterCupUpdate()
        fun clearAllNotifications()
    }

    interface Presenter {
        fun clearAllNotifications()
        fun incrementWaterCup()
        fun detach()
    }
}