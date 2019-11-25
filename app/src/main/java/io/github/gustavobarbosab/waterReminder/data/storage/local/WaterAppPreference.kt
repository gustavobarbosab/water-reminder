package io.github.gustavobarbosab.waterReminder.data.storage.local

interface WaterAppPreference {
    fun getTotalWaterCups(): Int
    fun saveNewTotalWaterCups(total: Int)
}