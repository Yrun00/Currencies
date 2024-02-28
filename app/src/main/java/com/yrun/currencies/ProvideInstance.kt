package com.yrun.currencies

import com.yrun.data.BaseLoadCurrencyRepository
import com.yrun.data.cache.CurrencyCacheDataSource
import com.yrun.data.cloud.CurrencyCloudDataSource
import com.yrun.data.core.ProvideResources
import com.yrun.domain.LoadCurrenciesRepository

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