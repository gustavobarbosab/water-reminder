package io.github.gustavobarbosab.waterReminder.data.storage.local

import android.content.Context

class WaterAppPreferenceImpl : WaterAppPreference {

    companion object {
        private const val WATER_APPLICATION_PREFERENCE = "WATER_APPLICATION_PREFERENCE"
        private const val KEY_WATER_COUNT = "water-count"
    }

    private fun waterPreferences(context: Context) = context.getSharedPreferences(
        WATER_APPLICATION_PREFERENCE,
        Context.MODE_PRIVATE
    )

    override fun getTotalWaterCups(context: Context): Int =
        waterPreferences(context)?.getInt(KEY_WATER_COUNT, 0) ?: 0

    override fun saveNewTotalWaterCups(context: Context, total: Int) {
        waterPreferences(context)
            ?.edit()
            ?.putInt(KEY_WATER_COUNT, total)
            ?.apply()
    }
}