package com.yrun.presentation.dashboard

import com.yrun.domain.dashboard.DashboardRepository
import com.yrun.domain.dashboard.DashboardResult
import com.yrun.presentation.core.UpdateUi
import com.yrun.presentation.main.BaseViewModel
import com.yrun.presentation.main.Clear
import com.yrun.presentation.main.Navigation
import com.yrun.presentation.main.RunAsync
import com.yrun.presentation.settings.SettingsScreen

class DashboardViewModel(
    private val navigation: Navigation,
    runAsync: RunAsync,
    private val clear: Clear,
    private val repository: DashboardRepository,
    private val observable: DashboardUiObservable,
    private val mapper: DashboardResult.Mapper = BaseDashboardResultMapper(observable)
) :
    BaseViewModel(runAsync), ClickActions {

    fun load() {
        observable.updateUi(DashboardUiState.Progress)
        runAsync({
            repository.dashboard()
        }) { it.map(mapper) }
    }

    override fun retry() {
        load()
    }

    fun goToSettings() {
        navigation.updateUi(SettingsScreen)
        clear.clear(this::class.java)
    }

    fun startGettingUpdates(observer: UpdateUi<DashboardUiState>) {
        observable.updateObserver(observer)
    }

    fun stopGettingUpdates() {
        observable.updateObserver(UpdateUi.Empty())
    }
}
