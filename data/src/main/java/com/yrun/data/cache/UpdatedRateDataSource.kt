package com.yrun.data.cache

import com.yrun.data.cloud.PairClodDataSource

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