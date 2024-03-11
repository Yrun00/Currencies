package com.yrun.data.dashboard

import com.yrun.data.core.HandleError
import com.yrun.data.dashboard.cache.DashboardItemsDataSource
import com.yrun.data.dashboard.cache.FavoritePairsCacheDataSource
import com.yrun.data.dashboard.cache.PairCache
import com.yrun.domain.dashboard.DashboardItem
import com.yrun.domain.dashboard.DashboardResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DashboardRepositoryTest {

    private lateinit var cloudDataSource: FakeFavoritePairsCacheDataSource
    private lateinit var repository: BaseDashboardRepository
    private lateinit var dashboardItemsDataSource: FakeDashboardItemsDataSource
    private lateinit var handleError: FakeHandleError

    @Before
    fun setup() {
        handleError = FakeHandleError()
        cloudDataSource = FakeFavoritePairsCacheDataSource()
        dashboardItemsDataSource = FakeDashboardItemsDataSource()
        repository = BaseDashboardRepository(cloudDataSource, handleError, dashboardItemsDataSource)
    }

    @Test
    fun emptyTest() = runBlocking {

        val actual = repository.dashboard()
        Assert.assertEquals(DashboardResult.Empty, actual)
    }

    @Test
    fun errorTest() = runBlocking {
        cloudDataSource.notEmpty()
        val actual = repository.dashboard()
        Assert.assertEquals(DashboardResult.Error("Service unavailable"), actual)
    }

    @Test
    fun successfulTest() = runBlocking {
        cloudDataSource.notEmpty()
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
        cloudDataSource.save(PairCache("A", "B", 0.0, -2))
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