package io.github.gustavobarbosab.waterReminder.ui

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import io.github.gustavobarbosab.waterReminder.data.repository.WaterReminderRepository

class MainViewModel(private val waterRepository: WaterReminderRepository) : ViewModel() {

    val cupsNumberVisibleOnScreen: ObservableField<Int> = ObservableField()

    init {
        updateCounter()
    }

    fun countAnotherCup() {
        waterRepository.incrementWaterCup()
        updateCounter()
    }

    fun updateCounter() {
        cupsNumberVisibleOnScreen.set(waterRepository.getWaterCups())
    }
}