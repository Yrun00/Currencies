package com.yrun.currencies.modules

import com.yrun.currencies.ProvideInstance
import com.yrun.data.dashboard.cache.FavoritePairsCacheDataSource
import com.yrun.data.load.cache.CurrencyCacheDataSource
import com.yrun.data.load.cache.CurrencyDao
import com.yrun.domain.settings.SettingsRepository
import com.yrun.presentation.settings.SettingsUiObservable
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsModule {

    @Binds
    abstract fun bindObservable(
        observable: SettingsUiObservable.Base
    ): SettingsUiObservable

    @Binds
    abstract fun bindAllCacheDataSource(
        allCacheDataSource: CurrencyCacheDataSource.Base
    ): CurrencyCacheDataSource.Read

    @Binds
    abstract fun bindFavoriteCacheDataSource(
        favoriteCacheDataSource: FavoritePairsCacheDataSource.Base
    ): FavoritePairsCacheDataSource.Mutable


    companion object {
        @Provides
        fun provideRepository(
            provideInstance: ProvideInstance,
            currencyDao: CurrencyDao,
            favoriteCurrenciesCacheDataSource: FavoritePairsCacheDataSource.Base
        ): SettingsRepository = provideInstance.provideSettingsRepository(
            favoritePairsCacheDataSource = favoriteCurrenciesCacheDataSource,
            currencyDao = currencyDao
        )
    }
}