package com.yrun.presentation.dashboard

import com.yrun.domain.dashboard.DashboardRepository
import com.yrun.domain.dashboard.DashboardResult
import com.yrun.presentation.core.UiObservable
import com.yrun.presentation.core.UpdateUi
import com.yrun.presentation.main.BaseViewModel
import com.yrun.presentation.main.Clear
import com.yrun.presentation.main.Navigation
import com.yrun.presentation.main.RunAsync
import com.yrun.presentation.settings.SettingsScreen

class DashboardViewModel(
    private val navigation: Navigation.Navigate,
    runAsync: RunAsync,
    private val clear: Clear,
    private val repository: DashboardRepository,
    private val observable: UiObservable<DashboardUiState>,
    private val mapper: DashboardResult.Mapper,
    private val derive: CurrencyPairUi.Derive
) : BaseViewModel(runAsync), ClickActions {

    fun load() {
        observable.updateUi(DashboardUiState.Progress)
        runAsync({
            repository.dashboard()
        }) { it.map(mapper) }
    }

    override fun retry() = load()

    override fun deletePair(pairUi: String) {
        runAsync({
            val (from, to) = derive.derive(pairUi)
            repository.deletePair(from = from, to = to)
        }) { it.map(mapper) }
    }

    fun goToSettings() {
        navigation.updateUi(SettingsScreen)
        clear.clear(DashboardViewModel::class.java)
    }

    fun startGettingUpdates(observer: UpdateUi<DashboardUiState>) {
        observable.updateObserver(observer)
    }

    fun stopGettingUpdates() {
        observable.updateObserver(UpdateUi.Empty())
    }
}
