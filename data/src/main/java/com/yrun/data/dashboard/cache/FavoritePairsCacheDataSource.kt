package com.yrun.data.dashboard.cache

interface FavoritePairsCacheDataSource {

    interface Save {
        suspend fun save(currencyPair: PairCache)
    }

    interface Read {
        suspend fun read(): List<PairCache>
    }

    interface Mutable : Save, Read

    class Base(private val pairDao: PairDao) : Mutable {

        override suspend fun save(currencyPair: PairCache) {
            pairDao.insert(currencyPair)
        }

        override suspend fun read(): List<PairCache> {
            return pairDao.favoritePairs()
        }
    }
}