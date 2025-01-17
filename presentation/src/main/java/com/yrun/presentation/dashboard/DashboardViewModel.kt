package com.yrun.presentation.dashboard

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
    private val derive: CurrencyPairUi.Mutable,
    private val handleDeath: HandleDeath
) : BaseViewModel(runAsync), ClickActions {

    fun load() {
        observable.updateUi(DashboardUiState.Progress)
        runAsync({
            repository.dashboard()
        }) { it.map(mapper) }
    }

    fun init(wrapper: BundleWrapper.Mutable) {
        if (wrapper.isEmpty()) { //Самое первое открытие фрагмента - поэтому бандл = нулл,
            load()
            handleDeath.firstTime()

        } else { // Или произошла смерть активити или процесса, если пришёл новый хэндл деаз - под капотом будет deathHappened = true
            if (handleDeath.deathHappened()) {
                handleDeath.reset()
                load()
            }
//            else  пересоздалось активити(но т.к. кеш в обсервалбе остался - данные прилетят.)
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


interface HandleDeath {

    fun firstTime()

    fun reset()

    fun deathHappened(): Boolean

    class Base : HandleDeath {

        private var deathHappened = true

        override fun firstTime() {
            deathHappened = false
        }

        override fun reset() {
            deathHappened = false
        }

        override fun deathHappened(): Boolean {
            return deathHappened
        }
    }
}
