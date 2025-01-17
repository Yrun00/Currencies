package com.yrun.data.load

//import com.yrun.data.load.cache.CurrencyCache
//import com.yrun.data.load.cache.CurrencyCacheDataSource
//import com.yrun.data.load.cloud.CurrencyCloudDataSource
//import com.yrun.domain.load.LoadCurrenciesRepository
//import javax.inject.Inject

//class BaseLoadCurrencyRepository @Inject constructor(
//    private val currencyCacheDataSource: CurrencyCacheDataSource.Mutable,
//    private val currencyCloudDataSource: CurrencyCloudDataSource,
//) : LoadCurrenciesRepository {
//
//    override suspend fun loadCurrencies() {
//
//        if (currencyCacheDataSource.read().isEmpty()) {
//            val currencies = currencyCloudDataSource.currencies().map {
//                CurrencyCache(it.key, it.value)
//            }
//            currencyCacheDataSource.save(currencies)
//        }
//    }
//}
//
//class FakeLoadCurrencyRepository @Inject constructor(
//    private val currencyCacheDataSource: CurrencyCacheDataSource.Mutable,
//) : LoadCurrenciesRepository {
//
//
//    override suspend fun loadCurrencies() {
//        currencyCacheDataSource.save(
//            listOf<CurrencyCache>(
//                CurrencyCache("USD", "1"),
//                CurrencyCache("EUR", "2"),
//                CurrencyCache("RUB", "3")
//            )
//        )
//    }
//}