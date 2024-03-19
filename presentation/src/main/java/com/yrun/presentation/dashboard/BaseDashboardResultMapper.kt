package com.yrun.presentation.dashboard

import com.yrun.domain.dashboard.DashboardItem
import com.yrun.domain.dashboard.DashboardResult
import com.yrun.presentation.dashboard.adapter.DashboardUi
import javax.inject.Inject

class BaseDashboardResultMapper @Inject constructor(
    private val observable: DashboardUiObservable,
    private val dashboardItemMapper: DashboardItem.Mapper<DashboardUi>
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

class BaseDashboardItemMapper @Inject constructor(private val concat: CurrencyPairUi.Mutable) :
    DashboardItem.Mapper<DashboardUi> {

    override fun map(fromCurrency: String, toCurrency: String, rate: Double): DashboardUi {
        val roundedRate = String.format("%.2f", rate)
        return DashboardUi.Success(concat.concat(fromCurrency, toCurrency), roundedRate)
    }
}
