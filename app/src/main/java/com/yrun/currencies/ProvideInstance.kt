package com.yrun.currencies

import com.yrun.data.core.HandleError
import com.yrun.data.dashboard.BaseDashboardRepository
import com.yrun.data.dashboard.FakeDashboardRepository
import com.yrun.data.dashboard.cache.DashboardItemsDataSource
import com.yrun.data.dashboard.cache.FavoritePairsCacheDataSource
import com.yrun.data.load.BaseLoadCurrencyRepository
import com.yrun.data.load.FakeLoadCurrencyRepository
import com.yrun.data.load.cache.CurrencyCacheDataSource
import com.yrun.data.load.cache.CurrencyDao
import com.yrun.data.load.cloud.CurrencyCloudDataSource
import com.yrun.data.settings.BaseSettingsRepository
import com.yrun.domain.dashboard.DashboardRepository
import com.yrun.domain.load.LoadCurrenciesRepository
import com.yrun.domain.premium.PremiumStorage
import com.yrun.domain.premium.SettingsInteractor
import com.yrun.domain.settings.SettingsRepository
import javax.inject.Inject

interface ProvideInstance {


    fun provideMaxFreeSavedPairsCount(): Int

    fun provideLoadRepository(
        cacheDataSource: CurrencyCacheDataSource.Mutable,
        cloudDataSource: CurrencyCloudDataSource,
        handleError: HandleError.Base
    ): LoadCurrenciesRepository

    fun provideSettingsRepository(
        currencyDao: CurrencyDao,
        favoritePairsCacheDataSource: FavoritePairsCacheDataSource.Mutable,
    ): SettingsRepository

    fun provideDashboardRepository(
        dashboardItemDataSource: DashboardItemsDataSource,
        favoritePairsCacheDataSource: FavoritePairsCacheDataSource.Mutable,
        handleError: HandleError.Base
    ): DashboardRepository

    fun provideSettingsInteractor(
        currencyDao: CurrencyDao,
        favoritePairsCacheDataSource: FavoritePairsCacheDataSource.Mutable,
        maxFreePairCount: Int,
        premiumStorage: PremiumStorage.Mutable,
    ): SettingsInteractor


    class Base @Inject constructor() : ProvideInstance {

        override fun provideMaxFreeSavedPairsCount(): Int = 5

        override fun provideLoadRepository(
            cacheDataSource: CurrencyCacheDataSource.Mutable,
            cloudDataSource: CurrencyCloudDataSource,
            handleError: HandleError.Base
        ) = BaseLoadCurrencyRepository(
            currencyCacheDataSource = cacheDataSource,
            currencyCloudDataSource = cloudDataSource,
            handleError = handleError
        )

        override fun provideSettingsRepository(
            currencyDao: CurrencyDao,
            favoritePairsCacheDataSource: FavoritePairsCacheDataSource.Mutable
        ): SettingsRepository = BaseSettingsRepository(
            currencyDao = currencyDao,
            favoritePairsCacheDataSource = favoritePairsCacheDataSource,
        )

        override fun provideDashboardRepository(
            dashboardItemDataSource: DashboardItemsDataSource,
            favoritePairsCacheDataSource: FavoritePairsCacheDataSource.Mutable,
            handleError: HandleError.Base
        ): DashboardRepository =
            BaseDashboardRepository(
                dashboardItemsDataSource = dashboardItemDataSource,
                favoritePairsCacheDataSource = favoritePairsCacheDataSource,
                handleError = handleError
            )

        override fun provideSettingsInteractor(
            currencyDao: CurrencyDao,
            favoritePairsCacheDataSource: FavoritePairsCacheDataSource.Mutable,
            maxFreePairCount: Int,
            premiumStorage: PremiumStorage.Mutable,
        ): SettingsInteractor =
            SettingsInteractor.Base(
                maxFreePairsCount = maxFreePairCount,
                premiumStorage = premiumStorage,
                repository = BaseSettingsRepository(
                    currencyDao = currencyDao,
                    favoritePairsCacheDataSource = favoritePairsCacheDataSource,
                )
            )
    }

    class Fake @Inject constructor() : ProvideInstance {
        override fun provideMaxFreeSavedPairsCount(): Int = 2

        override fun provideLoadRepository(
            cacheDataSource: CurrencyCacheDataSource.Mutable,
            cloudDataSource: CurrencyCloudDataSource,
            handleError: HandleError.Base
        ): LoadCurrenciesRepository = FakeLoadCurrencyRepository(
            currencyCacheDataSource = cacheDataSource,
            handleError = handleError
        )

        override fun provideSettingsRepository(
            currencyDao: CurrencyDao,
            favoritePairsCacheDataSource: FavoritePairsCacheDataSource.Mutable
        ): SettingsRepository = BaseSettingsRepository(
            currencyDao = currencyDao,
            favoritePairsCacheDataSource = favoritePairsCacheDataSource
        )

        override fun provideDashboardRepository(
            dashboardItemDataSource: DashboardItemsDataSource,
            favoritePairsCacheDataSource: FavoritePairsCacheDataSource.Mutable,
            handleError: HandleError.Base
        ): DashboardRepository = FakeDashboardRepository(
            dashboardItemsDataSource = dashboardItemDataSource,
            favoritePairsCacheDataSource = favoritePairsCacheDataSource,
            handleError = handleError
        )

        override fun provideSettingsInteractor(
            currencyDao: CurrencyDao,
            favoritePairsCacheDataSource: FavoritePairsCacheDataSource.Mutable,
            maxFreePairCount: Int,
            premiumStorage: PremiumStorage.Mutable,
        ): SettingsInteractor =
            SettingsInteractor.Base(
                maxFreePairsCount = maxFreePairCount,
                premiumStorage = premiumStorage,
                repository = BaseSettingsRepository(
                    currencyDao = currencyDao,
                    favoritePairsCacheDataSource = favoritePairsCacheDataSource,
                )
            )
    }
}

