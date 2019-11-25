package io.github.gustavobarbosab.waterReminder.data.repository

import io.github.gustavobarbosab.waterReminder.data.storage.local.WaterAppPreference

class WaterReminderRepositoryImpl(
    private val preference: WaterAppPreference
) : WaterReminderRepository {

    override fun getWaterCups(): Int = preference.getTotalWaterCups()

    override fun incrementWaterCup() {
        val total = preference.getTotalWaterCups()
        preference.saveNewTotalWaterCups(total + 1)
    }
}