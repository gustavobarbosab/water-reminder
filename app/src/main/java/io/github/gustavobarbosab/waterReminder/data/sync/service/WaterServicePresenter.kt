package io.github.gustavobarbosab.waterReminder.data.sync.service

import io.github.gustavobarbosab.waterReminder.data.repository.WaterRepository

class WaterServicePresenter(
    private var waterService: WaterReminderService?,
    private val waterRepository: WaterRepository
) {

    fun incrementWaterCup() {
        val total = waterRepository.getTotalWaterCups()
        waterRepository.saveNewTotalWaterCups(total + 1)
        clearAllNotifications()
    }

    fun detach() {
        waterService = null
    }

    fun rememberUser() {
        waterService?.reminderUser(waterRepository.getTotalWaterCups())
    }

    fun clearAllNotifications() {
        waterService?.clearAllNotifications()
    }
}