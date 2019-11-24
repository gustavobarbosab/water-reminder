package io.github.gustavobarbosab.waterReminder.data.storage.local

import android.content.Context
import android.content.SharedPreferences

object WaterAppPreferenceImpl : WaterAppPreference {

    private const val WATER_APPLICATION_PREFERENCE = "WATER_APPLICATION_PREFERENCE"
    private const val KEY_WATER_COUNT = "water-count"
    const val KEY_CHARGING_REMINDER_COUNT = "charging-reminder-count"

    private var waterPreferences: SharedPreferences? = null

    fun initPreferences(waterContext: Context) {
        waterPreferences = waterContext.getSharedPreferences(
            WATER_APPLICATION_PREFERENCE,
            Context.MODE_PRIVATE
        )
    }

    override fun getTotalWaterCups(): Int = waterPreferences?.getInt(KEY_WATER_COUNT, 0) ?: 0

    override fun saveNewTotalWaterCups(total: Int) {
        waterPreferences
            ?.edit()
            ?.putInt(KEY_WATER_COUNT,total)
            ?.apply()
    }
}