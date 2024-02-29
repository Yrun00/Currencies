package com.yrun.data

import com.yrun.data.cache.CurrencyCache
import com.yrun.data.cache.CurrencyCacheDataSource
import com.yrun.data.cloud.CurrencyCloudDataSource
import com.yrun.data.core.HandleError
import com.yrun.domain.load.LoadCurrenciesRepository
import com.yrun.domain.load.LoadCurrenciesResult

class BaseLoadCurrencyRepository(
    private val currencyCacheDataSource: CurrencyCacheDataSource.Mutable,
    private val currencyCloudDataSource: CurrencyCloudDataSource,
    private val handleError: HandleError
) : LoadCurrenciesRepository {

    override suspend fun loadCurrencies(): LoadCurrenciesResult = try {

        if (currencyCacheDataSource.read().isEmpty()) {
            val currencies = currencyCloudDataSource.currencies().map {
                CurrencyCache(it.key, it.value)
            }
            currencyCacheDataSource.save(currencies)
        }
        LoadCurrenciesResult.Success
    } catch (error: Exception) {
        LoadCurrenciesResult.Error(handleError.handleError(error))
    }
}
