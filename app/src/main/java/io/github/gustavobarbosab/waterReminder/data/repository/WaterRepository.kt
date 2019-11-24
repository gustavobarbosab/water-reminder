package io.github.gustavobarbosab.waterReminder.data.repository

import io.github.gustavobarbosab.waterReminder.data.storage.local.WaterAppPreference
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

object WaterRepository {

    private val waterCupsSubject: BehaviorSubject<Int> = BehaviorSubject.create()

    private var waterAppPreference: WaterAppPreference? = null

    fun init(waterAppPreference: WaterAppPreference) {
        this.waterAppPreference = waterAppPreference
    }

    fun observeWaterCups(): Observable<Int> = waterCupsSubject

    fun getTotalWaterCups(): Int = waterAppPreference?.getTotalWaterCups() ?: 0

    fun saveNewTotalWaterCups(total: Int) {
        waterAppPreference?.saveNewTotalWaterCups(total)
        waterCupsSubject.onNext(total)
    }
}