package io.github.gustavobarbosab.waterReminder.data.sync.service

class WaterServicePresenter(
    private var waterService: WaterReminderService?) {

    fun incrementWaterCup(totalCups: Int) {
        waterService?.saveNewTotalWaterCups(totalCups + 1)
        clearAllNotifications()
    }

    fun rememberUser(totalWaterCups: Int) {
        waterService?.reminderUser(totalWaterCups)
    }

    fun clearAllNotifications() {
        waterService?.clearAllNotifications()
    }

    fun detach() {
        waterService = null
    }
}