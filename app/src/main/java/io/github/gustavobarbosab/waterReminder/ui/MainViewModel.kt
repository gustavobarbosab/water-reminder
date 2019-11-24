package io.github.gustavobarbosab.waterReminder.ui

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import io.github.gustavobarbosab.waterReminder.data.repository.WaterRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val waterRepository: WaterRepository) : ViewModel() {

    var disposable: Disposable = waterRepository
        .observeWaterCups()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
            cupsNumber.set(it)
        }

    val cupsNumber: ObservableField<Int> = ObservableField()

    init {
        cupsNumber.set(waterRepository.getTotalWaterCups())
    }

    fun countAnotherCup() {
        val totalCups = waterRepository.getTotalWaterCups()
        waterRepository.saveNewTotalWaterCups(totalCups + 1)
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}