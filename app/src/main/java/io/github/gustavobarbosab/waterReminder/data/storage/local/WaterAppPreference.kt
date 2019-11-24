package io.github.gustavobarbosab.waterReminder.data.storage.local

import io.reactivex.Observable

interface WaterAppPreference {
    fun getTotalWaterCups(): Int
    fun saveNewTotalWaterCups(total: Int)
}