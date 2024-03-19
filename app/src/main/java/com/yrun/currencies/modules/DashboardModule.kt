package com.yrun.currencies.modules

import com.yrun.currencies.ProvideInstance
import com.yrun.data.core.CurrentTimeInMillis
import com.yrun.data.core.HandleError
import com.yrun.data.dashboard.cache.DashboardItemsDataSource
import com.yrun.data.dashboard.cache.FavoritePairsCacheDataSource
import com.yrun.data.dashboard.cloud.PairClodDataSource
import com.yrun.data.dashboard.cloud.UpdatedRateDataSource
import com.yrun.domain.dashboard.DashboardItem
import com.yrun.domain.dashboard.DashboardRepository
import com.yrun.domain.dashboard.DashboardResult
import com.yrun.presentation.dashboard.BaseDashboardItemMapper
import com.yrun.presentation.dashboard.BaseDashboardResultMapper
import com.yrun.presentation.dashboard.CurrencyPairUi
import com.yrun.presentation.dashboard.DashboardUiObservable
import com.yrun.presentation.dashboard.adapter.DashboardUi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


@Module
@InstallIn(ViewModelComponent::class)
abstract class DashboardModule {

    @Binds
    @ViewModelScoped
    abstract fun bindObservable(
        dashboardObservable: DashboardUiObservable.Base
    ): DashboardUiObservable

    @Binds
    abstract fun bindCacheDataSource(
        cacheDataSource: FavoritePairsCacheDataSource.Base
    ): FavoritePairsCacheDataSource.Save

    @Binds
    abstract fun bindUpdatedRateDataSource(
        updatedRateDataSource: UpdatedRateDataSource.Base
    ): UpdatedRateDataSource

    @Binds
    abstract fun bindCloudDataSource(
        currenciesCloudDataSource: PairClodDataSource.Base
    ): PairClodDataSource

    @Binds
    abstract fun bindCurrencyPairUi(
        delimiter: CurrencyPairUi.Base
    ): CurrencyPairUi.Mutable

    @Binds
    abstract fun bindDashboardResultMapper(
        mapper: BaseDashboardResultMapper
    ): DashboardResult.Mapper

    @Binds
    abstract fun bindDashboardItemMapper(
        mapper: BaseDashboardItemMapper
    ): DashboardItem.Mapper<DashboardUi>

    companion object {
        @Provides
        fun provideCurrencyPairUi(): CurrencyPairUi.Base = CurrencyPairUi.Base(separator = "/")

        @Provides
        fun provideDispatcherIO(): CoroutineDispatcher = Dispatchers.IO

        @Provides
        fun provideCurrentTimeInMillis(): CurrentTimeInMillis = CurrentTimeInMillis.Base()

        @Provides
        fun provideRepository(
            provideInstance: ProvideInstance,
            favoritePairsCacheDataSource: FavoritePairsCacheDataSource.Base,
            dashboardItemsDataSource: DashboardItemsDataSource.Base,
            handleError: HandleError.Base
        ): DashboardRepository = provideInstance.provideDashboardRepository(
            dashboardItemDataSource = dashboardItemsDataSource,
            favoritePairsCacheDataSource = favoritePairsCacheDataSource,
            handleError = handleError
        )
    }
}
