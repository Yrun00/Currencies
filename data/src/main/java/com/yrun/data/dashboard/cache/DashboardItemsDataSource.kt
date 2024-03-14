package com.yrun.data.dashboard.cache

import com.yrun.data.core.CurrentTimeInMillis
import com.yrun.data.dashboard.cloud.UpdatedRateDataSource
import com.yrun.domain.dashboard.DashboardItem

interface DashboardItemsDataSource {

    suspend fun updatePairs(favoritePairs: List<PairCache>): List<DashboardItem>

    class Base(
        private val currentTimeInMillis: CurrentTimeInMillis,
        private val updatedRateDataSource: UpdatedRateDataSource
    ) : DashboardItemsDataSource {
        override suspend fun updatePairs(favoritePairs: List<PairCache>): List<DashboardItem> =
            favoritePairs.map { currentPair ->
                val rate =
                    if (currentPair.isInvalid(currentTimeInMillis)) updatedRateDataSource.updatedRate(
                        currentPair
                    )
                    else currentPair.rate

                DashboardItem.Base(
                    currentPair.toCurrency,
                    currentPair.fromCurrency,
                    rate
                )
            }
    }
}
