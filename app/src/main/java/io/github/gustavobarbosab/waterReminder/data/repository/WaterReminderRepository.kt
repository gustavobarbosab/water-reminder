package io.github.gustavobarbosab.waterReminder.data.repository

interface WaterReminderRepository {
    fun getWaterCups(): Int
    fun incrementWaterCup()
}