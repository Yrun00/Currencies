package com.yrun.data.load.cache

import javax.inject.Inject

interface CurrencyCacheDataSource {
    interface Save {
        suspend fun save(currencyList: List<CurrencyCache>)
    }

    interface Read {
        suspend fun read(): List<CurrencyCache>
    }

    interface Mutable : Save, Read

    class Base @Inject constructor(private val currencyDao: CurrencyDao) : Mutable {

        override suspend fun save(currencyList: List<CurrencyCache>) {
            currencyDao.insert(currencyList)
        }

        override suspend fun read(): List<CurrencyCache> {
            return currencyDao.currencies()
        }
    }
}

