package com.yrun.data.dashboard

import com.yrun.data.dashboard.cache.DashboardItemsDataSource
import com.yrun.data.dashboard.cache.PairCache
import com.yrun.data.dashboard.cloud.UpdatedRateDataSource
import com.yrun.domain.dashboard.DashboardItem
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DashboardItemsDataSourceTest {

    private lateinit var dashboardItemsDataSource: DashboardItemsDataSource
    private lateinit var updatedRateDataSource: FakeUpdatedRateDataSource
    private lateinit var currentTimeInMillis: FakeCurrentTimeInMillis

    @Before
    fun setup() {
        updatedRateDataSource = FakeUpdatedRateDataSource()
        currentTimeInMillis = FakeCurrentTimeInMillis()
        dashboardItemsDataSource =
            DashboardItemsDataSource.Base(currentTimeInMillis, updatedRateDataSource)
    }

    @Test
    fun invalidPair() = runBlocking {
        val actual = dashboardItemsDataSource.updatePairs(
            listOf<PairCache>(
                PairCache("A", "B", 0.0, 23 * 3600 * 1000)
            )
        )
        Assert.assertEquals(listOf<DashboardItem>(DashboardItem.Base("A", "B", 22.2)), actual)
    }

    @Test
    fun validPair() = runBlocking {
        val actual = dashboardItemsDataSource.updatePairs(
            listOf<PairCache>(
                PairCache("Q", "W", 1.0, 47 * 3600 * 1000)
            )
        )
        Assert.assertEquals(listOf<DashboardItem>(DashboardItem.Base("Q", "W", 1.0)), actual)
    }
}

private class FakeUpdatedRateDataSource : UpdatedRateDataSource {
    override suspend fun updatedRate(currentPair: PairCache): Double {
        return 22.2
    }
}