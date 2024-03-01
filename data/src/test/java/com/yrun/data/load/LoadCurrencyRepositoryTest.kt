package com.yrun.data.load

import com.yrun.data.core.HandleError
import com.yrun.data.core.ProvideResources
import com.yrun.data.load.cache.CurrencyCache
import com.yrun.data.load.cache.CurrencyCacheDataSource
import com.yrun.data.load.cloud.CurrencyCloudDataSource
import com.yrun.domain.load.LoadCurrenciesResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class LoadCurrencyRepositoryTest {

    private lateinit var cloudDataSource: FakeCloudDataSource
    private lateinit var repository: BaseLoadCurrencyRepository
    private lateinit var cacheDataSource: FakeCacheDataSource
    private lateinit var handleError: HandleError
    private lateinit var provideResources: FakeProvideResources

    @Before
    fun setup() {
        cloudDataSource = FakeCloudDataSource()
        provideResources = FakeProvideResources()
        handleError = HandleError.Base(provideResources)
        cacheDataSource = FakeCacheDataSource()
        repository = BaseLoadCurrencyRepository(
            currencyCacheDataSource = cacheDataSource,
            currencyCloudDataSource = cloudDataSource,
            handleError = handleError
        )
    }

    @Test
    fun successNoCache() = runBlocking {
        cloudDataSource.successResult()

        val actual = repository.loadCurrencies()
        val expected = LoadCurrenciesResult.Success
        assertEquals(expected, actual)
    }

    @Test
    fun successHasCache() = runBlocking {
        cloudDataSource.successResult()
        cacheDataSource.save(hashMapOf(
            "111" to "1111111",
            "222" to "2222222",
            "333" to "3333333"
        ).map { CurrencyCache(it.key, it.value) })

        val actual = repository.loadCurrencies()
        val expected = LoadCurrenciesResult.Success
        assertEquals(expected, actual)
    }

    @Test
    fun noConnection() = runBlocking {
        cloudDataSource.expectException(UnknownHostException())
        val actual = repository.loadCurrencies()
        val expected = LoadCurrenciesResult.Error("No internet connection")
        assertEquals(expected, actual)
    }

    @Test
    fun serviceUnavailable() = runBlocking {
        cloudDataSource.expectException(IllegalStateException())
        val actual = repository.loadCurrencies()
        val expected = LoadCurrenciesResult.Error("Service unavailable")
        assertEquals(expected, actual)
    }
}

class FakeProvideResources : ProvideResources {

    override fun noInternetConnectionMessage() = "No internet connection"

    override fun serviceUnavailableMessage() = "Service unavailable"

}

private class FakeCacheDataSource : CurrencyCacheDataSource.Mutable {

    private var actualList = emptyList<CurrencyCache>()

    override suspend fun save(currencyList: List<CurrencyCache>) {
        actualList = currencyList
    }

    override suspend fun read(): List<CurrencyCache> {
        return actualList
    }
}

private class FakeCloudDataSource : CurrencyCloudDataSource {

    private var isSuccessResult: Boolean = false

    private lateinit var exception: Exception

    fun successResult() {
        isSuccessResult = true
    }

    override suspend fun currencies(): HashMap<String, String> {
        if (isSuccessResult)
            return hashMapOf(
                "111" to "1111111",
                "222" to "2222222",
                "333" to "3333333"
            )
        else
            throw exception
    }

    fun expectException(exception: Exception) {
        this.exception = exception
    }
}
