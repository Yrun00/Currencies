package com.yrun.data.settings

import com.yrun.data.dashboard.cache.FavoritePairsCacheDataSource
import com.yrun.data.dashboard.cache.PairCache
import com.yrun.data.load.cache.CurrencyCache
import com.yrun.data.load.cache.CurrencyDao
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BaseSettingsRepositoryTest {

    private lateinit var repository: BaseSettingsRepository
    private lateinit var currencyDao: FakeCurrencyDao
    private lateinit var favoritePairsCacheDataSource: FakeFavoritePairsCacheDataSource

    @Before
    fun setup() {
        currencyDao = FakeCurrencyDao()
        favoritePairsCacheDataSource = FakeFavoritePairsCacheDataSource()
        repository = BaseSettingsRepository(currencyDao, favoritePairsCacheDataSource)
    }

    @Test
    fun scenarioTest() = runBlocking {
        var actual = repository.allCurrencies()
        assertEquals(listOf("AUD", "EUR", "JPY", "RUB", "USD"), actual)

        actual = repository.availableDestinations("RUB")
        assertEquals(listOf("AUD", "EUR", "JPY", "USD"), actual)

        repository.save(toCurrency = "USD", fromCurrency = "RUB")
        actual = repository.availableDestinations("RUB")
        assertEquals(listOf("AUD", "EUR", "JPY"), actual)

        repository.save(toCurrency = "JPY", fromCurrency = "RUB")
        actual = repository.availableDestinations("RUB")
        assertEquals(listOf("AUD", "EUR"), actual)

        repository.save(toCurrency = "RUB", fromCurrency = "JPY")
        actual = repository.availableDestinations("RUB")
        assertEquals(listOf("AUD", "EUR"), actual)

        repository.save(toCurrency = "EUR", fromCurrency = "RUB")
        actual = repository.availableDestinations("RUB")
        assertEquals(listOf("AUD"), actual)

        repository.save(toCurrency = "AUD", fromCurrency = "RUB")
        actual = repository.availableDestinations("RUB")
        assertEquals(emptyList<String>(), actual)


        actual = repository.availableDestinations("JPY")
        assertEquals(listOf("AUD", "EUR", "USD"), actual)

    }

    @Test
    fun allCurrencyTest() = runBlocking {
        assertEquals(listOf("AUD", "EUR", "JPY", "RUB", "USD"), repository.allCurrencies())
    }

    @Test
    fun availableDestinationTest() = runBlocking {
        repository.save("USD", "EUR")
        repository.save("USD", "RUB")
        repository.save("AUD", "RUB")
        assertEquals(listOf("EUR", "JPY"), repository.availableDestinations(fromCurrency = "RUB"))
    }

    @Test
    fun saveTest() = runBlocking {
        repository.save("EUR", "JPY")
        assertEquals(
            listOf<PairCache>(
                PairCache("EUR", "JPY")
            ), favoritePairsCacheDataSource.actual
        )
        repository.save("RUB", "USD")
        assertEquals(
            listOf<PairCache>(
                PairCache("EUR", "JPY"),
                PairCache("RUB", "USD")
            ), favoritePairsCacheDataSource.actual
        )
    }
}

class FakeFavoritePairsCacheDataSource : FavoritePairsCacheDataSource.Mutable {

    var actual = mutableListOf<PairCache>()

    override suspend fun save(currencyPair: PairCache) {
        actual.add(currencyPair)
    }

    override suspend fun read(): List<PairCache> {
        return actual
    }

    override suspend fun delete(currencyPair: PairCache) {
        actual.remove(currencyPair)
    }
}

class FakeCurrencyDao : CurrencyDao {
    override suspend fun currencies(): List<CurrencyCache> {
        return listOf(
            CurrencyCache("USD", "Dollar"),
            CurrencyCache("EUR", "Euro"),
            CurrencyCache("RUB", "Ruble"),
            CurrencyCache("JPY", "Yen"),
            CurrencyCache("AUD", "Australian Dollar")
        )
    }

    override suspend fun insert(currencyList: List<CurrencyCache>) = Unit
}
