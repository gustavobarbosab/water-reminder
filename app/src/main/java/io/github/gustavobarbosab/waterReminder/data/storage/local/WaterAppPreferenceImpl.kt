package io.github.gustavobarbosab.waterReminder.data.storage.local

import android.content.Context
import android.content.SharedPreferences

object WaterAppPreferenceImpl : WaterAppPreference {

    private const val WATER_APPLICATION_PREFERENCE = "WATER_APPLICATION_PREFERENCE"
    private const val KEY_WATER_COUNT = "water-count"

    lateinit var waterPreferences: SharedPreferences

    fun init(context: Context) {
        this.waterPreferences =
            context.getSharedPreferences(WATER_APPLICATION_PREFERENCE, Context.MODE_PRIVATE)
    }

    override fun getTotalWaterCups(): Int =
        waterPreferences.getInt(KEY_WATER_COUNT, 0)

    override fun saveNewTotalWaterCups(total: Int) {
        waterPreferences
            .edit()
            .putInt(KEY_WATER_COUNT, total).apply()
    }
}