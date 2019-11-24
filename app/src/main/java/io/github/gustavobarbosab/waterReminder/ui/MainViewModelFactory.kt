package io.github.gustavobarbosab.waterReminder.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.gustavobarbosab.waterReminder.data.repository.WaterRepository

class MainViewModelFactory(private val waterRepository: WaterRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        modelClass
            .getConstructor(WaterRepository::class.java)
            .newInstance(waterRepository)
}