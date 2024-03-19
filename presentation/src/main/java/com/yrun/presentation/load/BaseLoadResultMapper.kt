package com.yrun.presentation.load

import com.yrun.domain.load.LoadCurrenciesResult
import com.yrun.presentation.dashboard.DashboardScreen
import com.yrun.presentation.main.Navigation
import javax.inject.Inject

class BaseLoadResultMapper @Inject constructor(
    private val observable: LoadUiObservable,
    private val navigation: Navigation.Mutable,
) : LoadCurrenciesResult.Mapper {

    override fun mapError(message: String) {
        observable.updateUi(LoadUiState.Error(message))
    }

    override fun mapSuccess() {
        navigation.updateUi(DashboardScreen)
    }
}