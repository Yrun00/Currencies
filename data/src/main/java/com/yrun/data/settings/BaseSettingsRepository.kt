package com.yrun.data.settings

import com.yrun.data.dashboard.cache.FavoritePairsCacheDataSource
import com.yrun.data.dashboard.cache.PairCache
import com.yrun.data.load.cache.CurrencyDao
import com.yrun.domain.settings.SettingsRepository
import javax.inject.Inject

class BaseSettingsRepository @Inject constructor(
    private val currencyDao: CurrencyDao,
    private val favoritePairsCacheDataSource: FavoritePairsCacheDataSource.Mutable,
) : SettingsRepository {

    override suspend fun allCurrencies(): List<String> =
        currencyDao.currencies().map { it.id }.sorted()

    override suspend fun savedPairsCount() =
        favoritePairsCacheDataSource.read().size


    override suspend fun availableDestinations(fromCurrency: String): List<String> {

        val allCurrencies = allCurrencies()
        val favoritePairs = favoritePairsCacheDataSource.read().filter {
            it.fromCurrency == fromCurrency
        }

        return allCurrencies.filterNot { currency ->
            favoritePairs.any { it.toCurrency == currency } || currency == fromCurrency
        }
    }

    override suspend fun save(toCurrency: String, fromCurrency: String) =
        favoritePairsCacheDataSource.save(
            PairCache(
                toCurrency = toCurrency,
                fromCurrency = fromCurrency
            )
        )
}