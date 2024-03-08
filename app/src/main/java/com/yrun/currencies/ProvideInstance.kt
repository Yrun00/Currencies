package com.yrun.currencies

import com.yrun.data.core.HandleError
import com.yrun.data.core.ProvideResources
import com.yrun.data.dashboard.BaseDashboardRepository
import com.yrun.data.dashboard.cache.DashboardItemsDataSource
import com.yrun.data.dashboard.cache.FavoritePairsCacheDataSource
import com.yrun.data.load.BaseLoadCurrencyRepository
import com.yrun.data.load.cache.CurrencyCacheDataSource
import com.yrun.data.load.cache.CurrencyDao
import com.yrun.data.load.cloud.CurrencyCloudDataSource
import com.yrun.data.settings.BaseSettingRepository
import com.yrun.domain.dashboard.DashboardRepository
import com.yrun.domain.load.LoadCurrenciesRepository
import com.yrun.domain.settings.SettingsRepository

interface ProvideInstance {

    fun provideLoadRepository(
        cacheDataSource: CurrencyCacheDataSource.Mutable,
        provideResources: ProvideResources,
        cloudDataSource: CurrencyCloudDataSource,
        handleError: HandleError.Base
    ): LoadCurrenciesRepository

    fun provideSettingsRepository(
        currencyDao: CurrencyDao,
        favoritePairsCacheDataSource: FavoritePairsCacheDataSource.Mutable
    ): SettingsRepository

    fun provideDashboardRepository(
        dashboardItemDataSource: DashboardItemsDataSource,
        favoritePairsCacheDataSource: FavoritePairsCacheDataSource.Read,
        handleError: HandleError.Base
    ): DashboardRepository

    class Base : ProvideInstance {
        override fun provideLoadRepository(
            cacheDataSource: CurrencyCacheDataSource.Mutable,
            provideResources: ProvideResources,
            cloudDataSource: CurrencyCloudDataSource,
            handleError: HandleError.Base
        ) = BaseLoadCurrencyRepository(
            currencyCacheDataSource = cacheDataSource,
            currencyCloudDataSource = cloudDataSource,
            handleError = HandleError.Base(provideResources)
        )

        override fun provideSettingsRepository(
            currencyDao: CurrencyDao,
            favoritePairsCacheDataSource: FavoritePairsCacheDataSource.Mutable
        ): SettingsRepository = BaseSettingRepository(
            currencyDao = currencyDao,
            favoritePairsCacheDataSource = favoritePairsCacheDataSource
        )

        override fun provideDashboardRepository(
            dashboardItemDataSource: DashboardItemsDataSource,
            favoritePairsCacheDataSource: FavoritePairsCacheDataSource.Read,
            handleError: HandleError.Base
        ): DashboardRepository =
            BaseDashboardRepository(
                dashboardItemsDataSource = dashboardItemDataSource,
                favoritePairsCacheDataSource = favoritePairsCacheDataSource,
                handleError = handleError
            )
    }
}