package io.github.gustavobarbosab.waterReminder.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.gustavobarbosab.waterReminder.data.repository.WaterReminderRepository

class MainViewModelFactory(private val repository: WaterReminderRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        modelClass
            .getConstructor(WaterReminderRepository::class.java)
            .newInstance(repository)
}