package com.yrun.data

import com.yrun.data.cache.CurrencyCache
import com.yrun.data.cache.CurrencyCacheDataSource
import com.yrun.data.cloud.CurrencyCloudDataSource
import com.yrun.data.core.ProvideResources
import com.yrun.domain.LoadCurrenciesRepository
import com.yrun.domain.LoadCurrenciesResult
import java.lang.Exception
import java.net.UnknownHostException

class BaseLoadCurrencyRepository(
    private val currencyCacheDataSource: CurrencyCacheDataSource.Mutable,
    private val currencyCloudDataSource: CurrencyCloudDataSource,
    private val provideResources: ProvideResources
) : LoadCurrenciesRepository {

    override suspend fun loadCurrencies(): LoadCurrenciesResult = try {

        if (currencyCacheDataSource.read().isEmpty()) {
            val currencies = currencyCloudDataSource.currencies().map {
                CurrencyCache(it.key, it.value)
            }
            currencyCacheDataSource.save(currencies)
        }
        LoadCurrenciesResult.Success
    } catch (e: Exception) {
        LoadCurrenciesResult.Error(
            if (e is UnknownHostException) {
                provideResources.noInternetConnectionMessage()
            } else
                provideResources.serviceUnavailableMessage()
        )
    }
}
