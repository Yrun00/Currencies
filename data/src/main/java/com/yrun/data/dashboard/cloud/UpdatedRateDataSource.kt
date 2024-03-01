package com.yrun.data.dashboard.cloud

import com.yrun.data.core.CurrentTimeInMillis
import com.yrun.data.dashboard.cache.FavoritePairsCacheDataSource
import com.yrun.data.dashboard.cache.PairCache

interface UpdatedRateDataSource {

    suspend fun updatedRate(currentPair: PairCache): Double

    class Base(
        private val currentTimeInMillis: CurrentTimeInMillis,
        private val cacheDataSource: FavoritePairsCacheDataSource.Save,
        private val pairClodDataSource: PairClodDataSource
    ) : UpdatedRateDataSource {

        override suspend fun updatedRate(currentPair: PairCache): Double {

            val updateRate = pairClodDataSource.rate(
                currentPair.toCurrency,
                currentPair.fromCurrency
            )
            val updatePair = PairCache(
                currentPair.toCurrency,
                currentPair.fromCurrency,
                updateRate,
                currentTimeInMillis.currentTime()
            )
            cacheDataSource.save(updatePair)
            return updateRate
        }
    }
}