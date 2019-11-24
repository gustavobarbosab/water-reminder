package io.github.gustavobarbosab.waterReminder.ui

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    val cupsNumber: ObservableField<Int> = ObservableField()

    var totalWaterCups: Int = 0
    set(value) {
        field = value
        cupsNumber.set(value)
    }

    fun countAnotherCup() {
        totalWaterCups += 1
    }
}