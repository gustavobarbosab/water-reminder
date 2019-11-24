package io.github.gustavobarbosab.waterReminder.data.sync

import io.github.gustavobarbosab.waterReminder.data.repository.WaterRepository

class WaterServicePresenter(
    var waterService: WaterService?,
    waterRepository: WaterRepository
) {


    fun detach() {
        waterService = null
    }
}