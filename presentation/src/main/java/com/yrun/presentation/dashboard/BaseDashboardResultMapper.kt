package com.yrun.presentation.dashboard

import com.yrun.domain.dashboard.DashboardItem
import com.yrun.domain.dashboard.DashboardResult
import com.yrun.presentation.core.UiObservable
import com.yrun.presentation.dashboard.adapter.DashboardUi

class BaseDashboardResultMapper(
    private val observable: UiObservable<DashboardUiState>,
    private val dashboardItemMapper: DashboardItem.Mapper<DashboardUi> = BaseDashboardItemMapper()
) : DashboardResult.Mapper {

    override fun mapSuccess(listOfItem: List<DashboardItem>) {
        observable.updateUi(
            DashboardUiState.Base(
                listOfItem.map {
                    it.map(dashboardItemMapper)
                }
            )
        )
    }

    override fun mapError(message: String) {
        observable.updateUi(DashboardUiState.Error(message))
    }

    override fun mapEmpty() {
        observable.updateUi(DashboardUiState.Empty)
    }
}

class BaseDashboardItemMapper : DashboardItem.Mapper<DashboardUi> {

    override fun map(fromCurrency: String, toCurrency: String, rate: Double): DashboardUi {
        val roundedRate = String.format("%.2f", rate)
        return DashboardUi.Success("$toCurrency / $fromCurrency", roundedRate)
    }
}