package com.yrun.currencies

import com.yrun.data.core.ProvideResources
import com.yrun.data.load.BaseLoadCurrencyRepository
import com.yrun.data.load.cache.CurrencyCacheDataSource
import com.yrun.data.load.cloud.CurrencyCloudDataSource
import com.yrun.domain.load.LoadCurrenciesRepository

interface ProvideInstance {

    fun provideLoadRepository(
        cacheDataSource: CurrencyCacheDataSource.Mutable,
        provideResources: ProvideResources,
        cloudDataSource: CurrencyCloudDataSource
    ): LoadCurrenciesRepository

    class Base : ProvideInstance {
        override fun provideLoadRepository(
            cacheDataSource: CurrencyCacheDataSource.Mutable,
            provideResources: ProvideResources,
            cloudDataSource: CurrencyCloudDataSource
        ) = BaseLoadCurrencyRepository(
            currencyCacheDataSource = cacheDataSource,
            currencyCloudDataSource = cloudDataSource,
            provideResources = provideResources
        )
    }
}