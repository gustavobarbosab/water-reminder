package io.github.gustavobarbosab.waterReminder

import android.app.Application
import io.github.gustavobarbosab.waterReminder.data.storage.local.WaterAppPreferenceImpl

class WaterReminderApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        WaterAppPreferenceImpl.init(this)
    }
}
