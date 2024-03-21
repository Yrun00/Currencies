package com.yrun.data.dashboard

import com.yrun.data.core.HandleError
import com.yrun.data.dashboard.cache.DashboardItemsDataSource
import com.yrun.data.dashboard.cache.FavoritePairsCacheDataSource
import com.yrun.data.dashboard.cache.PairCache
import com.yrun.data.load.cache.CurrencyCache
import com.yrun.data.load.cache.CurrencyCacheDataSource
import com.yrun.data.load.cloud.CurrencyCloudDataSource
import com.yrun.domain.dashboard.DashboardItem
import com.yrun.domain.dashboard.DashboardResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DashboardRepositoryTest {

    private lateinit var cloudPairDataSource: FakeFavoritePairsCacheDataSource
    private lateinit var repository: BaseDashboardRepository
    private lateinit var dashboardItemsDataSource: FakeDashboardItemsDataSource
    private lateinit var handleError: FakeHandleError
    private lateinit var cacheDataSource: FakeCacheDataSource
    private lateinit var cloudCurrencyDataSource: FakeCloudCurrencyDataSource

    @Before
    fun setup() {
        handleError = FakeHandleError()
        cloudPairDataSource = FakeFavoritePairsCacheDataSource()
        dashboardItemsDataSource = FakeDashboardItemsDataSource()
        cacheDataSource = FakeCacheDataSource()
        cloudCurrencyDataSource = FakeCloudCurrencyDataSource()
        repository = BaseDashboardRepository(
            cloudPairDataSource,
            handleError,
            dashboardItemsDataSource,
            cacheDataSource,
            cloudCurrencyDataSource
        )
    }

    @Test
    fun emptyTest() = runBlocking {

        val actual = repository.dashboard()
        Assert.assertEquals(DashboardResult.Empty, actual)
    }

    @Test
    fun errorTest() = runBlocking {
        cloudPairDataSource.notEmpty()
        val actual = repository.dashboard()
        Assert.assertEquals(DashboardResult.Error("Service unavailable"), actual)
    }

    @Test
    fun successfulTest() = runBlocking {
        cloudPairDataSource.notEmpty()
        dashboardItemsDataSource.successResult()
        val actual = repository.dashboard()
        Assert.assertEquals(
            DashboardResult.Success(
                listOf<DashboardItem>(DashboardItem.Base("A", "B", 0.0))
            ), actual
        )
    }

    @Test
    fun testDeletePair() = runBlocking {
        cloudPairDataSource.save(PairCache("A", "B", 0.0, -2))
        repository.deletePair("B", "A")
        Assert.assertEquals(repository.dashboard(), DashboardResult.Empty)
    }

    @Test
    fun deleteTest() = runBlocking {

    }
}

private class FakeHandleError : HandleError {
    override fun handleError(error: Exception): String {
        return "Service unavailable"
    }
}

private class FakeDashboardItemsDataSource : DashboardItemsDataSource {

    private var isSuccessResult: Boolean = false
    private lateinit var exception: IllegalStateException

    fun successResult() {
        isSuccessResult = true
    }

    override suspend fun updatePairs(favoritePairs: List<PairCache>): List<DashboardItem> {
        if (isSuccessResult)
            return listOf<DashboardItem>(
                DashboardItem.Base("A", "B", 0.0)
            )
        else
            throw exception
    }
}

private class FakeFavoritePairsCacheDataSource : FavoritePairsCacheDataSource.Mutable {

    private var list = mutableListOf<PairCache>()

    override suspend fun save(currencyPair: PairCache) {
        list.add(currencyPair)
    }

    override suspend fun read(): List<PairCache> {
        return list
    }

    override suspend fun delete(currencyPair: PairCache) {
        list.remove(currencyPair)
    }

    suspend fun notEmpty() {
        list.add(PairCache("A", "B", 0.0, -2))
    }
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

private class FakeCloudCurrencyDataSource : CurrencyCloudDataSource {

    private var isSuccessResult: Boolean = false

    private lateinit var exception: Exception

    fun successResult() {
        isSuccessResult = true
    }

    override suspend fun currencies(): HashMap<String, String> {
        return hashMapOf(
            "111" to "1111111",
            "222" to "2222222",
            "333" to "3333333"
        )
    }

    fun expectException(exception: Exception) {
        this.exception = exception
    }
}