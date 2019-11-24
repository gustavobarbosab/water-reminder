package io.github.gustavobarbosab.waterReminder.ui

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val cupsNumberVisibleOnScreen: ObservableField<Int> = ObservableField()

    private val _cupsNumber: MutableLiveData<Int> = MutableLiveData()
    val cupsNumber: LiveData<Int>
        get() = _cupsNumber

    var totalWaterCups: Int = 0
        set(value) {
            field = value
            cupsNumberVisibleOnScreen.set(value)
        }

    fun countAnotherCup() {
        totalWaterCups += 1
        _cupsNumber.value = totalWaterCups
    }
}