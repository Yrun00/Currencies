package com.yrun.data.dashboard.cache

import javax.inject.Inject

interface FavoritePairsCacheDataSource {

    interface Save {
        suspend fun save(currencyPair: PairCache)
    }

    interface Read {
        suspend fun read(): List<PairCache>
    }

    interface Delete {

        suspend fun delete(currencyPair: PairCache)
    }

    interface Mutable : Save, Read, Delete

    class Base @Inject constructor(private val pairDao: PairDao) : Mutable {

        override suspend fun save(currencyPair: PairCache) {
            pairDao.insert(currencyPair)
        }

        override suspend fun read(): List<PairCache> {
            return pairDao.favoritePairs()
        }

        override suspend fun delete(currencyPair: PairCache) {
            pairDao.delete(currencyPair)
        }
    }
}