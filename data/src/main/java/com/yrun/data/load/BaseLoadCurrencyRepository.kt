package com.yrun.data.load

import com.yrun.data.core.HandleError
import com.yrun.data.load.cache.CurrencyCache
import com.yrun.data.load.cache.CurrencyCacheDataSource
import com.yrun.data.load.cloud.CurrencyCloudDataSource
import com.yrun.domain.load.LoadCurrenciesRepository
import com.yrun.domain.load.LoadCurrenciesResult
import java.net.UnknownHostException
import javax.inject.Inject

class BaseLoadCurrencyRepository @Inject constructor(
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

class FakeLoadCurrencyRepository @Inject constructor(
    private val currencyCacheDataSource: CurrencyCacheDataSource.Mutable,
    private val handleError: HandleError
) : LoadCurrenciesRepository {


    override suspend fun loadCurrencies(): LoadCurrenciesResult {
        if (firstTime) {
            firstTime = false
            return LoadCurrenciesResult.Error(handleError.handleError(UnknownHostException()))
        } else {
            currencyCacheDataSource.save(
                listOf<CurrencyCache>(
                    CurrencyCache("USD", "1"),
                    CurrencyCache("EUR", "2"),
                    CurrencyCache("RUB", "3")
                )
            )
            return LoadCurrenciesResult.Success
        }


    }
}

private var firstTime = true
