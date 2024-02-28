package com.yrun.presentation.load

import com.yrun.domain.LoadCurrenciesResult
import com.yrun.presentation.core.LoadViewModel
import com.yrun.presentation.core.UpdateUi
import com.yrun.presentation.dashboard.DashboardScreen
import com.yrun.presentation.main.Clear
import com.yrun.presentation.main.Navigation

class BaseLoadResultMapper(
    private val observable: UpdateUi<LoadUiState>,
    private val navigation: Navigation,
    private val clear: Clear
) : LoadCurrenciesResult.Mapper {

    override fun mapError(message: String) {
        observable.updateUi(LoadUiState.Error(message))
    }

    override fun mapSuccess() {
        navigation.updateUi(DashboardScreen)
        clear.clear(LoadViewModel::class.java)
    }
}