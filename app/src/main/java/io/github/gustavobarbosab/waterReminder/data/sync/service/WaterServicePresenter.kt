package io.github.gustavobarbosab.waterReminder.data.sync.service

import io.github.gustavobarbosab.waterReminder.data.repository.WaterReminderRepository

class WaterServicePresenter(
    private var waterService: WaterReminderServiceContract.Service?,
    private val waterReminderRepository: WaterReminderRepository
) : WaterReminderServiceContract.Presenter {
    // TODO criar contrato

    override fun incrementWaterCup() {
        waterReminderRepository.incrementWaterCup()
        waterService?.notifyWaterCupUpdate()
        clearAllNotifications()
    }

    override fun clearAllNotifications() {
        waterService?.clearAllNotifications()
    }

    override fun detach() {
        waterService = null
    }
}