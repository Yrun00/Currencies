package com.yrun.currencies.modules

import com.yrun.currencies.Core
import com.yrun.currencies.ProvideInstance
import com.yrun.data.core.CurrentTimeInMillis
import com.yrun.data.core.HandleError
import com.yrun.data.dashboard.cache.DashboardItemsDataSource
import com.yrun.data.dashboard.cache.FavoritePairsCacheDataSource
import com.yrun.data.dashboard.cloud.PairClodDataSource
import com.yrun.data.dashboard.cloud.UpdatedRateDataSource
import com.yrun.presentation.dashboard.BaseDashboardItemMapper
import com.yrun.presentation.dashboard.BaseDashboardResultMapper
import com.yrun.presentation.dashboard.CurrencyPairUi
import com.yrun.presentation.dashboard.DashboardUiObservable
import com.yrun.presentation.dashboard.DashboardViewModel
import com.yrun.presentation.main.Clear

class DashboardModule(
    private val core: Core,
    private val provideInstance: ProvideInstance,
    private val clear: Clear

) : Module<DashboardViewModel> {

    override fun viewModel(): DashboardViewModel {
        val currentTimeInMillis = CurrentTimeInMillis.Base()
        val favoritePairsCacheDataSource = FavoritePairsCacheDataSource.Base(
            pairDao = core.providePairDatabase().pairDao()
        )
        val observable = DashboardUiObservable.Base()

        return DashboardViewModel(
            observable = observable,
            repository = provideInstance.provideDashboardRepository(
                dashboardItemDataSource = DashboardItemsDataSource.Base(
                    currentTimeInMillis = currentTimeInMillis,
                    updatedRateDataSource = UpdatedRateDataSource.Base(
                        currentTimeInMillis, favoritePairsCacheDataSource,
                        PairClodDataSource.Base()
                    )
                ),
                favoritePairsCacheDataSource = favoritePairsCacheDataSource,
                handleError = HandleError.Base(core.provideResources())
            ),
            clear = clear,
            navigation = core.provideNavigation(),
            runAsync = core.provideRunAsync(),
            derive = CurrencyPairUi.Base(),
            mapper = BaseDashboardResultMapper(
                observable = observable,
                dashboardItemMapper = BaseDashboardItemMapper(concat = CurrencyPairUi.Base())
            )
        )
    }
}
