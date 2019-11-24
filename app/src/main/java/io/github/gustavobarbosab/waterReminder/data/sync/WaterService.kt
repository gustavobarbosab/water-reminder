package io.github.gustavobarbosab.waterReminder.data.sync

import android.app.IntentService
import android.content.Intent
import io.github.gustavobarbosab.waterReminder.data.repository.WaterRepository

class WaterService: IntentService(WATER_SERVICE) {

    private val presenter = WaterServicePresenter(this, WaterRepository)

    override fun onHandleIntent(intent: Intent?) {

    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }
    companion object {
        private const val WATER_SERVICE = "WATER_SERVICE"
    }
}