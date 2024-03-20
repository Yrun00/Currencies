package com.yrun.presentation.dashboard

import android.os.Bundle
import com.yrun.data.databinding.FragmentDashboardBinding
import com.yrun.domain.dashboard.DashboardRepository
import com.yrun.domain.dashboard.DashboardResult
import com.yrun.presentation.core.BundleWrapper
import com.yrun.presentation.core.UpdateUi
import com.yrun.presentation.main.BaseViewModel
import com.yrun.presentation.main.Navigation
import com.yrun.presentation.main.RunAsync
import com.yrun.presentation.settings.SettingsScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val navigation: Navigation.Mutable,
    runAsync: RunAsync,
    private val repository: DashboardRepository,
    private val observable: DashboardUiObservable,
    private val mapper: DashboardResult.Mapper,
    private val derive: CurrencyPairUi.Mutable
) : BaseViewModel(runAsync), ClickActions {

    fun load() {
        observable.updateUi(DashboardUiState.Progress)
        runAsync({
            repository.dashboard()
        }) { it.map(mapper) }
    }

    fun init(savedInstanceState: Bundle?, binding: FragmentDashboardBinding) {
        if (savedInstanceState == null) load()
        else {
            val recyclerViewState = BundleWrapper.Base(savedInstanceState).restoreRecycler()
            binding.dashboardRecyclerView.layoutManager?.onRestoreInstanceState(recyclerViewState)
        }
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
    }

    fun startGettingUpdates(observer: UpdateUi<DashboardUiState>) {
        observable.updateObserver(observer)
    }

    fun stopGettingUpdates() {
        observable.updateObserver(UpdateUi.Empty())
    }
}
