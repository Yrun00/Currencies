package com.yrun.data.dashboard.cache

import com.yrun.data.core.CurrentTimeInMillis
import com.yrun.data.dashboard.cloud.UpdatedRateDataSource
import com.yrun.domain.dashboard.DashboardItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

interface DashboardItemsDataSource {

    suspend fun updatePairs(favoritePairs: List<PairCache>): List<DashboardItem>

    class Base(
        private val currentTimeInMillis: CurrentTimeInMillis,
        private val updatedRateDataSource: UpdatedRateDataSource,
        private val dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) : DashboardItemsDataSource {
        override suspend fun updatePairs(favoritePairs: List<PairCache>): List<DashboardItem> =
            withContext(dispatcher) {
                val results = favoritePairs.map { currentPair ->
                    async {
                        DashboardItem.Base(
                            fromCurrency = currentPair.fromCurrency,
                            toCurrency = currentPair.toCurrency,
                            rate = if (currentPair.isInvalid(currentTimeInMillis))
                                updatedRateDataSource.updatedRate(currentPair)
                            else
                                currentPair.rate
                        )
                    }
                }
                results.awaitAll()
            }
    }
}
