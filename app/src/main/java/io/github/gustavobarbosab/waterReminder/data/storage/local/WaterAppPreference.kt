package io.github.gustavobarbosab.waterReminder.data.storage.local

import android.content.Context
import io.reactivex.Observable

interface WaterAppPreference {
    fun getTotalWaterCups(context: Context): Int
    fun saveNewTotalWaterCups(context: Context, total: Int)
}